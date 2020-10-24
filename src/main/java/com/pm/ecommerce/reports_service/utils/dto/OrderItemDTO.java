package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private int order_item_id;
    private int order_id;
    private int product_id;
    private String product_name;
    private int category_id;
    private String category_name;
    private int vendor_id;
    private String vendor_name;
    private int quantity;
    private double price;
}
