package ua.edu.krnu.javatech.repository;

import org.springframework.stereotype.Repository;
import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.model.NodeStatus;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class EdgeNodeRepository {
    private final ConcurrentMap<Long, EdgeNode> storage = new ConcurrentHashMap<>();

    public EdgeNodeRepository() {
        save(new EdgeNode(1L, "edge-node-01", "x86_64", 4, 16, 256, NodeStatus.ONLINE, "Site-A"));
        save(new EdgeNode(2L, "edge-node-02", "ARM64", 8, 32, 512, NodeStatus.DEGRADED, "Site-B"));
    }

    public List<EdgeNode> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(EdgeNode::getId))
                .toList();
    }

    public List<EdgeNode> findByStatus(NodeStatus status) {
        return storage.values().stream()
                .filter(node -> node.getStatus() == status)
                .sorted(Comparator.comparing(EdgeNode::getId))
                .toList();
    }

    public Optional<EdgeNode> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public EdgeNode save(EdgeNode node) {
        storage.put(node.getId(), node);
        return node;
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}