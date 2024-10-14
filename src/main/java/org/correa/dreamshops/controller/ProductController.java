package org.correa.dreamshops.controller;

import lombok.RequiredArgsConstructor;
import org.correa.dreamshops.exceptions.ResourceNotFoundException;
import org.correa.dreamshops.model.Product;
import org.correa.dreamshops.request.AddProductRequest;
import org.correa.dreamshops.request.ProductUpdateRequest;
import org.correa.dreamshops.response.ApiResponse;
import org.correa.dreamshops.service.product.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity
                .ok(new ApiResponse(
                        "Success",
                        products
                ));
    }

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(
            @PathVariable Long id
    ) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Success",
                            product
                    ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(
                            e.getMessage(),
                            null
                    ));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(
            @RequestBody AddProductRequest product
    ) {
        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Add product success",
                            newProduct
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            e.getMessage(),
                            null
                    ));
        }
    }

    @PutMapping("product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestBody ProductUpdateRequest request,
            @PathVariable Long productId
    ) {
        try {
            Product theProduct = productService.updateProduct(request, productId);
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Update Product Success.",
                            theProduct
                    ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(
                            e.getMessage(),
                            null
                    ));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long productId
    ) {
        try {
            productService
                    .deleteProductById(productId);
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Delete product success",
                            productId
                    ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)

                    .body(new ApiResponse(
                            e.getMessage(),
                            null
                    ));
        }
    }

    @GetMapping("/peoducts/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(
            @RequestParam String brandName,
            @RequestParam String productName
    ) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(
                    brandName,
                    productName
            );
            if (products.isEmpty())
                return ResponseEntity
                        .status(NOT_FOUND)
                        .body(new ApiResponse(
                                "No product found with name " + productName,
                                null
                        ));
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Success",
                            products
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            e.getMessage(),
                            null
                    ));
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(
            @RequestParam String categoryName,
            @RequestParam String brandName
    ) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            if (products.isEmpty())
                return ResponseEntity
                        .status(NOT_FOUND)
                        .body(new ApiResponse(
                                "No product found.",
                                null
                        ));
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Success",
                            products
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            e.getMessage(),
                            null
                    ));
        }
    }

    @GetMapping("/product/{name}/products")
    public ResponseEntity<ApiResponse> getProductsByName(
            @PathVariable String name
    ) {
        try {
            List<Product> products = productService.getProductByName(name);
            if (products.isEmpty())
                return ResponseEntity
                        .status(NOT_FOUND)
                        .body(new ApiResponse(
                                "No products found",
                                null
                        ));
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Success",
                            products
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            "erroe",
                            e.getMessage()
                    ));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

}
