package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class VendorGroupByYearDTO extends VendorGroupDTO {
    private int year;
    public VendorGroupByYearDTO(Object[] objects){
        this.year=Integer.parseInt(objects[0].toString());
        this.count=Integer.parseInt(objects[1].toString());
    }
}
