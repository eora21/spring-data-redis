package eora21.demo.redis.repository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "human-info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Human {

    @Id
    private Long id;

    private String name;

    private int age;
}
