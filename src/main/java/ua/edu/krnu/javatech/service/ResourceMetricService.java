package ua.edu.krnu.javatech.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.krnu.javatech.dto.ResourceMetricRequest;
import ua.edu.krnu.javatech.dto.ResourceMetricResponse;
import ua.edu.krnu.javatech.model.ResourceMetric;
import ua.edu.krnu.javatech.repository.EdgeNodeRepository;
import ua.edu.krnu.javatech.repository.ResourceMetricRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ResourceMetricService {
    private final ResourceMetricRepository metricRepository;
    private final EdgeNodeRepository nodeRepository;

    public ResourceMetricService(
            ResourceMetricRepository metricRepository,
            EdgeNodeRepository nodeRepository
    ) {
        this.metricRepository = metricRepository;
        this.nodeRepository = nodeRepository;
    }

    public Optional<List<ResourceMetricResponse>> findByNodeId(Long nodeId) {
        if (!nodeRepository.existsById(nodeId)) {
            return Optional.empty();
        }
        return Optional.of(
                metricRepository.findByNode_IdOrderByRecordedAtDesc(nodeId)
                        .stream()
                        .map(ResourceMetricService::toResponse)
                        .toList()
        );
    }

    @Transactional
    public Optional<ResourceMetricResponse> create(Long nodeId, ResourceMetricRequest request) {
        validate(request);
        return nodeRepository.findById(nodeId).map(node -> {
            ResourceMetric metric = new ResourceMetric(
                    request.metricName().trim(),
                    request.value(),
                    request.unit().trim(),
                    node
            );
            return toResponse(metricRepository.save(metric));
        });
    }

    private static void validate(ResourceMetricRequest request) {
        if (request == null
                || request.metricName() == null || request.metricName().isBlank()
                || request.value() == null
                || request.unit() == null || request.unit().isBlank()) {
            throw new IllegalArgumentException("Required metric fields are missing.");
        }
    }

    private static ResourceMetricResponse toResponse(ResourceMetric metric) {
        return new ResourceMetricResponse(
                metric.getId(),
                metric.getMetricName(),
                metric.getValue(),
                metric.getUnit(),
                metric.getRecordedAt(),
                metric.getNode().getId()
        );
    }
}