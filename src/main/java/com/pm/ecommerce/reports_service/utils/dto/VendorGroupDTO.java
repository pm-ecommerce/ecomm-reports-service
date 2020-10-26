package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class VendorGroupDTO<T> {
    protected int count;
    public VendorGroupDTO(){}
    public VendorGroupDTO(Object[] objects){
        if(objects.length>0) {
            this.count = Integer.parseInt(objects[0].toString());
        }
    }
}
