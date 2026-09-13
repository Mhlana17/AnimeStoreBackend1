package za.ac.cput.repository;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.OrderItem;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, String> {
    // JpaRepository provides all CRUD methods
    List<OrderItem> findByItemDescription(String description);
    OrderItem findByOrderItemId(String orderId);
}