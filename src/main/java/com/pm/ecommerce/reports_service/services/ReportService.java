package com.pm.ecommerce.reports_service.services;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.reports_service.repositories.OrderRepository;
import com.pm.ecommerce.reports_service.utils.dto.OrderDTO;
import com.pm.ecommerce.reports_service.utils.dto.ReportRequestDTO;
import com.pm.ecommerce.reports_service.utils.dto.ReportResponseDTO;
import com.pm.ecommerce.reports_service.utils.enums.ReportRequestEnum;
import com.pm.ecommerce.reports_service.utils.enums.ReportResponseEnum;
import com.pm.ecommerce.reports_service.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportService implements  IReportService{

    @Autowired
    private OrderRepository orderRepository;

    public ReportResponseDTO generateReport(ReportRequestDTO reportRequestDTO){

        //check input params
        String vendorId=null;
        Timestamp fromDate=null, toDate=null;
        String minPrice=null, maxPrice=null, minCost=null, maxCost=null;
        HashMap<String, String> requestParams = reportRequestDTO.getRequestParams();
        if(requestParams.containsKey(ReportRequestEnum.FROM_DATE.value())){
            fromDate = Converter.convert(requestParams.get(ReportRequestEnum.FROM_DATE.value()));
        }
        if(requestParams.containsKey(ReportRequestEnum.TO_DATE.value())){
            toDate = Converter.convert(requestParams.get(ReportRequestEnum.TO_DATE.value()));
        }
        if(requestParams.containsKey(ReportRequestEnum.VENDOR_ID.value())){
            vendorId = requestParams.get(ReportRequestEnum.VENDOR_ID.value());
        }
        if(requestParams.containsKey(ReportRequestEnum.MIN_PRICE.value())){
            minPrice = requestParams.get(ReportRequestEnum.MIN_PRICE.value());
        }
        if(requestParams.containsKey(ReportRequestEnum.MAX_PRICE.value())){
            maxPrice = requestParams.get(ReportRequestEnum.MAX_PRICE.value());
        }
        if(requestParams.containsKey(ReportRequestEnum.MIN_COST.value())){
            minCost = requestParams.get(ReportRequestEnum.MIN_COST.value());
        }
        if(requestParams.containsKey(ReportRequestEnum.MAX_COST.value())){
            maxCost = requestParams.get(ReportRequestEnum.MAX_COST.value());
        }
        //process
        List<Order> orderList = orderRepository.findOrderByReportRequest(fromDate,toDate,vendorId,minPrice,maxPrice,minCost,maxCost);
        List<OrderDTO> orderDTOList = Converter.convert(orderList);

        //output params
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        HashMap<String, Object> responseParams = reportResponseDTO.getResponseData();
        responseParams.put(ReportResponseEnum.TOTAL.value(),orderDTOList.size());
        responseParams.put(ReportResponseEnum.LIST.value(),orderDTOList);

        return reportResponseDTO;
    }

    public ReportResponseDTO generateJasperReport(ReportRequestDTO reportRequestDTO){
        return null;
    }
}
