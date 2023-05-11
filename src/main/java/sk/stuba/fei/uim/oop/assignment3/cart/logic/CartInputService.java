package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartInput;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ICartInputRepository;

@Service
public class CartInputService implements ICartInputService {
    private final ICartInputRepository repository;

    public CartInputService(ICartInputRepository repository) {
        this.repository = repository;
    }

    @Override
    public CartInput create(CartInput cartInput) {
        return this.repository.save(cartInput);
    }



}