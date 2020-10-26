package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class ProductGroupDTO<T> {
    protected int count;
    public ProductGroupDTO(){}
    public ProductGroupDTO(Object[] objects){
        if(objects.length>0) {
            this.count = Integer.parseInt(objects[0].toString());
        }
    }
}
