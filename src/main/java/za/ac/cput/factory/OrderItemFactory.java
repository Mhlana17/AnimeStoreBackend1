package za.ac.cput.factory;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import za.ac.cput.domain.OrderItem;
import za.ac.cput.util.Helper;

public class OrderItemFactory {
    public static OrderItem createOrderItem(String orderItemId,
                                            String itemDescription,
                                            int itemQuantity,
                                            double unitPrice) {

        if (Helper.isNullOrEmpty(orderItemId) ||
                Helper.isNullOrEmpty(itemDescription) ||
                !Helper.isValidQuantity(itemQuantity) ||
                !Helper.isValidPrice(unitPrice)||
                orderItemId.isEmpty()) {
            return null;
        }
        return new OrderItem.Builder()
                .setOrderItemId(orderItemId)
                .setItemDescription(itemDescription)
                .setItemQuantity(itemQuantity)
                .setUnitPrice(unitPrice)
                .build();
    }
}