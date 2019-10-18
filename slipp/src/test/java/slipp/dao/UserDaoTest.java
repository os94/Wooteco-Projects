package slipp.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import slipp.domain.User;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.ConnectionManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {
    @BeforeEach
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = UserDao.getInstance();

        userDao.insert(expected);
        Optional<User> maybeUser = userDao.findByUserId(expected.getUserId());
        assertThat(maybeUser.isPresent()).isTrue();

        User actual = maybeUser.get();
        assertThat(actual).isEqualTo(expected);

        expected.update(new UserUpdatedDto("password2", "name2", "sanjigi@email.com"));
        userDao.update(expected);
        maybeUser = userDao.findByUserId(expected.getUserId());
        assertThat(maybeUser.isPresent()).isTrue();

        actual = maybeUser.get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findAll() {
        UserDao userDao = UserDao.getInstance();
        List<User> users = userDao.findAll();
        assertThat(users).hasSize(1);
    }
}