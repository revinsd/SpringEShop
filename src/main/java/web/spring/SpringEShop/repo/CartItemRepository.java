package web.spring.SpringEShop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import web.spring.SpringEShop.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
