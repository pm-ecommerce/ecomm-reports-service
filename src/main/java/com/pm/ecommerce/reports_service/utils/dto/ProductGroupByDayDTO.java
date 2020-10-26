package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class ProductGroupByDayDTO extends ProductGroupDTO {
    private int day;
    public ProductGroupByDayDTO(Object[] objects){
        if(objects.length>1) {
            this.day = Integer.parseInt(objects[0].toString());
            this.count = Integer.parseInt(objects[1].toString());
        }
    }
}
