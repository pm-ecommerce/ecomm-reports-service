package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class VendorGroupByYearMonthDTO extends VendorGroupDTO {
    String year_month;
    public VendorGroupByYearMonthDTO(Object[] objects){
        this.year_month=objects[0].toString();
        this.count=Integer.parseInt(objects[1].toString());
    }
}
