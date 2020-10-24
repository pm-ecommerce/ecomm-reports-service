package com.pm.ecommerce.reports_service.utils.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ReportRequestDTO {
    HashMap<String, String> requestParams = new HashMap<>();
}

