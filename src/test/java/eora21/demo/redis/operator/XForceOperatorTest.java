package eora21.demo.redis.operator;

import eora21.demo.redis.domain.XForce;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XForceOperatorTest {

    @Autowired
    XForceReader xForceReader;

    @Autowired
    XForceWriter xForceWriter;

    @Test
    @DisplayName("operator를 통한 조회")
    void OperatorRead() throws Exception {

        // given
        XForce vanisher = new XForce(1L, "vanisher", 60);
        xForceWriter.write(vanisher);

        // when
        XForce readXForce = xForceReader.read(vanisher.id())
                .orElseThrow();

        // then
        Assertions.assertThat(readXForce).extracting(XForce::id, XForce::name, XForce::age)
                .containsExactly(1L, "vanisher", 60);
    }
}
