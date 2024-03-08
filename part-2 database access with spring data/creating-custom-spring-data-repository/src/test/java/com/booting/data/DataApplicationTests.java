package com.booting.data;

import static org.assertj.core.api.Assertions.assertThat;

import com.booting.data.model.Course;
import com.booting.data.repository.CustomizedCourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@DataJpaTest
class DataApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private CustomizedCourseRepository customizedCourseRepository;

	@Test
	public void givenCreateCourseWhenFindAllCoursesThenExpectOneCourse() {
		Course course = new Course("Rapid Spring Boot Application Development", "Spring", 4
				, "Spring boot gives all the power of the Spring Framework " +
				"without all of the complexities");
		customizedCourseRepository.save(course);
		assertThat(Arrays.asList(customizedCourseRepository.findAll()).size()).isEqualTo(1);
	}

}
