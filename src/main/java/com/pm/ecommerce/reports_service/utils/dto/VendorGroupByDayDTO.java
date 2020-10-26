package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class VendorGroupByDayDTO extends VendorGroupDTO {
    private int day;
    public VendorGroupByDayDTO(Object[] objects){
        this.day=Integer.parseInt(objects[0].toString());
        this.count=Integer.parseInt(objects[1].toString());
    }
}
