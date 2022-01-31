package web.spring.SpringEShop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import web.spring.SpringEShop.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
