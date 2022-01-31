package web.spring.SpringEShop.repo;

import org.springframework.data.repository.CrudRepository;
import web.spring.SpringEShop.models.Item;

public interface ItemRepository extends CrudRepository<Item,Long> {
}
