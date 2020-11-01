package com.pm.ecommerce.reports_service.utils.dto.pdf;

import lombok.Data;

@Data
public class OrderProductDTO {
    private int total;
    private String name;
    public OrderProductDTO(Object[] objects){
        if(objects.length>1){
            this.total = Integer.parseInt(objects[0].toString());
            this.name = objects[1].toString();
        }
    }
}
