package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class ProductGroupDTO<T> {
    protected int count;
    public ProductGroupDTO(){}
    public ProductGroupDTO(Object[] objects){
        this.count=Integer.parseInt(objects[1].toString());
    }
}
