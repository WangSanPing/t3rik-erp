package com.t3rik.common.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 颜色生成
 *
 * @author t3rik
 * @date 2025/4/17 22:31
 */
public class ColorUtils {

    /**
     * 生成echarts风格颜色
     */
    public static List<String> generateEChartsLikeColors(int count) {
        List<String> colors = new ArrayList<>();

        // ECharts 常用配色风格（柔和且协调）
        String[] echartsColors = {
                "#5470C6", // 蓝色
                "#91CC75", // 绿色
                "#FAC858", // 黄色
                "#EE6666", // 红色
                "#73C0DE", // 浅蓝
                "#FFB980", // 浅橙
                "#D87A80", // 暗红
                "#8D98B3", // 灰色调
                "#E7BCF2", // 淡紫
                "#FF9F7F"  // 粉橙
        };

        // 确保颜色的数量不会超过预设的颜色池大小
        for (int i = 0; i < count; i++) {
            String color = echartsColors[i % echartsColors.length];
            colors.add(color);
        }

        return colors;
    }


    /**
     * 生成随机颜色
     */
    public static List<String> generateNiceColors(int count) {
        List<String> colors = new ArrayList<>();
        float saturation = 0.5f; // 饱和度
        float lightness = 0.65f;   // 亮度

        for (int i = 0; i < count; i++) {
            float hue = (float) i / count; // 均匀分布色相
            Color color = hslToRgb(hue, saturation, lightness);
            String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
            colors.add(hex);
        }
        return colors;
    }

    private static Color hslToRgb(float h, float s, float l) {
        float q = l < 0.5 ? l * (1 + s) : (l + s) - (l * s);
        float p = 2 * l - q;
        float r = hueToRgb(p, q, h + 1f / 3f);
        float g = hueToRgb(p, q, h);
        float b = hueToRgb(p, q, h - 1f / 3f);
        return new Color(Math.round(r * 255), Math.round(g * 255), Math.round(b * 255));
    }

    private static float hueToRgb(float p, float q, float t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1f / 6f) return p + (q - p) * 6 * t;
        if (t < 1f / 2f) return q;
        if (t < 2f / 3f) return p + (q - p) * (2f / 3f - t) * 6;
        return p;
    }

}
