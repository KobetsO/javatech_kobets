package ua.edu.krnu.javatech.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.krnu.javatech.dto.EdgeNodeRequest;
import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.model.NodeStatus;
import ua.edu.krnu.javatech.repository.EdgeNodeRepository;
import ua.edu.krnu.javatech.repository.ResourceMetricRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EdgeNodeService {
    private final EdgeNodeRepository repository;
    private final ResourceMetricRepository metricRepository;

    public EdgeNodeService(EdgeNodeRepository repository, ResourceMetricRepository metricRepository) {
        this.repository = repository;
        this.metricRepository = metricRepository;
    }

    public List<EdgeNode> findAll(NodeStatus status) {
        if (status == null) {
            return repository.findAll();
        }
        return repository.findByStatusOrderByIdAsc(status);
    }

    public Optional<EdgeNode> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public EdgeNode create(EdgeNodeRequest request) {
        validate(request);
        EdgeNode node = new EdgeNode(
                request.hostname().trim(),
                request.architecture().trim(),
                request.cpuCores(),
                request.ramGb(),
                request.storageGb(),
                request.status(),
                request.site().trim()
        );
        return repository.save(node);
    }

    @Transactional
    public Optional<EdgeNode> update(Long id, EdgeNodeRequest request) {
        validate(request);
        return repository.findById(id).map(node -> {
            node.update(
                    request.hostname().trim(),
                    request.architecture().trim(),
                    request.cpuCores(),
                    request.ramGb(),
                    request.storageGb(),
                    request.status(),
                    request.site().trim()
            );
            return repository.save(node);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        metricRepository.deleteByNode_Id(id);
        repository.deleteById(id);
        return true;
    }

    private static void validate(EdgeNodeRequest request) {
        if (request == null
                || isBlank(request.hostname())
                || isBlank(request.architecture())
                || isBlank(request.site())
                || request.cpuCores() == null || request.cpuCores() <= 0
                || request.ramGb() == null || request.ramGb() <= 0
                || request.storageGb() == null || request.storageGb() <= 0
                || request.status() == null) {
            throw new IllegalArgumentException("Required fields are missing or invalid.");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}