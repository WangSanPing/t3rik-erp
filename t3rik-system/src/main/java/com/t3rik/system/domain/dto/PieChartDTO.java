package com.t3rik.system.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * 饼图
 *
 * @author t3rik
 * @date 2025/4/9 22:55
 */
@Data
public class PieChartDTO {

    private List<PieSeries> series;

    @Data
    public static class PieSeries {
        private String name;
        private List<PieDataItem> data;
        // 可选：颜色数组
        private List<String> colors;
    }

    @Data
    public static class PieDataItem {
        private String name;
        private Integer value;
    }
}
