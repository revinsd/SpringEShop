package web.spring.SpringEShop.models;

import javax.persistence.*;

@Entity
@Table(name = "cartitem")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int quantity;


    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


}
