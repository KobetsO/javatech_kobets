package ua.edu.krnu.javatech.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.model.NodeStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class EdgeNodeServlet extends HttpServlet {
    private final List<EdgeNode> nodes = new CopyOnWriteArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(100);

    @Override
    public void init() {
        nodes.add(new EdgeNode(1L, "edge-node-01", "x86_64", 4, 16, 256, NodeStatus.ONLINE, "Site-A"));
        nodes.add(new EdgeNode(2L, "edge-node-02", "ARM64", 8, 32, 512, NodeStatus.DEGRADED, "Site-B"));
        System.out.println("EdgeNodeServlet initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String statusFilter = request.getParameter("status");

        HttpSession session = request.getSession(false);
        String message = null;
        if (session != null) {
            Object value = session.getAttribute("message");
            if (value != null) {
                message = value.toString();
                session.removeAttribute("message");
            }
        }

        PrintWriter out = response.getWriter();
        out.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Edge Nodes</title>
                </head>
                <body>
                <h1>Edge Computing Nodes</h1>
                """);

        if (message != null) {
            out.printf("<p><strong>%s</strong></p>%n", escapeHtml(message));
        }

        out.println("""
                <p><a href="/node-form.html">Add new node</a></p>
                <p>
                  Filter:
                  <a href="/nodes">ALL</a> |
                  <a href="/nodes?status=ONLINE">ONLINE</a> |
                  <a href="/nodes?status=OFFLINE">OFFLINE</a> |
                  <a href="/nodes?status=DEGRADED">DEGRADED</a>
                </p>
                <table border="1" cellpadding="5">
                <tr>
                    <th>ID</th><th>Hostname</th><th>Architecture</th>
                    <th>CPU Cores</th><th>RAM (GB)</th><th>Storage (GB)</th>
                    <th>Status</th><th>Site</th>
                </tr>
                """);

        for (EdgeNode node : nodes) {
            if (statusFilter != null && !statusFilter.isBlank()
                    && !node.getStatus().name().equalsIgnoreCase(statusFilter)) {
                continue;
            }
            out.printf("""
                    <tr>
                      <td>%d</td><td>%s</td><td>%s</td>
                      <td>%d</td><td>%d</td><td>%d</td><td>%s</td><td>%s</td>
                    </tr>
                    """,
                    node.getId(),
                    escapeHtml(node.getHostname()),
                    escapeHtml(node.getArchitecture()),
                    node.getCpuCores(),
                    node.getRamGb(),
                    node.getStorageGb(),
                    node.getStatus(),
                    escapeHtml(node.getSite())
            );
        }

        out.println("""
                </table>
                <p><a href="/">Home</a></p>
                </body>
                </html>
                """);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String hostname = request.getParameter("hostname");
        String architecture = request.getParameter("architecture");
        String cpuCoresStr = request.getParameter("cpuCores");
        String ramGbStr = request.getParameter("ramGb");
        String storageGbStr = request.getParameter("storageGb");
        String site = request.getParameter("site");
        String statusText = request.getParameter("status");

        if (isBlank(hostname) || isBlank(architecture) || isBlank(site)
                || isBlank(cpuCoresStr) || isBlank(ramGbStr) || isBlank(storageGbStr)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required fields are missing.");
            return;
        }

        int cpuCores;
        int ramGb;
        int storageGb;
        try {
            cpuCores = Integer.parseInt(cpuCoresStr);
            ramGb = Integer.parseInt(ramGbStr);
            storageGb = Integer.parseInt(storageGbStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric format.");
            return;
        }

        NodeStatus status;
        try {
            status = NodeStatus.valueOf(statusText);
        } catch (IllegalArgumentException | NullPointerException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid node status.");
            return;
        }

        EdgeNode node = new EdgeNode(
                idSequence.incrementAndGet(),
                hostname.trim(),
                architecture.trim(),
                cpuCores,
                ramGb,
                storageGb,
                status,
                site.trim()
        );

        nodes.add(node);

        HttpSession session = request.getSession();
        session.setAttribute("message", "Edge node " + hostname + " was added successfully.");
        response.sendRedirect(request.getContextPath() + "/nodes");
    }

    @Override
    public void destroy() {
        System.out.println("EdgeNodeServlet destroyed.");
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
