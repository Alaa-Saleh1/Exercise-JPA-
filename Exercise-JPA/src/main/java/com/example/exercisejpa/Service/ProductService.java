package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.Category;
import com.example.exercisejpa.Model.Product;
import com.example.exercisejpa.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    public Boolean addProduct(Product product) {
        for (int i = 0; i < categoryService.getAllCategories().size(); i++) {
            if (product.getCategoryID().equals(categoryService.getAllCategories().get(i).getId())) {
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    public Integer updateProduct(Integer id, Product updatedProduct) {
            Boolean checkcategory = false;
            Product oldproduct = productRepository.getById(id);
            if (oldproduct ==null){
                return 0;
            }
            for (Category category : categoryService.getAllCategories()) {
                if (updatedProduct.getCategoryID().equals(category.getId())) {
                    checkcategory = true;
                    break;
                }
            }
            if (!checkcategory) {
                return 1;
            }
        for (int i = 0; i < productRepository.findAll().size(); i++) {
            if (oldproduct.getCategoryID().equals(productRepository.findAll().get(i).getId())) {
                oldproduct.setName(updatedProduct.getName());
                oldproduct.setCategoryID(updatedProduct.getCategoryID());
                oldproduct.setPrice(updatedProduct.getPrice());
            }
        }
        productRepository.save(oldproduct);
        return 2;
        }




    public Boolean deleteProduct(Integer id){
        Product product = productRepository.getById(id);
        if (product == null){
            return false;
        }
        productRepository.delete(product);
        return true;
    }

    //method 2 for endpoint 2
    public List<Product> getNewlyAddedProducts(LocalDate fromDate) {
        ArrayList<Product> newlyAddedProducts = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            if (product.getDateAdded().isAfter(fromDate)) {
                newlyAddedProducts.add(product);
            }
        }
        return newlyAddedProducts;
    }


    public List searchProductsByName(String name) {
        List result = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            if (product.getName().equalsIgnoreCase(name)) {
                result.add(product);
            }
        }
        return result;
    }

    public List filterProductsByPriceRange(double minPrice, double maxPrice) {
        List result = new ArrayList<>();
        for (int i = 0; i < productRepository.findAll().size(); i++) {
            if (productRepository.findAll().get(i).getPrice() >= minPrice && productRepository.findAll().get(i).getPrice() <= maxPrice) {
                result.add(productRepository.findAll().get(i));
            }
        }
        return result;
    }



}

