package techcourse.myblog.domain;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Id> {
    List<User> findByEmail(String email);
}
