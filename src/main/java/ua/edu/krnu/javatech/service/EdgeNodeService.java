package ua.edu.krnu.javatech.service;

import org.springframework.stereotype.Service;
import ua.edu.krnu.javatech.dto.EdgeNodeRequest;
import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.model.NodeStatus;
import ua.edu.krnu.javatech.repository.EdgeNodeRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EdgeNodeService {
    private final EdgeNodeRepository repository;
    private final AtomicLong idSequence = new AtomicLong(100);

    // Constructor injection
    public EdgeNodeService(EdgeNodeRepository repository) {
        this.repository = repository;
    }

    public List<EdgeNode> findAll(NodeStatus status) {
        return status == null
                ? repository.findAll()
                : repository.findByStatus(status);
    }

    public Optional<EdgeNode> findById(Long id) {
        return repository.findById(id);
    }

    public EdgeNode create(EdgeNodeRequest request) {
        validate(request);
        EdgeNode node = new EdgeNode(
                idSequence.incrementAndGet(),
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

    public Optional<EdgeNode> update(Long id, EdgeNodeRequest request) {
        validate(request);
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        EdgeNode updated = new EdgeNode(
                id,
                request.hostname().trim(),
                request.architecture().trim(),
                request.cpuCores(),
                request.ramGb(),
                request.storageGb(),
                request.status(),
                request.site().trim()
        );
        return Optional.of(repository.save(updated));
    }

    public boolean delete(Long id) {
        return repository.deleteById(id);
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