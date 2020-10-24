package com.pm.ecommerce.reports_service.utils.enums;

public enum ReportRequestEnum {
    FROM_DATE("fromDate"),
    TO_DATE("toDate"),
    MIN_PRICE("minPrice"),
    MAX_PRICE("maxPrice"),
    MIN_COST("minCost"),
    MAX_COST("maxCost"),
    VENDOR_ID("vendorId");
    String value;

    public String value() {
        return this.value;
    }

    ReportRequestEnum(String value){
        this.value = value;
    }
}
