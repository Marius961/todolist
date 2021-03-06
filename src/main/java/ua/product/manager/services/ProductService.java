package ua.product.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.product.manager.entities.Product;
import ua.product.manager.entities.Subcategory;
import ua.product.manager.entities.User;
import ua.product.manager.exceptions.NotFoundException;
import ua.product.manager.exceptions.ObjectExistException;
import ua.product.manager.repo.MeasurementUnitRepo;
import ua.product.manager.repo.ProductRepo;
import ua.product.manager.repo.SubcategoryRepo;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import static ua.product.manager.Specifications.ProductSpecification.*;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private ImgService imgService;
    private UserService userService;
    private SubcategoryRepo subcategoryRepo;
    private MeasurementUnitRepo measurementUnitRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, ImgService imgService, UserService userService, SubcategoryRepo subcategoryRepo, MeasurementUnitRepo measurementUnitRepo) {
        this.productRepo = productRepo;
        this.imgService = imgService;
        this.userService = userService;
        this.subcategoryRepo = subcategoryRepo;
        this.measurementUnitRepo = measurementUnitRepo;
    }

    public void addProduct(MultipartFile file, Product product, Principal principal) throws IOException, ObjectExistException {
        boolean isProductNameExist = isProductExist(product.getName());
        boolean isMeasurementUnitExist = measurementUnitRepo.existsById(product.getMeasurementUnit().getId());
        if (!isProductNameExist && isMeasurementUnitExist) {
            product.setImageName(imgService.saveImage(file));
            User user = (User) userService.loadUserByUsername(principal.getName());
            product.setUser(user);
            productRepo.save(product);
        } else throw new ObjectExistException("Product with name " + product.getName() + " already exist");
    }

    public Page<Product> getProducts(int page, int size, Long subcategoryId, Double minPrice, Double maxPrice) throws NotFoundException {
        if (page > -1 && size > -1) {
            if (subcategoryRepo.existsById(subcategoryId)) {
                Specification<Product> endSpecification = productsByCategoryId(subcategoryId);

                if (minPrice != null) {
                    endSpecification = endSpecification.and(productsByMinPrice(minPrice));
                }
                if (maxPrice != null) {
                    endSpecification = endSpecification.and(productsByMaxPrice(maxPrice));
                }
                return productRepo.findAll(endSpecification, PageRequest.of(page, size));
            } else throw new NotFoundException("Unable to find subcategory with id " + subcategoryId);
        } else throw new IllegalArgumentException("Page number and size must be greater than 0");
    }

    public Product getProductById(Long id) throws NotFoundException {
        Optional<Product> opProduct = productRepo.findById(id);
        if (opProduct.isPresent()) {
            return opProduct.get();
        } else throw new NotFoundException("Cannot find product with id " + id);
    }

    public boolean isProductExist(String name) {
        return productRepo.existsByName(name);
    }
}
