package com.pm.ecommerce.reports_service.utils.enums;

public enum ReportResponseEnum {
    TOTAL("total"),
    LIST("list");
    String value;

    public String value() {
        return this.value;
    }

    ReportResponseEnum(String value){
        this.value = value;
    }
}
