package za.ac.cput.repository;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Order;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, String> {
    List<Order> findByStatus(String status);
    List<Order> findByOrderId(String orderId);
}