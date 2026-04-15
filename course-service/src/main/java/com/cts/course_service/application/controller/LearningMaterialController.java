package com.cts.course_service.application.controller;

import com.cts.dto.request.LearningMaterialRegistrationDto;
import com.cts.course_service.application.projection.LearningCourseMaterialProjection;
import com.cts.course_service.application.service.ILearningMaterialService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/learningMaterial")
public class LearningMaterialController {

    private final ILearningMaterialService learningMaterialService;

    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerLearningMaterial(@Valid @ModelAttribute LearningMaterialRegistrationDto learningMaterialRegistrationDto){
        log.info("Received POST request to register learning material for Course ID: {}", learningMaterialRegistrationDto.getCourseId());
        return ResponseEntity.status(200).body(learningMaterialService.registerLearningMaterial(learningMaterialRegistrationDto));
    }

    @GetMapping("/findCourseMaterial/{courseId}")
    public ResponseEntity<LearningCourseMaterialProjection> findLearningCourseMaterialByCourseId(@Valid @PathVariable Long courseId){
        return ResponseEntity.status(200).body(learningMaterialService.findMaterialsByCourseId(courseId));
    }

    @GetMapping("/displayLearningMaterialContent/{id}")
    public ResponseEntity<Resource> displayLearningMaterialContent(@PathVariable Long id) {
        Resource resource = learningMaterialService.getFileFromProjection(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"material_" + id + "\"")
                .body(resource);
    }

}