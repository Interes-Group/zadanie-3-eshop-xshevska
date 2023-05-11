package sk.stuba.fei.uim.oop.assignment3.cart.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CartInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;

    @ManyToOne
    private Product product;

    public CartInput(Long amount, Product product) {
        this.amount = amount;
        this.product = product;
    }

}