package sk.stuba.fei.uim.oop.assignment3.product.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long amount;
    private String unit;
    private Double price;

    public Product(ProductRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.amount = request.getAmount();
        this.unit = request.getUnit();
        this.price = request.getPrice();
    }
}
