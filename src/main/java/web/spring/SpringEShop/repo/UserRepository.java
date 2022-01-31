package web.spring.SpringEShop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import web.spring.SpringEShop.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
