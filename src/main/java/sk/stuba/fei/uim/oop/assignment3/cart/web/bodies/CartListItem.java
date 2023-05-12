package sk.stuba.fei.uim.oop.assignment3.cart.web.bodies;


import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartInput;


@Data
@NoArgsConstructor
public class CartListItem {
    private Long productId;
    private Long amount;

    public CartListItem(CartInput cartInput) {
        this.productId = cartInput.getProduct().getId();
        this.amount = cartInput.getAmount();
    }
}
