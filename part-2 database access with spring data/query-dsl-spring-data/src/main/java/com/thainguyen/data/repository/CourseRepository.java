package com.thainguyen.data.repository;

import com.thainguyen.data.model.Course;
import com.thainguyen.data.projection.DescriptionOnly;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>,
        QuerydslPredicateExecutor<Course> {

    Iterable<DescriptionOnly> getCourseByName(String name);
}
