package com.pm.ecommerce.reports_service.utils.dto;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private int order_id;
    private int user_id;
    private Address billing_address_id;
    private Address shipping_address_id;
    private double tax;
    private OrderStatus status;
    private LocalDateTime order_date;
    private List<OrderItemDTO> order_item_list;
}
