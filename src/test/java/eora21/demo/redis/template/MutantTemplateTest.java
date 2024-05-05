package eora21.demo.redis.template;

import eora21.demo.redis.domain.Mutant;
import eora21.demo.redis.domain.NewMutant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class MutantTemplateTest {

    @Autowired
    RedisTemplate<String, Mutant> jsonRedisTemplate;

    @Autowired
    RedisTemplate<String, Mutant> beautyKeyJsonRedisTemplate;

    @Autowired
    RedisTemplate<String, Mutant> beautyKeyValueRedisTemplate;

    @Autowired
    RedisTemplate<String, NewMutant> newMutantRedisTemplate;

    @Test
    @DisplayName("일반 json 조회")
    void jsonTemplateGet() throws Exception {

        // given
        Mutant deadpool = new Mutant(1L, "deadpool", 47);
        ValueOperations<String, Mutant> valueOps = jsonRedisTemplate.opsForValue();

        valueOps.set("mutant:" + deadpool.id(), deadpool);

        // when
        Mutant findMutant = valueOps.get("mutant:" + 1);

        // then
        Assertions.assertThat(findMutant).extracting(Mutant::id, Mutant::name, Mutant::age)
                .containsExactly(1L, "deadpool", 47);
    }

    @Test
    @DisplayName("이쁜 key를 가진 json 조회")
    void beautyKeyJsonTemplateGet() throws Exception {

        // given
        Mutant wolverine = new Mutant(2L, "wolverine", 55);
        ValueOperations<String, Mutant> valueOps = beautyKeyJsonRedisTemplate.opsForValue();

        valueOps.set("mutant:" + wolverine.id(), wolverine);

        // when
        Mutant findMutant = valueOps.get("mutant:" + 2);

        // then
        Assertions.assertThat(findMutant).extracting(Mutant::id, Mutant::name, Mutant::age)
                .containsExactly(2L, "wolverine", 55);
    }

    @Test
    @DisplayName("serializer를 사용한 mutant 조회")
    void beautyKeyValueRedisTemplateGet() throws Exception {

        // given
        Mutant yukio = new Mutant(3L, "yukio", 31);
        ValueOperations<String, Mutant> valueOps = beautyKeyValueRedisTemplate.opsForValue();

        valueOps.set("mutant:" + yukio.id(), yukio);

        // when
        Mutant findMutant = valueOps.get("mutant:" + 3);

        // then
        Assertions.assertThat(findMutant).extracting(Mutant::id, Mutant::name, Mutant::age)
                .containsExactly(3L, "yukio", 31);
    }
}
