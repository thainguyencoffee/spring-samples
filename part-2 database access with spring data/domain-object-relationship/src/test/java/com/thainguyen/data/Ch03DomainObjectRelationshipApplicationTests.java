package com.thainguyen.data;

import static org.assertj.core.api.Assertions.*;

import com.thainguyen.data.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch03DomainObjectRelationshipApplicationTests {

	@Autowired
	AuthorRepository authorRepository;

	@Test
	public void whenCountAllCoursesThenExpectFiveCourses() {
		assertThat(authorRepository.getAuthorCourseInfo(2)).hasSize(3);
	}

}
