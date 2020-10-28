package com.pm.ecommerce.reports_service.utils.dto.pdf;

import lombok.Data;

@Data
public class OrderMonthDTO {
    private int total;
    private String month;
    public OrderMonthDTO(Object[] objects){
        if(objects.length>1){
            this.total = Integer.parseInt(objects[0].toString());
            this.month = objects[1].toString();
        }
    }
}
