package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.ProductFactory;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.service.ProductService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {
    @Autowired
    private ProductService service;

    private static Product product;

    @BeforeAll
    static void setUp(@Autowired ProductService service) {
        // Create category
        Category category = CategoryFactory.createCategory(7568759L, "Anime Figures");

        // Fake image
        byte[] sampleImage = "fakeImageData".getBytes();

        // Create product using factory
        product = ProductFactory.createProduct(
                "Luffy Action Figure",
                299.99,
                64,
                sampleImage
        );
    }

    @Test
    @Order(1)
    void a_create() {
        Product created = service.create(product);

        assertNotNull(created);
        assertEquals("Luffy Action Figure", created.getName());
        assertEquals(299.99, created.getPrice());

        product = created; // update static reference with DB-generated ID
        System.out.println("Created: " + created);
    }



    @Test
    @Order(3)
    void c_update() {
        Product updatedProduct = new Product.Builder()
                .copy(product)
                .setPrice(349.99)
                .build();

        Product updated = service.update(updatedProduct);

        assertNotNull(updated);
        assertEquals(349.99, updated.getPrice());
        product = updated; // keep updated reference
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_delete() {
        boolean deleted = service.delete(product.getProductId());
        assertTrue(deleted);
        System.out.println("Deleted: " + deleted);
    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println("All Products: " + service.getAll());
    }

}