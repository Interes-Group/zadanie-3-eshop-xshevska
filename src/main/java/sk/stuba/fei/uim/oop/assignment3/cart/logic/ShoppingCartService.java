package sk.stuba.fei.uim.oop.assignment3.cart.logic;


import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.IShoppingCartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.Objects;

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

    @Override
    public ShoppingCart getById(Long id) throws NotFoundException {
        ShoppingCart shoppingCart = this.repository.findShoppingCartById(id);
        if (Objects.isNull(shoppingCart)) {
            throw new NotFoundException();
        }
        return shoppingCart;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }


}
