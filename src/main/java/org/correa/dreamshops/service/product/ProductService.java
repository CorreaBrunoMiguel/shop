package org.correa.dreamshops.service.product;

import lombok.RequiredArgsConstructor;
import org.correa.dreamshops.exceptions.ProductNotFound;
import org.correa.dreamshops.model.Category;
import org.correa.dreamshops.model.Product;
import org.correa.dreamshops.repository.CategoryRepository;
import org.correa.dreamshops.repository.ProductRepository;
import org.correa.dreamshops.request.AddProductRequest;
import org.correa.dreamshops.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional
                .ofNullable(
                        categoryRepository
                                .findByName(
                                        request
                                                .getCategory()
                                                .getName())
                )
                .orElseGet(
                        () -> {
                            Category newCategory = new Category(request.getCategory().getName());
                            return categoryRepository.save(newCategory);
                        }
                );
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(
                        () -> new ProductNotFound(
                                "Product not found."
                        ));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository
                .findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> {
                            throw new ProductNotFound(
                                    "Product not found"
                            );
                        }
                );
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository
                .findById(productId)
                .map(
                        existingProduct -> updateExistingProduct(existingProduct, request)
                )
                .map(
                      productRepository::save
                )
                .orElseThrow(
                        () -> new ProductNotFound(
                                "Product not found!"
                        )
                );
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName())  ;
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return  productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
