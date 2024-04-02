package com.craft.manageOrders.invoice;

import com.craft.manageOrders.order.Order;
import com.craft.manageOrders.order.OrderController;
import com.craft.manageOrders.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final Invoice invoice;
    Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    public InvoiceServiceImpl(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public Invoice generateInvoice(Order order) {
        String invoiceId = UUID.randomUUID().toString();
        invoice.setInvoiceId(invoiceId);
        invoice.setUserId(order.getUserId());
        invoice.setOrderId(order.getOrderId());
        invoice.setAddress(order.getAddress());
        invoice.setProductVsUnits(order.getProductVsUnits());
        invoice.setTotalItemPrice(order.getBillAmount());
        logger.info("Invoice is generated for invoiceId: "+ invoiceId);
        return invoice;
    }
}
