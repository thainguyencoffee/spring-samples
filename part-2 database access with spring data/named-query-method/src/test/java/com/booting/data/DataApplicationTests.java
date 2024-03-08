package com.booting.data;

import static org.assertj.core.api.Assertions.*;

import com.booting.data.model.Course;
import com.booting.data.repository.CourseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataApplicationTests {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	private EntityManager em;

	@Test
	public void givenCoursesCreatedWhenLoadCoursesWithQueryThenExpectCorrectCourseDetails() {
		assertThat(courseRepository.findAllByCategory("Spring")).hasSize(8);
		assertThat(courseRepository.findAllByRating(3)).hasSize(3);
		assertThat(courseRepository.updateCourseRatingByName(5, "Cloud Native Spring Boot Application Development")).isEqualTo(1);
		assertThat(courseRepository.findAllByCategoryAndRatingGreaterThan("Spring", 4)).hasSize(4);
	}

	// Criteria JPA
	@Test
	public void givenCoursesCreatedWhenLoadCoursesWithQuery() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);

		Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);
		Predicate courseCategoryPredicate = criteriaBuilder
				.equal(courseRoot.get("category"), "Spring");
		courseCriteriaQuery.where(courseCategoryPredicate);
		TypedQuery<Course> query = em.createQuery(courseCriteriaQuery);
		assertThat(query.getResultList().size()).isEqualTo(8);
	}
}
