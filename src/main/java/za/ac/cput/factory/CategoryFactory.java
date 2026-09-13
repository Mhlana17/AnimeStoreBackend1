package za.ac.cput.factory;

import za.ac.cput.domain.Category;

/**
 * CategoryFactory
 * Author: Mbasa Mcakumba 241080371
 * Date: 24 March 2026
 */
public class CategoryFactory {

    public static Category createCategory(Long categoryId, String name) {

        return new Category.Builder()
                .setCategoryId(categoryId)
                .setName(name)
                .build();
    }
}