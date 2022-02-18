package web.spring.SpringEShop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import web.spring.SpringEShop.models.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByNameContainingIgnoreCase(String name);
}
