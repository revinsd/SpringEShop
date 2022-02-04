package web.spring.SpringEShop.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Заполните артикул товара")
    private String articul;

    private String filename;
    @NotBlank(message = "Заполните название товара")
    private String name;
    @Min(value = 1, message = "Минимальная цена 1₽")
    private int price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticul() {
        return articul;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFilename() {return filename;}

    public void setFilename(String filename) {this.filename = filename;}

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", articul='" + articul + '\'' +
                ", filename='" + filename + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
