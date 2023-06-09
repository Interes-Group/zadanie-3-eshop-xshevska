package sk.stuba.fei.uim.oop.assignment3.cart.web.bodies;

import lombok.Data;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartResponse {
    private Long id;
    private List<CartListItem> shoppingList;
    private boolean payed;


    public CartResponse(ShoppingCart shoppingCart) {
        this.id = shoppingCart.getId();
        this.payed = shoppingCart.isPayed();
        this.shoppingList = shoppingCart.getShoppingList().stream().map(CartListItem::new).collect(Collectors.toList());
    }
}
