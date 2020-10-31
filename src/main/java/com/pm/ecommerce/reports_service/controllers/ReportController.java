package com.pm.ecommerce.reports_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.reports_service.services.ReportService;
import com.pm.ecommerce.reports_service.utils.Converter;
import com.pm.ecommerce.reports_service.utils.dto.ReportRequestDTO;
import com.pm.ecommerce.reports_service.utils.dto.ReportResponseDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/data")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> generateDataReport(HttpServletRequest httpServletRequest) {
        ApiResponse<ReportResponseDTO> response = new ApiResponse<>();
        try{
            //setup parameters
            ReportRequestDTO reportRequestDTO = Converter.convert(httpServletRequest);
            //generate sale report
            ReportResponseDTO reportResponseDTO = reportService.generateReport((reportRequestDTO));

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

    @GetMapping("/data/order")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> generateDataOrderReport(HttpServletRequest httpServletRequest)  {
        ApiResponse<ReportResponseDTO> response = new ApiResponse<>();
        try{
            //setup parameters
            ReportRequestDTO reportRequestDTO = Converter.convert(httpServletRequest);
            //generate sale report
            ReportResponseDTO reportResponseDTO = reportService.generateOrderReport(reportRequestDTO);

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

    @GetMapping("/data/product")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> generateDataProductReport(HttpServletRequest httpServletRequest)  {
        ApiResponse<ReportResponseDTO> response = new ApiResponse<>();
        try{
            //setup parameters
            ReportRequestDTO reportRequestDTO = Converter.convert(httpServletRequest);
            //generate sale report
            ReportResponseDTO reportResponseDTO = reportService.generateProductReport(reportRequestDTO);

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

    @GetMapping("/data/vendor")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> generateDataVendorReport(HttpServletRequest httpServletRequest) {
        ApiResponse<ReportResponseDTO> response = new ApiResponse<>();
        try{
            //setup parameters
            ReportRequestDTO reportRequestDTO = Converter.convert(httpServletRequest);
            //generate sale report
            ReportResponseDTO reportResponseDTO = reportService.generateVendorReport(reportRequestDTO);

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

    @GetMapping("/data/category")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> generateDataCategoryReport(HttpServletRequest httpServletRequest) {
        ApiResponse<ReportResponseDTO> response = new ApiResponse<>();
        try{
            //setup parameters
            ReportRequestDTO reportRequestDTO = Converter.convert(httpServletRequest);
            //generate sale report
            ReportResponseDTO reportResponseDTO = reportService.generateCategoryReport(reportRequestDTO);

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

    @GetMapping("/pdf/full-report")
    public String generatePdfReportForAdmin(HttpServletResponse response) throws IOException, JRException{
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        // Generating report using List<JasperPrint>
        List<JasperPrint> jasperPrintList = reportService.generateJasperPrintsForAdmin();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        out.flush();
        out.close();
        return "success";
    }

    @GetMapping("/pdf/full-report/{vendorId}")
    public String generatePdfReportForVendor(@PathVariable(name="vendorId", required = true) Integer vendorId, HttpServletResponse response) throws IOException, JRException{
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        //Generating report using List<JasperPrint>
        List<JasperPrint> jasperPrintList = reportService.generateJasperPrintsForVendor(vendorId);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        out.flush();
        out.close();
        return "success";
    }
}
