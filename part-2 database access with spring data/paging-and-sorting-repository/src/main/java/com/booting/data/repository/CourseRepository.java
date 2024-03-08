package com.booting.data.repository;

import com.booting.data.domain.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

}
