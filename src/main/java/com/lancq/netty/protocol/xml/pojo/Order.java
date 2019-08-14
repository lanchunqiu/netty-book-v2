package com.lancq.netty.protocol.xml.pojo;

import lombok.Data;

/**
 * @author lancq
 */
@Data
public class Order {
    private long orderNumber;

    private Customer customer;

    /** Billing address information. */
    private Address billTo;

    private Shipping shipping;

    /**
     * Shipping address information. If missing, the billing address is also
     * used as the shipping address.
     */
    private Address shipTo;

    private Float total;
}
