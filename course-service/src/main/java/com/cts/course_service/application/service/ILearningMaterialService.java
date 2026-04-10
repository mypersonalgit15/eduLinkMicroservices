package com.cts.course_service.application.service;

import com.cts.dto.request.LearningMaterialRegistrationDto;
import com.cts.course_service.application.projection.LearningCourseMaterialProjection;
import org.springframework.core.io.Resource;

public interface ILearningMaterialService {
    String registerLearningMaterial(LearningMaterialRegistrationDto dto);
    LearningCourseMaterialProjection findMaterialsByCourseId(Long courseId);
    Resource getFileFromProjection(Long id); // The Gateway must support streaming for this
}
