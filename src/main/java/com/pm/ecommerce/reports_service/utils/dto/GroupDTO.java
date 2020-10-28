package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class GroupDTO<T> {
    protected int count;
    protected double sum;
    public GroupDTO(){}
    public GroupDTO(Object[] objects){
        if(objects.length>1) {
            this.count = Integer.parseInt(objects[0].toString());
            this.sum = Double.parseDouble(objects[1].toString());
        }
    }
}
