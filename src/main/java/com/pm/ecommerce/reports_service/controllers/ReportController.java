package com.pm.ecommerce.reports_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.reports_service.services.ReportService;
import com.pm.ecommerce.reports_service.utils.Converter;
import com.pm.ecommerce.reports_service.utils.dto.ReportRequestDTO;
import com.pm.ecommerce.reports_service.utils.dto.ReportResponseDTO;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/data/order")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> generateDataSaleReport(HttpServletRequest httpServletRequest) throws IOException, JRException, SQLException {
        ApiResponse<ReportResponseDTO> response = new ApiResponse<>();
        try{
            //setup parameters
            ReportRequestDTO reportRequestDTO = Converter.convert(httpServletRequest);
            //generate sale report
            ReportResponseDTO reportResponseDTO = reportService.generateReport(reportRequestDTO);

            response.setData(reportResponseDTO);
            response.setMessage("Successfully");
            //response.setStatus();
        }catch (Exception e){
            response.setStatus(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
