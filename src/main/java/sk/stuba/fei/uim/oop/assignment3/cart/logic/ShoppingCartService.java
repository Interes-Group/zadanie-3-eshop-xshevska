package sk.stuba.fei.uim.oop.assignment3.cart.logic;


import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.IShoppingCartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;

@Service
public class ShoppingCartService implements IShoppingCartService {
    private final IShoppingCartRepository repository;


    public ShoppingCartService(IShoppingCartRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShoppingCart create() {
        return this.repository.save(new ShoppingCart());
    }

}
