package com.cts.course_service.application.entity;
import com.cts.util.DtoMapper;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private Long courseId;

    private String courseTitle;
    private String courseSubject;
    private String courseGradeLevel;
    private int courseCredit;
    private String courseStatus;
    private double courseRating;
    private Long totalCourseRatingCount;

    @ElementCollection
    private Set<Long> studentId = new HashSet<>();
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<LearningMaterial> learningMaterialList;

}
