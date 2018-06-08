package com.anur.pagehelper.model;

import java.util.List;
import javax.persistence.*;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Table(name = "provider_order")
public class ProviderOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "po_id")
    private String poId;

    @Transient
    private List<OrderInfo> orderInfoList;

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public List<OrderInfo> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<OrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
}