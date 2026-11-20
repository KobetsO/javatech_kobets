package ua.edu.krnu.javatech.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "resource_metrics")
public class ResourceMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String metricName;

    @Column(name = "metric_value", nullable = false)
    private double value;

    @Column(nullable = false, length = 30)
    private String unit;

    @Column(nullable = false)
    private Instant recordedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "node_id", nullable = false)
    private EdgeNode node;

    protected ResourceMetric() {
    }

    public ResourceMetric(
            String metricName,
            double value,
            String unit,
            EdgeNode node
    ) {
        this.metricName = metricName;
        this.value = value;
        this.unit = unit;
        this.node = node;
        this.recordedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getMetricName() { return metricName; }
    public double getValue() { return value; }
    public String getUnit() { return unit; }
    public Instant getRecordedAt() { return recordedAt; }
    public EdgeNode getNode() { return node; }
}