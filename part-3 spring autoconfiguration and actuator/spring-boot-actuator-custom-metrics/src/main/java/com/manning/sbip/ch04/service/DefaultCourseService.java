package com.manning.sbip.ch04.service;

import com.manning.sbip.ch04.model.Course;
import com.manning.sbip.ch04.repository.CourseRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultCourseService implements CourseService {

    private final CourseRepository courseRepository;
    private final Counter createCourseCounter;
    private final Timer createCoursesTimer;
    private final DistributionSummary distributionSummary;

    @Autowired
    public DefaultCourseService(CourseRepository courseRepository,
                                Counter createCourseCounter,
                                Timer createCoursesTimer,
                                DistributionSummary distributionSummary) {
        this.courseRepository = courseRepository;
        this.createCourseCounter = createCourseCounter;
        this.createCoursesTimer = createCoursesTimer;
        this.distributionSummary = distributionSummary;
    }

    public Course createCourse(Course course) throws Exception {
        distributionSummary.record(course.getRating());
        return createCoursesTimer.recordCallable(() -> courseRepository.save(course));
    }

    public Optional<Course> findCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    public Iterable<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public long count() {
        return courseRepository.count();
    }
}
