package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.Category;

import com.example.exercisejpa.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public boolean updateCategory(Integer id, Category category) {
        Category oldCategory = categoryRepository.getById(id);
        if (oldCategory == null) {
            return false;
        }
        for (int i = 0; i < categoryRepository.findAll().size(); i++) {
            if (categoryRepository.findAll().get(i).getId().equals(id)) {
                oldCategory.setName(category.getName());

            }
        }
        categoryRepository.save(oldCategory);
        return true;
    }

    public boolean deleteCategory(Integer id) {
        Category category = categoryRepository.getById(id);
        if (category == null) {
            return false;
        }
        categoryRepository.delete(category);
        return false;
    }
}
