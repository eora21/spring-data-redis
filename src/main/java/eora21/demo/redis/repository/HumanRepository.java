package eora21.demo.redis.repository;

import eora21.demo.redis.domain.Human;
import org.springframework.data.repository.CrudRepository;

public interface HumanRepository extends CrudRepository<Human, Long> {
}
