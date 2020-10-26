package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class ProductGroupByYearMonthDTO extends ProductGroupDTO {
    String year_month;
    public ProductGroupByYearMonthDTO(Object[] objects){
        if(objects.length>1) {
            this.year_month = objects[0].toString();
            this.count = Integer.parseInt(objects[1].toString());
        }
    }
}
