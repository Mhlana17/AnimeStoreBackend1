package za.ac.cput.factory;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.OrderItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemFactoryTest {
    OrderItem item1;
@BeforeEach
void setUp() {
    item1 = OrderItemFactory.createOrderItem("0k002","Pokemon comic book",1,15.00);
}
    @Test
    void createOrderItem() {
        assertEquals("0k002",item1.getOrderItemId());
        assertEquals("Pokemon comic book",item1.getItemDescription());
        System.out.println(item1.toString());
    }


}