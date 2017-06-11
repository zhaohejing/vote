package com.efan.core.primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;



/**
 * 订单编号
 */
@Entity
@Table(name="orders")
public class Order implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //用户唯一id
    @NotNull
    //购买人唯一id
    @Column(length = 200)
    private String payKey;
    //商品Id
    @NotNull
    private  Long productId;
    //商品名
    @NotNull
    @Column(length = 200)
    private  String productName;
    @NotNull
    @Column(length = 200,unique = true)
    //订单唯一编号
    private String  orderNumber;


    //订单描述
    @Column(length = 200)
    private String  orderDes;
    @NotNull
    //价格单位为分
    private Integer  price;

    //支付状态（默认false）
    private Boolean payState=false;
    @Column(length = 50)
    private String creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDes() {
        return orderDes;
    }

    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getPayState() {
        return payState;
    }

    public void setPayState(Boolean payState) {
        this.payState = payState;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}