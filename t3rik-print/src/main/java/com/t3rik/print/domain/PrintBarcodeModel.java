package com.t3rik.print.domain;

import lombok.Data;

import java.util.Map;

/**
 * @author skyyan
 */
@Data
public class PrintBarcodeModel {
    private Map<String, String> params;
    private String printerCode;
    private String businessType;
    private Long businessId;
    private String businessCode;
}
