package techcourse.myblog.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findUsersByEmail(String email);

    User findUserByEmail(String email);

    User findUserById(long id);
}
