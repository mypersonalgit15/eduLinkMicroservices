package com.cts.course_service.application.service;

import com.cts.classexception.CourseException;
import com.cts.classexception.FileException;
import com.cts.classexception.LearningMaterialException;
import com.cts.course_service.application.entity.Course;
import com.cts.course_service.application.entity.LearningMaterial;
import com.cts.course_service.application.repository.CourseRepository;
import com.cts.course_service.application.repository.LearningMaterialRepository;
import com.cts.course_service.application.dto.LearningMaterialRegistrationDto;
import com.cts.course_service.application.projection.LearningCourseMaterialProjection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class LearningMaterialServiceImpl implements ILearningMaterialService {

    private final CourseRepository courseRepository;
    private final LearningMaterialRepository learningMaterialRepository;

    // In a real microservice, you would inject a StorageService here
    // private final IStorageService storageService;

    @Override
    @Transactional
    public String registerLearningMaterial(LearningMaterialRegistrationDto dto) {
        Long courseId = dto.getCourseId();

        // 1. Check local Course Repository
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new CourseException("Course not found: " + courseId, HttpStatus.NOT_FOUND));

        // 2. Check if material already exists
        if(learningMaterialRepository.checkExistingLearningMaterial(courseId)){
            throw new LearningMaterialException("Material already exists for course: " + courseId, HttpStatus.CONFLICT);
        }

        try {
            // 3. Logic to handle file (Extract to a dedicated Storage Utility/Service)
            // String fileReference = storageService.upload(dto.getLearningMaterialFile());

            LearningMaterial learningMaterial = new LearningMaterial();
            learningMaterial.setLearningMaterialTitle(dto.getLearningMaterialTitle());
            learningMaterial.setLearningMaterialUploadedDate(LocalDateTime.now());
            learningMaterial.setLearningMaterialStatus("UPLOADED");
            learningMaterial.setCourse(course);

            // For now, using your DtoMapper logic but ensuring it returns a detached entity
            // learningMaterial.setLearningMaterialFile(fileReference);

            learningMaterialRepository.save(learningMaterial);
            return "Learning material uploaded successfully!";

        } catch (Exception e) {
            log.error("Upload failed", e);
            throw new FileException("Internal Server Error during upload", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public LearningCourseMaterialProjection findMaterialsByCourseId(Long courseId) {
        // Ensure the course exists in our local domain
        if (!courseRepository.existsByCourseId(courseId)) {
            throw new CourseException("Course not found", HttpStatus.NOT_FOUND);
        }

        return learningMaterialRepository.findMaterialsByCourseId(courseId)
                .orElseThrow(() -> new LearningMaterialException("No material found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Resource getFileFromProjection(Long id) {
        LearningMaterial material = learningMaterialRepository.findById(id)
                .orElseThrow(() -> new LearningMaterialException("Material not found", HttpStatus.NOT_FOUND));

        try {
            // Path-based retrieval is okay for a single-node dev environment,
            // but in production microservices, this would be:
            // return storageService.download(material.getLearningMaterialFile());

            Path path = Paths.get(material.getLearningMaterialFile());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileException("File is missing on storage", HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            throw new FileException("Storage configuration error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}