package sk.stuba.fei.uim.oop.assignment3.cart.data;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(orphanRemoval = true)
    private List<CartInput> shoppingList;

    private boolean payed;

    public ShoppingCart() {
        this.shoppingList = new ArrayList<>();
    }

}
