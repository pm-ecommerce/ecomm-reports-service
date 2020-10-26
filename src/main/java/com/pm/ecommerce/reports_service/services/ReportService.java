package com.pm.ecommerce.reports_service.services;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.reports_service.repositories.OrderRepository;
import com.pm.ecommerce.reports_service.repositories.ProductRepository;
import com.pm.ecommerce.reports_service.repositories.VendorRepository;
import com.pm.ecommerce.reports_service.utils.dto.*;
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
public class ReportService implements IReportService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VendorRepository vendorRepository;

    String vendorId=null;
    Timestamp fromDate=null, toDate=null;
    String minCost=null, maxCost=null;
    String groupBy=null;

    private void checkInputParams(HashMap<String, String> requestParams){
        if(requestParams.containsKey(ReportRequestEnum.FROM_DATE.value())){
            fromDate = Converter.convert(requestParams.get(ReportRequestEnum.FROM_DATE.value()));
        }
        if(requestParams.containsKey(ReportRequestEnum.TO_DATE.value())){
            toDate = Converter.convert(requestParams.get(ReportRequestEnum.TO_DATE.value()));
        }
        if(requestParams.containsKey(ReportRequestEnum.VENDOR_ID.value())){
            vendorId = requestParams.get(ReportRequestEnum.VENDOR_ID.value());
        }
        if(requestParams.containsKey(ReportRequestEnum.MIN_COST.value())){
            minCost = requestParams.get(ReportRequestEnum.MIN_COST.value());
        }
        if(requestParams.containsKey(ReportRequestEnum.MAX_COST.value())){
            maxCost = requestParams.get(ReportRequestEnum.MAX_COST.value());
        }
    }

    public ReportResponseDTO generateOrderReport(ReportRequestDTO reportRequestDTO){

        //check input params
        HashMap<String, String> requestParams = reportRequestDTO.getRequestParams();
        checkInputParams(requestParams);
        //process
        List<Order> orderList = orderRepository.findOrderByReportRequest(fromDate,toDate,vendorId,minCost,maxCost);
        List<OrderDTO> orderDTOList = Converter.convert(orderList);

        //output params
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        HashMap<String, Object> responseParams = reportResponseDTO.getResponseData();
        responseParams.put(ReportResponseEnum.TOTAL.value(),orderDTOList.size());
        responseParams.put(ReportResponseEnum.LIST.value(),orderDTOList);

        return reportResponseDTO;
    }

    public ReportResponseDTO generateOrderReportGroupBy(ReportRequestDTO reportRequestDTO){

        //check input params
        HashMap<String, String> requestParams = reportRequestDTO.getRequestParams();
        checkInputParams(requestParams);
        if(requestParams.containsKey(ReportRequestEnum.GROUP_BY.value())){
            groupBy = requestParams.get(ReportRequestEnum.GROUP_BY.value());
        }
        //process
        List<Object[]> list = null;
        List<OrderGroupDTO> dtoList = new ArrayList<>();
        if(groupBy!=null && groupBy.equals(ReportRequestEnum.YEAR.value())) {
            list = orderRepository.findOrdersByReportRequestWithGroupByYear(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new OrderGroupByYearDTO(objects));
            }
        } else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.MONTH.value())) {
            list = orderRepository.findOrdersByReportRequestWithGroupByYearMonth(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new OrderGroupByYearMonthDTO(objects));
            }
        }else if(groupBy!=null && groupBy.equals(ReportRequestEnum.DAY.value())) {
            list = orderRepository.findOrdersByReportRequestWithGroupByDay(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new OrderGroupByDayDTO(objects));
            }
        }else {
            list = orderRepository.findOrdersByReportRequestWithoutGroupBy(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new OrderGroupDTO(objects));
            }
        }
        //output params
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        HashMap<String, Object> responseParams = reportResponseDTO.getResponseData();
        responseParams.put(ReportResponseEnum.TOTAL.value(),dtoList.size());
        responseParams.put(ReportResponseEnum.LIST.value(),dtoList);

        return reportResponseDTO;
    }



    public ReportResponseDTO generateVendorReport(ReportRequestDTO reportRequestDTO){
        //check input params
        HashMap<String, String> requestParams = reportRequestDTO.getRequestParams();
        checkInputParams(requestParams);
        if(requestParams.containsKey(ReportRequestEnum.GROUP_BY.value())){
            groupBy = requestParams.get(ReportRequestEnum.GROUP_BY.value());
        }
        //process
        List<Object[]> list = null;
        List<VendorGroupDTO> dtoList = new ArrayList<>();
        if(groupBy!=null && groupBy.equals(ReportRequestEnum.YEAR.value())) {
            list = vendorRepository.findVendorByReportRequestWithGroupByYear(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new VendorGroupByYearDTO(objects));
            }
        } else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.MONTH.value())) {
            list = vendorRepository.findVendorByReportRequestWithGroupByYearMonth(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new VendorGroupByYearMonthDTO(objects));
            }
        }else if(groupBy!=null && groupBy.equals(ReportRequestEnum.DAY.value())) {
            list = vendorRepository.findVendorByReportRequestWithGroupByDay(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new VendorGroupByDayDTO(objects));
            }
        }else {
            list = vendorRepository.findVendorByReportRequestWithoutGroupBy(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new VendorGroupByDayDTO(objects));
            }
        }
        //output params
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        HashMap<String, Object> responseParams = reportResponseDTO.getResponseData();
        responseParams.put(ReportResponseEnum.TOTAL.value(),dtoList.size());
        responseParams.put(ReportResponseEnum.LIST.value(),dtoList);

        return reportResponseDTO;
    }
    public ReportResponseDTO generateProductReport(ReportRequestDTO reportRequestDTO){
        //check input params
        HashMap<String, String> requestParams = reportRequestDTO.getRequestParams();
        checkInputParams(requestParams);
        if(requestParams.containsKey(ReportRequestEnum.GROUP_BY.value())){
            groupBy = requestParams.get(ReportRequestEnum.GROUP_BY.value());
        }
        //process
        List<Object[]> list = null;
        List<ProductGroupDTO> dtoList = new ArrayList<>();
        if(groupBy!=null && groupBy.equals(ReportRequestEnum.YEAR.value())) {
            list = productRepository.findProductByReportRequestWithGroupByYear(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new ProductGroupByYearDTO(objects));
            }
        } else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.MONTH.value())) {
            list = productRepository.findProductByReportRequestWithGroupByYearMonth(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new ProductGroupByYearMonthDTO(objects));
            }
        }else if(groupBy!=null && groupBy.equals(ReportRequestEnum.DAY.value())) {
            list = productRepository.findProductByReportRequestWithGroupByDay(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new ProductGroupByDayDTO(objects));
            }
        }else {
            list = productRepository.findProductByReportRequestWithoutGroupBy(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new ProductGroupDTO(objects));
            }
        }
        //output params
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        HashMap<String, Object> responseParams = reportResponseDTO.getResponseData();
        responseParams.put(ReportResponseEnum.TOTAL.value(),dtoList.size());
        responseParams.put(ReportResponseEnum.LIST.value(),dtoList);

        return reportResponseDTO;
    }
    public ReportResponseDTO generateJasperReport(ReportRequestDTO reportRequestDTO){
        return null;
    }
}
