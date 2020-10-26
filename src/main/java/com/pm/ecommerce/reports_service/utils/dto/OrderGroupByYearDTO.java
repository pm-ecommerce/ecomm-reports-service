package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class OrderGroupByYearDTO extends OrderGroupDTO {
    private int year;
    public OrderGroupByYearDTO(Object[] objects){
        this.year=Integer.parseInt(objects[0].toString());
        this.count=Integer.parseInt(objects[1].toString());
        this.sum=Double.parseDouble(objects[2].toString());
        this.min=Double.parseDouble(objects[3].toString());
        this.max=Double.parseDouble(objects[4].toString());
        this.avg=Double.parseDouble(objects[5].toString());
    }
}
