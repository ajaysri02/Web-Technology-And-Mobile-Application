package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response, "POST");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, String method)
            throws IOException {
        String name = request.getParameter("name");
        String message = request.getParameter("message");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("    <meta charset=\"UTF-8\">");
            out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("    <title>GET vs POST Result</title>");
            out.println("    <style>");
            out.println("        body { font-family: Arial, sans-serif; background: #eef2f7; color: #1f2937; margin: 0; padding: 24px; } ");
            out.println("        .card { background: #ffffff; border: 1px solid #d1d5db; border-radius: 16px; padding: 24px; max-width: 700px; margin: auto; box-shadow: 0 12px 24px rgba(0,0,0,0.08); }");
            out.println("        h1 { color: #1d4ed8; margin-top: 0; }");
            out.println("        p { line-height: 1.6; }");
            out.println("        .info { background: #eff6ff; border: 1px solid #bfdbfe; padding: 16px; border-radius: 12px; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class=\"card\">");
            out.println("        <h1>Request Processed with " + method + "</h1>");
            out.println("        <div class=\"info\">");
            out.println("            <p><strong>Request Method:</strong> " + method + "</p>");
            out.println("            <p><strong>Name:</strong> " + escapeHtml(name) + "</p>");
            out.println("            <p><strong>Message:</strong> " + escapeHtml(message) + "</p>");
            out.println("        </div>");
            out.println("        <p><a href=\"getPostForm.html\">Back to GET/POST form</a></p>");
            out.println("    </div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String escapeHtml(String value) {
        if (value == null || value.isEmpty()) {
            return "(not provided)";
        }
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
