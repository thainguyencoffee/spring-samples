package com.booting.data;

import static org.assertj.core.api.Assertions.assertThat;

import com.booting.data.model.Course;
import com.booting.data.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataApplicationTests {

	@Autowired
	private CourseRepository courseRepository;

	@Test
	public void givenCreateCourseWhenLoadTheCourseThenExpectSameCourse() {
		Course course = new Course("Rapid Spring Boot Application Development", "Spring", 4, "'Spring Boot gives all the power of the Spring Framework without all of the complexities");
		Course savedCourse = courseRepository.save(course);
		assertThat(courseRepository.findById(savedCourse.getId()).get()).isEqualTo(course);
	}

	@Test
	public void givenUpdateCourseWhenLoadTheCourseThenExpectUpdatedCourse() {
		Course course = new Course("Rapid Spring Boot Application Development", "Spring", 4, "'Spring Boot gives all the power of the Spring Framework without all of the complexities");
		courseRepository.save(course);
		course.setRating(5);
		Course savedCourse = courseRepository.save(course);
		assertThat(courseRepository.findById(savedCourse.getId()).get().getRating()).isEqualTo(5);
	}

	@Test
	public void givenDeleteCourseWhenLoadTheCourseThenExpectNoCourse() {
		Course course = new Course("Rapid Spring Boot Application Development", "Spring", 4, "'Spring Boot gives all the power of the Spring Framework without all of the complexities");
		Course savedCourse  = courseRepository.save(course);
		assertThat(courseRepository.findById(savedCourse.getId()).get()).isEqualTo(course);
		courseRepository.delete(course);
		assertThat(courseRepository.findById(savedCourse.getId()).isPresent()).isFalse();
	}

}
