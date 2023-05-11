package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartListItem;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartResponse;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;

import java.util.List;

public interface IShoppingCartService {

    List<ShoppingCart> getAll();
    ShoppingCart create();

    ShoppingCart getById(Long cartId) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    ShoppingCart addToShoppingCart(Long shoppingCartId, CartListItem cartListItem) throws NotFoundException, IllegalOperationException;



}
