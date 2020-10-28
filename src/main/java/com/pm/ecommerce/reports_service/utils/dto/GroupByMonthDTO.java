package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

@Data
public class GroupByMonthDTO extends GroupDTO {
    String month;
    public GroupByMonthDTO(Object[] objects){
        if(objects.length>2) {
            this.month = objects[0].toString();
            this.count = Integer.parseInt(objects[1].toString());
            this.sum = Double.parseDouble(objects[2].toString());
        }
    }
}
