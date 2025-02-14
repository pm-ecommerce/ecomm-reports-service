package com.pm.ecommerce.reports_service.utils;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.reports_service.utils.dto.OrderDTO;
import com.pm.ecommerce.reports_service.utils.dto.OrderItemDTO;
import com.pm.ecommerce.reports_service.utils.dto.ReportRequestDTO;
import com.pm.ecommerce.reports_service.utils.dto.ScheduledDeliveryDTO;
import com.pm.ecommerce.reports_service.utils.enums.ReportRequestEnum;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converter {

    public static ReportRequestDTO convert(HttpServletRequest httpServletRequest) {
        ReportRequestDTO reportRequestDTO = new ReportRequestDTO();
        for (ReportRequestEnum e : ReportRequestEnum.values()) {
            reportRequestDTO.getRequestParams().put(e.value(), httpServletRequest.getParameter(e.value()));
        }
        return reportRequestDTO;
    }

    public static Timestamp convert(String dateString) {
        Timestamp ts = null;
        if (dateString != null) {
            String[] patterns = {"MM/dd/yyyy", "MM-dd-yyyy", "yyyy/MM/dd", "yyyy-MM-dd"};
            for (String p : patterns) {
                DateFormat df = new SimpleDateFormat(p);
                Date pd = null;
                try {
                    pd = df.parse(dateString);
                    ts = new Timestamp((pd).getTime());
                    //OK: parsing date string"
                    break;
                } catch (ParseException e) {
                    //error: parsing date string
                }
            }
        }
        return ts;
    }

    public static List<OrderDTO> convert(List<Order> orderList) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orderList) {
            orderDTOList.add(Converter.convert(order));
        }
        return orderDTOList;
    }

    public static List<ScheduledDeliveryDTO> convert2(List<ScheduledDelivery> orderList) {
        List<ScheduledDeliveryDTO> orderDTOList = new ArrayList<>();
        for (ScheduledDelivery order : orderList) {
            orderDTOList.add(Converter.convert2(order));
        }
        return orderDTOList;
    }

    public static ScheduledDeliveryDTO convert2(ScheduledDelivery order) {
        ScheduledDeliveryDTO orderDTO = new ScheduledDeliveryDTO();
        if (order != null) {
            orderDTO.setOrder_id(order.getId());
            orderDTO.setUser_id(order.getVendor().getId());
            orderDTO.setShipping_address(order.getAddress());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setDelivery_date(order.getDeliveryDate().toLocalDateTime());
            List<OrderItem> orderItemList = order.getItems();
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            for (OrderItem orderItem : orderItemList) {
                OrderItemDTO orderItemDTO = Converter.convert(orderItem);
                orderItemDTO.setOrder_id(order.getId());
                orderItemDTOList.add(orderItemDTO);
            }
            orderDTO.setOrder_item_list(orderItemDTOList);
        }
        return orderDTO;
    }

    public static OrderDTO convert(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        if (order != null) {
            orderDTO.setOrder_id(order.getId());
            orderDTO.setUser_id(order.getUser().getId());
            orderDTO.setBilling_address_id(order.getBillingAddress());
            orderDTO.setShipping_address_id(order.getShippingAddress());
            orderDTO.setTax(order.getTax());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setOrder_date(order.getCreatedDate().toLocalDateTime());
            List<OrderItem> orderItemList = order.getItems();
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            for (OrderItem orderItem : orderItemList) {
                OrderItemDTO orderItemDTO = Converter.convert(orderItem);
                orderItemDTO.setOrder_id(order.getId());
                orderItemDTOList.add(orderItemDTO);
            }
            orderDTO.setOrder_item_list(orderItemDTOList);
        }
        return orderDTO;
    }

    public static OrderItemDTO convert(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        if (orderItem != null) {
            orderItemDTO.setOrder_item_id(orderItem.getId());
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setPrice(orderItem.getRate());
            Product product = orderItem.getProduct();
            if (product != null) {
                orderItemDTO.setProduct_id(product.getId());
                orderItemDTO.setProduct_name(product.getName());
                orderItemDTO.setPrice(product.getPrice());
                Category category = product.getCategory();
                if (category != null) {
                    try {
                        orderItemDTO.setCategory_id(category.getId());
                        orderItemDTO.setCategory_name(category.getName());
                    } catch (Exception e) {
                        // assume could not find the category id
                    }
                }
                Vendor vendor = product.getVendor();
                if (vendor != null) {
                    try {
                        orderItemDTO.setVendor_id(vendor.getId());
                        orderItemDTO.setVendor_name(vendor.getName());
                    } catch (Exception e) {
                        // assume could not find the vendor id
                    }
                }
            }
        }
        return orderItemDTO;
    }
}
