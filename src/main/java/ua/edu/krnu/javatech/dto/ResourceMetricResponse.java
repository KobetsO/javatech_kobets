package ua.edu.krnu.javatech.dto;

import java.time.Instant;

public record ResourceMetricResponse(
        Long id,
        String metricName,
        double value,
        String unit,
        Instant recordedAt,
        Long nodeId
) {}