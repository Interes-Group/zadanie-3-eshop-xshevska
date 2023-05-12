package sk.stuba.fei.uim.oop.assignment3.cart.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart findShoppingCartById(Long id);

}
