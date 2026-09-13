package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;
import static org.junit.jupiter.api.Assertions.*;

/*
 * CategoryFactoryTest.java
 * Author: Mbasa Mcakumba (241080371)
 */
class CategoryFactoryTest {

    @Test
    void testBuildCategory() {
        Category category = CategoryFactory.createCategory(7568759L, "Hoodies");

        assertNotNull(category);
        System.out.println(category.toString());
    }
}