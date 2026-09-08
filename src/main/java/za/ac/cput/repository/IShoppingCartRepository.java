package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.ShoppingCart;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, String> {

}