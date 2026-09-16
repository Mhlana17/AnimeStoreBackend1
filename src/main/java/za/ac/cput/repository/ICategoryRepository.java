package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Category;
import java.util.List;

/*
 * ICategoryRepository.java
 * Author: Mbasa Mcakumba (241080371)
 */
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}