package sk.stuba.fei.uim.oop.assignment3.cart.web.bodies;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartInput;

@Getter
@Setter
@NoArgsConstructor
public class CartListItem {
    private Long productId;
    private Long amount;

    public CartListItem(CartInput cartInput) {
        this.productId = cartInput.getProduct().getId();
        this.amount = cartInput.getAmount();
    }
}
