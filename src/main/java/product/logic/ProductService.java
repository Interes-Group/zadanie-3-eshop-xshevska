package product.logic;

import org.springframework.beans.factory.annotation.Autowired;
import product.data.IProductRepository;
import product.data.Product;

import java.util.List;

public class ProductService implements IProductService {
    @Autowired
    private IProductRepository repository;

    @Override
    public List<Product> getAll() {
        return null;
    }
}
