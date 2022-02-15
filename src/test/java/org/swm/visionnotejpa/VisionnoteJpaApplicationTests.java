package org.swm.visionnotejpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.swm.visionnotejpa.entity.Sample;

import javax.persistence.EntityManager;

import static org.swm.visionnotejpa.entity.QSample.*;

@SpringBootTest
@Transactional
class VisionnoteJpaApplicationTests {

	@Autowired EntityManager em;
	@Autowired JPAQueryFactory queryFactory;

	@Test
	void contextLoads() {
	}

	@Test
	public void basicTest() throws Exception {
	    // given
		Sample sampleData = new Sample();
		em.persist(sampleData);

	    // when
		Sample result = queryFactory
				.selectFrom(sample)
				.fetchOne();

		// then
		Assertions.assertThat(result).isEqualTo(sampleData);
	}
}
