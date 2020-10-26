package com.pm.ecommerce.reports_service.utils.dto.pdf;

import lombok.Data;

@Data
public class OrderStateDTO {
    private int total;
    private String state;
    public OrderStateDTO(Object[] objects){
        if(objects.length>1){
            this.total = Integer.parseInt(objects[0].toString());
            this.state = objects[1].toString();
        }
    }
}
