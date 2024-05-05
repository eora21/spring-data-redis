package eora21.demo.redis.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

@DataRedisTest
class HumanRepositoryTest {

    @Autowired
    HumanRepository humanRepository;

    @AfterEach
    void tearDown() {
        humanRepository.deleteAll();
    }

    @Test
    @DisplayName("조회")
    void findHuman() throws Exception {

        // given
        Human human = new Human(1L, "eora", 21);
        humanRepository.save(human);

        // when
        Human foundHuman = humanRepository.findById(1L)
                .orElseThrow();

        // then
        Assertions.assertThat(foundHuman).extracting(Human::getId, Human::getName, Human::getAge)
                .containsExactly(1L, "eora", 21);
    }
}
