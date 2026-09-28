package ua.edu.krnu.javatech.model;

public class EdgeNode {
    private final Long id;
    private final String hostname;
    private final String architecture;
    private final int cpuCores;
    private final int ramGb;
    private final int storageGb;
    private final NodeStatus status;
    private final String site;

    public EdgeNode(Long id, String hostname, String architecture, int cpuCores,
                    int ramGb, int storageGb, NodeStatus status, String site) {
        this.id = id;
        this.hostname = hostname;
        this.architecture = architecture;
        this.cpuCores = cpuCores;
        this.ramGb = ramGb;
        this.storageGb = storageGb;
        this.status = status;
        this.site = site;
    }

    public Long getId() { return id; }
    public String getHostname() { return hostname; }
    public String getArchitecture() { return architecture; }
    public int getCpuCores() { return cpuCores; }
    public int getRamGb() { return ramGb; }
    public int getStorageGb() { return storageGb; }
    public NodeStatus getStatus() { return status; }
    public String getSite() { return site; }
}
