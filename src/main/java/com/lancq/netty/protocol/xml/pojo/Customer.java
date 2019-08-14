package com.lancq.netty.protocol.xml.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author lancq
 */
@Data
public class Customer {
    private long customerNumber;

    /** Personal name. */
    private String firstName;

    /** Family name. */
    private String lastName;

    /** Middle name(s), if any. */
    private List<String> middleNames;
}
