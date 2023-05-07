package sk.stuba.fei.uim.oop.assignment3.product.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.data.IProductRepository;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductRequest;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductUpdateRequest;

import java.util.List;
import java.util.Objects;


@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository repository;

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Product create(ProductRequest request) {
        return this.repository.save(new Product(request));
    }

    @Override
    public Product getById(Long id) throws NotFoundException {
        Product productById = this.repository.findProductById(id);
        if (productById == null) {
            throw new NotFoundException();
        }
        return productById;
    }

    @Override
    public Product update(Long id, ProductUpdateRequest updateRequest) throws NotFoundException {
        Product product = this.getById(id);

        if (Objects.nonNull(updateRequest.getName())) {
            product.setName(updateRequest.getName());
        }
        if (Objects.nonNull(updateRequest.getDescription())) {
            product.setDescription(updateRequest.getDescription());
        }

        return this.repository.save(product);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }


}
