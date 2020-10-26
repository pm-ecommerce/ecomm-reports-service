package com.pm.ecommerce.reports_service.services;

import com.pm.ecommerce.reports_service.utils.dto.OrderDTO;
import com.pm.ecommerce.reports_service.utils.dto.ReportRequestDTO;
import com.pm.ecommerce.reports_service.utils.dto.ReportResponseDTO;

import java.util.List;

public interface IReportService {
    public ReportResponseDTO generateOrderReport(ReportRequestDTO reportRequestDTO);
    public ReportResponseDTO generateVendorReport(ReportRequestDTO reportRequestDTO);
    public ReportResponseDTO generateProductReport(ReportRequestDTO reportRequestDTO);
}
