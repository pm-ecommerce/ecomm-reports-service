package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class GroupByDayDTO extends GroupDTO {
    private int day;
    public GroupByDayDTO(Object[] objects){
        if(objects.length>2) {
            this.day = Integer.parseInt(objects[0].toString());
            this.count = Integer.parseInt(objects[1].toString());
            this.sum = Double.parseDouble(objects[2].toString());
        }
    }
}
