package com.t3rik.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 饼图和折线图
 *
 * @author t3rik
 * @date 2025/4/9 22:56
 */
@Data
public class XYChartDTO {
    /**
     * x轴
     */
    @JsonProperty("xAxisData")
    private List<String> xAxisData;
    /**
     * y轴
     */
    private List<XYSeries> series;

    @Data
    public static class XYSeries {
        private String name;
        private Integer count;
        private List<Integer> data;
        private String color; // 可选：图表线条颜色
    }
}
