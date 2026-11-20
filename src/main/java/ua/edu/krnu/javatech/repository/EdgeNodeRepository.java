package ua.edu.krnu.javatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.model.NodeStatus;

import java.util.List;

public interface EdgeNodeRepository extends JpaRepository<EdgeNode, Long> {
    List<EdgeNode> findByStatusOrderByIdAsc(NodeStatus status);
}