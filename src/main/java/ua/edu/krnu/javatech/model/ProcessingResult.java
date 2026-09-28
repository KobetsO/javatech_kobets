package ua.edu.krnu.javatech.model;

public record ProcessingResult(
        Long objectId,
        String objectName,
        boolean success,
        long threadId,
        boolean virtualThread,
        long simulatedDelayMs
) {}
