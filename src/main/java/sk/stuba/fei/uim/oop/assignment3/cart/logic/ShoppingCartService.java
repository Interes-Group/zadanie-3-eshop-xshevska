package sk.stuba.fei.uim.oop.assignment3.cart.logic;


import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartInput;
import sk.stuba.fei.uim.oop.assignment3.cart.data.IShoppingCartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartListItem;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;

import java.util.List;
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
    public List<ShoppingCart> getAll() {
        return this.shoppingRepository.findAll();
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
    public ShoppingCart addToShoppingCart(Long shoppingCartId, CartListItem cartListItem) throws NotFoundException, IllegalOperationException {
        ShoppingCart shoppingCart = this.shoppingRepository.findShoppingCartById(shoppingCartId);
        if (shoppingCart == null) {
            throw new NotFoundException();
        }
        Product product = this.productService.getById(cartListItem.getProductId());
        if (product.getAmount() < cartListItem.getAmount()) {
            throw new IllegalOperationException();
        }

        Long newAmount = product.getAmount() - cartListItem.getAmount();
        product.setAmount(newAmount);
        this.productService.updateAmount(product.getId(), newAmount);

        List<CartInput> shopList = shoppingCart.getShoppingList();

        shopList.stream()
                .filter(cartInput -> cartInput.getProduct().getId().equals(cartListItem.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        input -> updateCartInput(input, cartListItem),
                        () -> addNewCartInput(shopList, cartListItem, product)
                );

        if (newAmount == 0) {
            this.productService.delete(cartListItem.getProductId());
        }

        shoppingCart.setShoppingList(shopList);
        this.shoppingRepository.save(shoppingCart);
        return shoppingCart;
    }

    private void updateCartInput(CartInput cartInput, CartListItem cartListItem) {
        cartInput.setAmount(cartInput.getAmount() + cartListItem.getAmount());
        this.cartInputService.create(cartInput);
    }

    private void addNewCartInput(List<CartInput> shopList, CartListItem cartListItem, Product product) {
        CartInput cartInput = new CartInput(cartListItem.getAmount(), product);
        this.cartInputService.create(cartInput);
        shopList.add(cartInput);
    }


}
