package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class VendorGroupDTO<T> {
    protected int count;
    public VendorGroupDTO(){}
    public VendorGroupDTO(Object[] objects){
        this.count=Integer.parseInt(objects[1].toString());
    }
}
