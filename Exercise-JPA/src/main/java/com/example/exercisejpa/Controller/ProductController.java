package com.example.exercisejpa.Controller;

import com.example.exercisejpa.ApiResponse.ApiResponse;
import com.example.exercisejpa.Model.Product;
import com.example.exercisejpa.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/amazon-product")
public class ProductController {


    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProduct(){
        List product = productService.getProducts();
        return ResponseEntity.status(200).body(product);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addProudect(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Boolean idAdded = productService.addProduct(product);
        if (idAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Product has been added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Check Id of category"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Integer isUpdate = productService.updateProduct(id,product);
        if (isUpdate ==0 ){
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        if (isUpdate == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("Category does not exist"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product has been updated successfully"));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        boolean isDeleted = productService.deleteProduct(id);

        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Product has been deleted successfully"));
        }
        return ResponseEntity.status(400).body("Product not found");

    }

    //endpoint 2
    @GetMapping("/newly-added/{fromDate}")
    public ResponseEntity<?> getNewlyAddedProducts(@PathVariable LocalDate fromDate) {

        List<Product> newlyAddedProducts = productService.getNewlyAddedProducts(fromDate);

        if (newlyAddedProducts.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No products found"));
        }
        return ResponseEntity.status(200).body(newlyAddedProducts);
    }





    //endpoint 5
    @GetMapping("/searchByName/{name}")
    public ResponseEntity<?> searchProductsByName(@PathVariable String name) {
        List products = productService.searchProductsByName(name);
        if (products.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No products found"));
        }
        return ResponseEntity.status(200).body(products);
    }

    //endpoint 4
    @GetMapping("/filter-price/{min}/{max}")
    public ResponseEntity<?> filterProductsByPriceRange(@PathVariable double min, @PathVariable double max) {
        List result = productService.filterProductsByPriceRange(min, max);
        if (result.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No products found"));
        }
        return ResponseEntity.status(200).body(result);
    }

}
