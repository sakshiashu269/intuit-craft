package com.craft.manageOrders.invoice;

import com.craft.manageOrders.order.Order;
import com.craft.manageOrders.user.User;

public interface InvoiceService {
    Invoice generateInvoice(Order order);
}
