package ua.edu.krnu.javatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.krnu.javatech.dto.EdgeNodeRequest;
import ua.edu.krnu.javatech.model.NodeStatus;
import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.service.EdgeNodeService;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nodes")
public class EdgeNodeController {
    private final EdgeNodeService service;

    // Constructor injection
    public EdgeNodeController(EdgeNodeService service) {
        this.service = service;
    }

    @GetMapping
    public List<EdgeNode> findAll(
            @RequestParam(required = false) NodeStatus status
    ) {
        return service.findAll(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EdgeNode> findById(
            @PathVariable Long id
    ) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody EdgeNodeRequest request
    ) {
        try {
            EdgeNode created = service.create(request);
            URI location = URI.create("/api/nodes/" + created.getId());
            return ResponseEntity.created(location).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody EdgeNodeRequest request
    ) {
        try {
            return service.update(id, request)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}