package ua.edu.krnu.javatech.dto;

public record ResourceMetricRequest(
        String metricName,
        Double value,
        String unit
) {}