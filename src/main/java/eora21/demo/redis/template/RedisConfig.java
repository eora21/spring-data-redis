package eora21.demo.redis.template;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.StringJoiner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Mutant> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Mutant> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Mutant> beautyKeyJsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Mutant> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Mutant> beautyKeyValueRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Mutant> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new MutantSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, NewMutant> newMutantRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, NewMutant> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new NewMutantSerializer());
        return redisTemplate;
    }

    private static final class MutantSerializer implements RedisSerializer<Mutant> {

        @Override
        public byte[] serialize(Mutant mutant) throws SerializationException {

            if (Objects.isNull(mutant)) {
                throw new IllegalArgumentException();
            }

            String mutantText = new StringJoiner("/")
                    .add(String.valueOf(mutant.id()))
                    .add(mutant.name())
                    .add(String.valueOf(mutant.age()))
                    .toString();

            return mutantText.getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public Mutant deserialize(byte[] bytes) throws SerializationException {
            if (Objects.isNull(bytes) || bytes.length == 0) {
                return null;
            }

            String value = new String(bytes);
            String[] mutantInfo = value.split("/");

            if (mutantInfo.length != 3) {
                throw new IllegalArgumentException();
            }

            long id = Long.parseLong(mutantInfo[0]);
            String name = mutantInfo[1];
            int age = Integer.parseInt(mutantInfo[2]);

            return new Mutant(id, name, age);
        }
    }

    private static final class NewMutantSerializer implements RedisSerializer<NewMutant> {

        @Override
        public byte[] serialize(NewMutant newMutant) throws SerializationException {

            if (Objects.isNull(newMutant)) {
                throw new IllegalArgumentException();
            }

            return newMutant.serialize();
        }

        @Override
        public NewMutant deserialize(byte[] bytes) throws SerializationException {
            if (Objects.isNull(bytes) || bytes.length == 0) {
                return null;
            }

            return NewMutant.deserialize(bytes);
        }
    }
}
