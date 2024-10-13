package org.correa.dreamshops.controller;

import lombok.RequiredArgsConstructor;
import org.correa.dreamshops.exceptions.AlreadyExistsException;
import org.correa.dreamshops.exceptions.ResourceNotFoundException;
import org.correa.dreamshops.model.Category;
import org.correa.dreamshops.response.ApiResponse;
import org.correa.dreamshops.service.category.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Found!",
                            categories
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    "Error:",
                                    INTERNAL_SERVER_ERROR
                            )
                    );
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(
            @RequestBody Category name
    ) {
        try {
            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Success",
                            theCategory
                    ));
        } catch (AlreadyExistsException e) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new ApiResponse(
                            e.getMessage(),
                            null
                    ));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(
            @PathVariable Long id
    ){
        Category theCategory = categoryService.getCategoryById(id);
        try {
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Found",
                            theCategory
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

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(
            @PathVariable String name
    ){
        Category theCategory = categoryService.getCategoryByName(name);
        try {
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Found",
                            theCategory
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

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id
    ){
        categoryService.deleteCategory(id);
        try {
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Deleted",
                            null
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

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    ){
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity
                    .ok(new ApiResponse(
                            "Update success.",
                            updatedCategory
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
}
