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
        // есть ли вообще такая корзина у нас?
        System.out.println(" ---- this is shopingCart: " + shoppingCart.toString());
        Product product = this.productService.getById(cartListItem.getProductId());
        // существует ли вообще такой продукт в складе?
        System.out.println(" ---- this is productService: " + product.getName() + " " + product.getId() + " " + product.getAmount());


        if (!Objects.isNull(shoppingCart)) {
            if (product.getAmount() >= cartListItem.getAmount()) { // больше равно количества того продукта на складе с тем что мы хотим
                // проверить корзину, есть ли в шопинг-листе уже данный продукт
                List<CartInput> shopList = shoppingCart.getShoppingList();
                Long newAmount = product.getAmount() - cartListItem.getAmount();

                product.setAmount(newAmount);
                this.productService.updateAmount(product.getId(), newAmount);


                if (checkShoppingListForProduct(cartListItem, shopList)) {
                    for (CartInput ci : shopList) {
                        if (ci.getProduct().getId().equals(cartListItem.getProductId())) {
                            ci.setAmount(ci.getAmount() + cartListItem.getAmount());
                            this.cartInputService.create(ci);
                        }
                    }

                } else {
                    // сделай апдейт продукта в базе данных, или удал его оттуда если количества нету???

                    CartInput cartInput = new CartInput(cartListItem.getAmount(), product);
                    this.cartInputService.create(cartInput);
                    shopList.add(cartInput);
                }

                if (newAmount == 0) {
                    this.productService.delete(cartListItem.getProductId());
                }


                // we need to update out shoppingCart shoppingList
                shoppingCart.setShoppingList(shopList);
                // and then we need to make change in repository
                System.out.println("ShoppingCart to update: " + shoppingCart.toString());
                this.shoppingRepository.save(shoppingCart);
                return shoppingCart;
//                }
                // if NOT OK -> создай новый продукт с данным количеством и запиши его в шопинг кард
            } else {
                throw new IllegalOperationException();
//                System.out.println("Producta neni v sklade.. ДОБАВИТЬ");
            }

        } else {
            System.out.println("ShoppingCart nie je.. ");

//            throw new NotFoundException();
        }
        return null;
    }

    private boolean checkShoppingListForProduct(CartListItem cartListItem, List<CartInput> shopList) {
        for (CartInput cartInput : shopList) {
            if (cartInput.getProduct().getId().equals(cartListItem.getProductId())) {
                return true;
            }
        }
        return false;
    }


}
