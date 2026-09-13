package za.ac.cput.service;

import za.ac.cput.domain.Category;

import java.util.List;

public interface ICategoryService {
    Category save(Category category);
    Category getById(Long id); // MAKE SURE THIS EXISTS
    List<Category> getAll();
    boolean delete(Long id);
}