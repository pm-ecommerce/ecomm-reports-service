package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class GroupByYearDTO extends GroupDTO {
    private int year;
    public GroupByYearDTO(Object[] objects){
        if(objects.length>2) {
            this.year = Integer.parseInt(objects[0].toString());
            this.count = Integer.parseInt(objects[1].toString());
            this.sum = Double.parseDouble(objects[2].toString());
        }
    }
}
