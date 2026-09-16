package za.ac.cput.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.CategoryFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void testSave() {
        Category category = CategoryFactory.createCategory(1L, "Anime Figures");
        Category saved = categoryService.save(category);
        assertNotNull(saved);
        assertEquals("Anime Figures", saved.getName());
        System.out.println("Saved: " + saved);
    }

    @Test
    void testGetById() {
        Category category = CategoryFactory.createCategory(2L, "Manga");
        categoryService.save(category);
        Category read = categoryService.getById(category.getCategoryId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    void testGetAll() {
        List<Category> categories = categoryService.getAll();
        assertNotNull(categories);
        System.out.println("All Categories: " + categories);
    }

    @Test
    void testDelete() {
        Category category = CategoryFactory.createCategory(3L, "Posters");
        categoryService.save(category);
        boolean deleted = categoryService.delete(category.getCategoryId());
        assertTrue(deleted);
        System.out.println("Deleted successfully");
    }
}