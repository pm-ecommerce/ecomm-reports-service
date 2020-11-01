package com.pm.ecommerce.reports_service.services;

import com.pm.ecommerce.reports_service.utils.dto.ReportRequestDTO;
import com.pm.ecommerce.reports_service.utils.dto.ReportResponseDTO;

public interface IReportService {
    ReportResponseDTO generateOrderReport(ReportRequestDTO reportRequestDTO);
    ReportResponseDTO generateVendorReport(ReportRequestDTO reportRequestDTO);
    ReportResponseDTO generateProductReport(ReportRequestDTO reportRequestDTO);
    ReportResponseDTO generateCategoryReport(ReportRequestDTO reportRequestDTO);
}
