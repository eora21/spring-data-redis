package eora21.demo.redis.operator;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class XForceReader {

    private final RedisTemplate<String, String> redisTemplate;

    public Optional<XForce> read(Long id) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

        String redisXForceValueText = valueOps.get(XForceWriter.PREFIX + id);

        if (Objects.isNull(redisXForceValueText)) {
            return Optional.empty();
        }

        XForce xForceFromText = createXForceFromText(id, redisXForceValueText);

        return Optional.of(xForceFromText);
    }

    private XForce createXForceFromText(Long id, String redisXForceValueText) {
        String[] xForceFieldValues = redisXForceValueText.split(XForceWriter.DELIMITER);

        if (xForceFieldValues.length != 2) {
            throw new IllegalStateException();
        }

        String name = xForceFieldValues[0];
        int age = Integer.parseInt(xForceFieldValues[1]);

        return new XForce(id, name, age);
    }
}
