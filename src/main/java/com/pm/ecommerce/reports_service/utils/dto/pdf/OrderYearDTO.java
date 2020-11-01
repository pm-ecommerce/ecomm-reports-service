package com.pm.ecommerce.reports_service.utils.dto.pdf;

import lombok.Data;

@Data
public class OrderYearDTO {
    private int total;
    private String year;
    public OrderYearDTO(Object[] objects){
        if(objects.length>1){
            this.total = Integer.parseInt(objects[0].toString());
            this.year = objects[1].toString();
        }
    }
}
