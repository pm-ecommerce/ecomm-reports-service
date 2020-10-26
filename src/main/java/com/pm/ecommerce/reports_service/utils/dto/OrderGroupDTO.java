package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderGroupDTO<T> {
    protected int count;
    protected double sum;
    protected double min;
    protected double max;
    protected double avg;
    public OrderGroupDTO(){}
    public OrderGroupDTO(Object[] objects){
        this.count=Integer.parseInt(objects[0].toString());
        this.sum=Double.parseDouble(objects[1].toString());
        this.min=Double.parseDouble(objects[2].toString());
        this.max=Double.parseDouble(objects[3].toString());
        this.avg=Double.parseDouble(objects[4].toString());
    }
}
