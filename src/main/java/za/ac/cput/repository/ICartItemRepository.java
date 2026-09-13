package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.CartItem;

public interface ICartItemRepository extends JpaRepository<CartItem, String> {
}