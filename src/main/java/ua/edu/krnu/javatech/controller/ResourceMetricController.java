package ua.edu.krnu.javatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.krnu.javatech.dto.ResourceMetricRequest;
import ua.edu.krnu.javatech.dto.ResourceMetricResponse;
import ua.edu.krnu.javatech.service.ResourceMetricService;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nodes/{nodeId}/metrics")
public class ResourceMetricController {
    private final ResourceMetricService service;

    public ResourceMetricController(ResourceMetricService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResourceMetricResponse>> findAll(@PathVariable Long nodeId) {
        return service.findByNodeId(nodeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable Long nodeId,
            @RequestBody ResourceMetricRequest request
    ) {
        try {
            return service.create(nodeId, request)
                    .<ResponseEntity<?>>map(result -> {
                        URI location = URI.create("/api/nodes/" + nodeId + "/metrics/" + result.id());
                        return ResponseEntity.created(location).body(result);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}