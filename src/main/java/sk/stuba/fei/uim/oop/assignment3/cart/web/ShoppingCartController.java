package sk.stuba.fei.uim.oop.assignment3.cart.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.cart.logic.IShoppingCartService;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartListItem;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartResponse;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final IShoppingCartService service;

    public ShoppingCartController(IShoppingCartService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CartResponse> getAllProducts() {
        return this.service.getAll().stream().map(CartResponse::new).collect(Collectors.toList());
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResponse> addShoppingCart() {
        return new ResponseEntity<>(new CartResponse(this.service.create()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartResponse getShoppingCartById(@PathVariable("id") Long cartId) throws NotFoundException {
        return new CartResponse(this.service.getById(cartId));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long cartId) throws NotFoundException {
        this.service.delete(cartId);
    }

    @PostMapping(value = "/{id}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CartResponse addToShoppingCart(@PathVariable("id") Long shoppingCartId, @RequestBody CartListItem cartListItem) throws NotFoundException, IllegalOperationException {
        return new CartResponse(this.service.addToShoppingCart(shoppingCartId, cartListItem));
    }

}
