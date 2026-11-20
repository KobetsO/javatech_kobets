package ua.edu.krnu.javatech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "edge_nodes")
public class EdgeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String hostname;

    @Column(nullable = false, length = 50)
    private String architecture;

    @Column(nullable = false)
    private int cpuCores;

    @Column(nullable = false)
    private int ramGb;

    @Column(nullable = false)
    private int storageGb;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NodeStatus status;

    @Column(nullable = false, length = 150)
    private String site;

    protected EdgeNode() {
        // Required by JPA
    }

    public EdgeNode(
            String hostname,
            String architecture,
            int cpuCores,
            int ramGb,
            int storageGb,
            NodeStatus status,
            String site
    ) {
        this.hostname = hostname;
        this.architecture = architecture;
        this.cpuCores = cpuCores;
        this.ramGb = ramGb;
        this.storageGb = storageGb;
        this.status = status;
        this.site = site;
    }

    public void update(
            String hostname,
            String architecture,
            int cpuCores,
            int ramGb,
            int storageGb,
            NodeStatus status,
            String site
    ) {
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