package org.sbip.ch06.repository;

import org.sbip.ch06.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

}
