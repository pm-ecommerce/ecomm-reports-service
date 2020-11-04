package com.pm.ecommerce.reports_service.utils.dto;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.enums.OrderItemStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduledDeliveryDTO {
    private int order_id;
    private int user_id;
    private Address shipping_address;
    private double tax;
    private OrderItemStatus status;
    private LocalDateTime delivery_date;
    private List<OrderItemDTO> order_item_list;
}
