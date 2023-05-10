package sk.stuba.fei.uim.oop.assignment3.product.logic;

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
    private final IProductRepository repository;

    public ProductService(IProductRepository repository) {
        this.repository = repository;
    }

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
        if (Objects.isNull(productById)) {
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

    @Override
    public Long getAmount(Long id) throws NotFoundException {
        return this.getById(id).getAmount();
    }

    @Override
    public Long addAmount(Long id, Long increment) throws NotFoundException {
        Product product = this.getById(id);
        product.setAmount(product.getAmount() + increment);
        this.repository.save(product);
        return product.getAmount();
    }


}
