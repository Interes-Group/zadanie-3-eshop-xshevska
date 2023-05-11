package sk.stuba.fei.uim.oop.assignment3.cart.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;

public interface ICartInputRepository extends JpaRepository<CartInput, Long> {

    Product findByProduct_Id(Long id);


}
