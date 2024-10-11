package org.correa.dreamshops.service.product;

import org.correa.dreamshops.model.Product;
import org.correa.dreamshops.request.AddProductRequest;
import org.correa.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);

    void deleteProductById(Long id);

    List<Product> getAllProducts();
    List<Product> getProductByName(String name);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);
}
