package sk.stuba.fei.uim.oop.assignment3.cart.logic;


import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartInput;
import sk.stuba.fei.uim.oop.assignment3.cart.data.IShoppingCartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartListItem;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartResponse;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;

import java.util.Objects;

@Service
public class ShoppingCartService implements IShoppingCartService {
    private final IShoppingCartRepository shoppingRepository;

    private final IProductService productService;

    private final ICartInputService cartInputService;


    public ShoppingCartService(IShoppingCartRepository repository, IProductService productService, ICartInputService cartInputService) {
        this.shoppingRepository = repository;
        this.productService = productService;
        this.cartInputService = cartInputService;
    }

    @Override
    public ShoppingCart create() {
        return this.shoppingRepository.save(new ShoppingCart());
    }

    @Override
    public ShoppingCart getById(Long id) throws NotFoundException {
        ShoppingCart shoppingCart = this.shoppingRepository.findShoppingCartById(id);
        if (Objects.isNull(shoppingCart)) {
            throw new NotFoundException();
        }
        return shoppingCart;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        this.shoppingRepository.delete(this.getById(id));
    }

    @Override
    public CartResponse addToShoppingCart(Long shoppingCartId, CartListItem cartListItem) throws NotFoundException{
        ShoppingCart shoppingCart = this.shoppingRepository.findShoppingCartById(shoppingCartId);
        Product product = this.productService.getById(cartListItem.getProductId());

        if(!Objects.isNull(shoppingCart)) {
            if(!Objects.isNull(product) && (product.getAmount() >= cartListItem.getAmount()) ){ // больше равно количества того продукта на складе с тем что мы хотим
                // get shoppingList
                // find in shopping list product
                CartInput cartInput = shoppingCart.findProductById(cartListItem.getProductId());
                // another way will be with CartInput
//            this.cartInputService.

                // if OK -> добавь продукт тем что увеличишь количество того продукта в обьекте
                if(!Objects.isNull(cartInput)){
                    product.setAmount(product.getAmount()-cartListItem.getAmount());
                    this.productService.
                }
                // if NOT OK -> создай новый продукт с данным количеством и запиши его в шопинг кард
            }

        }

    }


}
