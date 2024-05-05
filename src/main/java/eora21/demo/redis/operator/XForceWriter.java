package eora21.demo.redis.operator;

import eora21.demo.redis.domain.XForce;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class XForceWriter {

    public static final String PREFIX = "x-force#";
    public static final String DELIMITER = "/";

    private final RedisTemplate<String, String> redisTemplate;

    public void write(XForce xForce) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

        valueOps.set(keyGenerator(xForce.id()), valueGenerator(xForce));
    }

    private String keyGenerator(Long id) {
        return PREFIX + id;
    }

    private String valueGenerator(XForce xForce) {
        return new StringJoiner(DELIMITER)
                .add(xForce.name())
                .add(String.valueOf(xForce.age()))
                .toString();
    }
}
