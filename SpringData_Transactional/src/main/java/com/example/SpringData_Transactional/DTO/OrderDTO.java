package com.example.SpringData_Transactional.DTO;

import java.util.List;

public class OrderDTO {
    Long customerId;
    List<Long> productIds;

    public OrderDTO(Long customerId, List<Long> productIds) {
        this.customerId = customerId;
        this.productIds = productIds;
    }

    public OrderDTO() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
