package ua.edu.krnu.javatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.krnu.javatech.model.ResourceMetric;

import java.util.List;
import java.util.Optional;

public interface ResourceMetricRepository extends JpaRepository<ResourceMetric, Long> {
    List<ResourceMetric> findByNode_IdOrderByRecordedAtDesc(Long nodeId);
    Optional<ResourceMetric> findByIdAndNode_Id(Long id, Long nodeId);
    long deleteByNode_Id(Long nodeId);
}