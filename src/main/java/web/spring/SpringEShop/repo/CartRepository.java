package web.spring.SpringEShop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import web.spring.SpringEShop.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findBySessionValue(String sessionValue);
}
