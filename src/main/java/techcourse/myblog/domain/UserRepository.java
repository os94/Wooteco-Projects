package techcourse.myblog.domain;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;

public interface UserRepository extends CrudRepository<User, Id> {
}
