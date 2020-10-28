package com.pm.ecommerce.reports_service.services;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.reports_service.repositories.CategoryRepository;
import com.pm.ecommerce.reports_service.repositories.OrderRepository;
import com.pm.ecommerce.reports_service.repositories.ProductRepository;
import com.pm.ecommerce.reports_service.repositories.VendorRepository;
import com.pm.ecommerce.reports_service.utils.dto.*;
import com.pm.ecommerce.reports_service.utils.dto.pdf.*;
import com.pm.ecommerce.reports_service.utils.enums.ReportRequestEnum;
import com.pm.ecommerce.reports_service.utils.enums.ReportResponseEnum;
import com.pm.ecommerce.reports_service.utils.Converter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ReportService implements IReportService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ResourceLoader resourceLoader;

    String vendorId=null;
    Timestamp fromDate=null, toDate=null;
    String minCost=null, maxCost=null;
    String groupBy=null;
    List<Object[]> list = null;
    List<GroupDTO> dtoList = null;

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
        if(requestParams.containsKey(ReportRequestEnum.GROUP_BY.value())){
            groupBy = requestParams.get(ReportRequestEnum.GROUP_BY.value());
        }
    }

    public ReportResponseDTO generateReport(ReportRequestDTO reportRequestDTO){

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

    public ReportResponseDTO generateOrderReport(ReportRequestDTO reportRequestDTO){

        //check input params
        HashMap<String, String> requestParams = reportRequestDTO.getRequestParams();
        checkInputParams(requestParams);
        //process
        dtoList = new ArrayList<>();
        if(groupBy!=null && groupBy.equals(ReportRequestEnum.YEAR.value())) {
            list = orderRepository.findOrdersByReportRequestWithGroupByYear(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByYearDTO(objects));
            }
        } else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.MONTH.value())) {
            list = orderRepository.findOrdersByReportRequestWithGroupByYearMonth(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByMonthDTO(objects));
            }
        }else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.WEEK.value())) {
            list = orderRepository.findOrdersByReportRequestWithGroupByWeek(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByMonthDTO(objects));
            }
        }else if(groupBy!=null && groupBy.equals(ReportRequestEnum.DAY.value())) {
            list = orderRepository.findOrdersByReportRequestWithGroupByDay(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByDayDTO(objects));
            }
        }else {
            list = orderRepository.findOrdersByReportRequestWithoutGroupBy(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupDTO(objects));
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
        //process
        dtoList = new ArrayList<>();
        if(groupBy!=null && groupBy.equals(ReportRequestEnum.YEAR.value())) {
            list = vendorRepository.findVendorByReportRequestWithGroupByYear(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByYearDTO(objects));
            }
        } else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.MONTH.value())) {
            list = vendorRepository.findVendorByReportRequestWithGroupByYearMonth(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByMonthDTO(objects));
            }
        }else if(groupBy!=null && groupBy.equals(ReportRequestEnum.WEEK.value())) {
            list = vendorRepository.findVendorByReportRequestWithGroupByWeek(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByDayDTO(objects));
            }
        }else if(groupBy!=null && groupBy.equals(ReportRequestEnum.DAY.value())) {
            list = vendorRepository.findVendorByReportRequestWithGroupByDay(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByDayDTO(objects));
            }
        }else {
            list = vendorRepository.findVendorByReportRequestWithoutGroupBy(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupDTO(objects));
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
        //process
        dtoList = new ArrayList<>();
        if(groupBy!=null && groupBy.equals(ReportRequestEnum.YEAR.value())) {
            list = productRepository.findProductByReportRequestWithGroupByYear(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByYearDTO(objects));
            }
        } else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.MONTH.value())) {
            list = productRepository.findProductByReportRequestWithGroupByYearMonth(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByMonthDTO(objects));
            }
        }else if(groupBy!=null && groupBy.equals(ReportRequestEnum.DAY.value())) {
            list = productRepository.findProductByReportRequestWithGroupByDay(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByDayDTO(objects));
            }
        }else if(groupBy!=null && groupBy.equals(ReportRequestEnum.WEEK.value())) {
            list = productRepository.findProductByReportRequestWithGroupByWeek(fromDate, toDate, vendorId, minCost, maxCost);
            for (Object[] objects : list) {
                dtoList.add(new GroupByDayDTO(objects));
            }
        }else {
            list = productRepository.findProductByReportRequestWithoutGroupBy(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupDTO(objects));
            }
        }
        //output params
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        HashMap<String, Object> responseParams = reportResponseDTO.getResponseData();
        responseParams.put(ReportResponseEnum.TOTAL.value(),dtoList.size());
        responseParams.put(ReportResponseEnum.LIST.value(),dtoList);

        return reportResponseDTO;
    }
    public ReportResponseDTO generateCategoryReport(ReportRequestDTO reportRequestDTO){
        //check input params
        HashMap<String, String> requestParams = reportRequestDTO.getRequestParams();
        checkInputParams(requestParams);
        //process
        dtoList = new ArrayList<>();
        if(groupBy!=null && groupBy.equals(ReportRequestEnum.YEAR.value())) {
            list = categoryRepository.findCategoryByReportRequestWithGroupByYear(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByYearDTO(objects));
            }
        } else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.MONTH.value())) {
            list = categoryRepository.findCategoryByReportRequestWithGroupByYearMonth(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByMonthDTO(objects));
            }
        } else  if(groupBy!=null && groupBy.equals(ReportRequestEnum.WEEK.value())) {
            list = categoryRepository.findCategoryByReportRequestWithGroupByWeek(fromDate, toDate, vendorId, minCost, maxCost);
            for (Object[] objects : list) {
                dtoList.add(new GroupByMonthDTO(objects));
            }
        } else if(groupBy!=null && groupBy.equals(ReportRequestEnum.DAY.value())) {
            list = categoryRepository.findCategoryByReportRequestWithGroupByDay(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupByDayDTO(objects));
            }
        }else {
            list = categoryRepository.findCategoryByReportRequestWithoutGroupBy(fromDate,toDate,vendorId,minCost,maxCost);
            for(Object[] objects:list){
                dtoList.add( new GroupDTO(objects));
            }
        }
        //output params
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        HashMap<String, Object> responseParams = reportResponseDTO.getResponseData();
        responseParams.put(ReportResponseEnum.TOTAL.value(),dtoList.size());
        responseParams.put(ReportResponseEnum.LIST.value(),dtoList);

        return reportResponseDTO;
    }

    public List<JasperPrint> generateJasperPrintsForAdmin(){
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(this.generateOrderCoverJasperPrint());
        jasperPrintList.add(this.generateOrderYearJasperPrint(null));
        jasperPrintList.add(this.generateOrderYearMonthJasperPrint(null));
        jasperPrintList.add(this.generateTop10OrderVendorJasperPrint());
        jasperPrintList.add(this.generateTop10OrderProductJasperPrint(null));
        jasperPrintList.add(this.generateOrderBillingStateJasperPrint(null));
        jasperPrintList.add(this.generateOrderShippingStateJasperPrint(null));
        return jasperPrintList;
    }

    //TO DO
    public List<JasperPrint> generateJasperPrintsForVendor(Integer vendorId){
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(this.generateOrderCoverJasperPrint());
        jasperPrintList.add(this.generateOrderYearJasperPrint(vendorId));
        jasperPrintList.add(this.generateOrderYearMonthJasperPrint(vendorId));
        jasperPrintList.add(this.generateTop10OrderProductJasperPrint(vendorId));
        jasperPrintList.add(this.generateOrderBillingStateJasperPrint(vendorId));
        jasperPrintList.add(this.generateOrderShippingStateJasperPrint(vendorId));
        return jasperPrintList;
    }

    private JasperPrint generateOrderCoverJasperPrint() {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resourceLoader.getResource("classpath:templates/rpt_order_cover.jrxml").getURI().getPath());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(null,false);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Today", new Date());
            parameters.put("Title", "Sale Report");
            parameters.put("SubTitle", "A quick report of a PM eCommerce");
            InputStream imgInputStream = resourceLoader.getResource("classpath:static/wave.png").getInputStream();
            parameters.put("Logo",imgInputStream);
            return JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    private JasperPrint generateOrderYearJasperPrint(Integer vendorId) {
        try {
            List<Object[]> list = (vendorId==null)? orderRepository.findOrderYear():orderRepository.findOrderYearByVendor(vendorId);
            List<OrderYearDTO> dtoList = new ArrayList<>();
            for(Object[] objects: list){
                dtoList.add(new OrderYearDTO(objects));
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(resourceLoader.getResource("classpath:templates/rpt_order_year.jrxml").getURI().getPath());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dtoList,false);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("caption", list.size() + " years / " + ((vendorId==null)?orderRepository.count():orderRepository.findOrderTotalByVendor(vendorId)) +" orders");
            return JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    private JasperPrint generateOrderYearMonthJasperPrint(Integer vendorId) {
        try {
            List<Object[]> list = (vendorId==null)?orderRepository.findOrderYearMonth():orderRepository.findOrderYearMonthByVendor(vendorId);
            List<OrderMonthDTO> dtoList = new ArrayList<>();
            for(Object[] objects: list){
                dtoList.add(new OrderMonthDTO(objects));
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(resourceLoader.getResource("classpath:templates/rpt_order_month.jrxml").getURI().getPath());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dtoList,false);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("caption", list.size() + " months / " + ((vendorId==null)?orderRepository.count():orderRepository.findOrderTotalByVendor(vendorId)) +" orders");
            return JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    private JasperPrint generateTop10OrderVendorJasperPrint() {
        try {
            List<Object[]> list = orderRepository.findOrderVendor();
            List<OrderVendorDTO> dtoList = new ArrayList<>();
            for(Object[] objects: list){
                dtoList.add(new OrderVendorDTO(objects));
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(resourceLoader.getResource("classpath:templates/rpt_order_vendor.jrxml").getURI().getPath());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dtoList,false);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("caption", list.size() + " vendors / " + orderRepository.count() +" orders");
            return JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    private JasperPrint generateTop10OrderProductJasperPrint(Integer vendorId) {
        try {
            List<Object[]> list = (vendorId==null)?orderRepository.findOrderProduct():orderRepository.findOrderProductByVendor(vendorId);
            List<OrderProductDTO> dtoList = new ArrayList<>();
            for(Object[] objects: list){
                dtoList.add(new OrderProductDTO(objects));
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(resourceLoader.getResource("classpath:templates/rpt_order_product.jrxml").getURI().getPath());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dtoList,false);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("caption", list.size() + " products / " + ((vendorId==null)?orderRepository.count():orderRepository.findOrderTotalByVendor(vendorId)) +" orders");
            return JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    private JasperPrint generateOrderBillingStateJasperPrint(Integer vendorId) {
        try {
            List<Object[]> list = (vendorId==null)?orderRepository.findOrderBillingState():orderRepository.findOrderBillingStateByVendor(vendorId);
            List<OrderStateDTO> dtoList = new ArrayList<>();
            for(Object[] objects: list){
                dtoList.add(new OrderStateDTO(objects));
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(resourceLoader.getResource("classpath:templates/rpt_order_billing_state.jrxml").getURI().getPath());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dtoList,false);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("caption", list.size() + " states / " + ((vendorId==null)?orderRepository.count():orderRepository.findOrderTotalByVendor(vendorId)) +" orders");
            return JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    private JasperPrint generateOrderShippingStateJasperPrint(Integer vendorId) {
        try {
            List<Object[]> list = (vendorId==null)?orderRepository.findOrderShippingState():orderRepository.findOrderShippingStateByVendor(vendorId);
            List<OrderStateDTO> dtoList = new ArrayList<>();
            for(Object[] objects: list){
                dtoList.add(new OrderStateDTO(objects));
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(resourceLoader.getResource("classpath:templates/rpt_order_shipping_state.jrxml").getURI().getPath());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dtoList,false);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("caption", list.size() + " states / " + ((vendorId==null)?orderRepository.count():orderRepository.findOrderTotalByVendor(vendorId)) +" orders");
            return JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }
}
