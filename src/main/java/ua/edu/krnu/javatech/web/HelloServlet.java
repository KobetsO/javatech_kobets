package ua.edu.krnu.javatech.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Query parameter 'name' is required."
            );
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Hello Servlet</title>
                </head>
                <body>
                """);
        out.printf("<h1>Hello, %s!</h1>%n", escapeHtml(name));
        out.printf("<p>HTTP method: %s</p>%n", escapeHtml(request.getMethod()));
        out.printf("<p>Request URI: %s</p>%n", escapeHtml(request.getRequestURI()));
        out.printf("<p>Query string: %s</p>%n",
                escapeHtml(String.valueOf(request.getQueryString())));
        out.println("""
                </body>
                </html>
                """);
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