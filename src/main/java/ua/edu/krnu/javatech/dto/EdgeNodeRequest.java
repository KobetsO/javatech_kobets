package ua.edu.krnu.javatech.dto;

import ua.edu.krnu.javatech.model.NodeStatus;

public record EdgeNodeRequest(
        String hostname,
        String architecture,
        Integer cpuCores,
        Integer ramGb,
        Integer storageGb,
        NodeStatus status,
        String site
) {}