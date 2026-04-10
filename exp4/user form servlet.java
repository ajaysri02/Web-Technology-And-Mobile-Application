package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserFormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("userName");
        String age = request.getParameter("userAge");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("    <meta charset=\"UTF-8\">");
            out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("    <title>User Details</title>");
            out.println("    <style>");
            out.println("        body { font-family: Arial, sans-serif; background: #eef2f7; color: #1f2937; margin: 0; padding: 24px; } ");
            out.println("        .card { background: #ffffff; border: 1px solid #d1d5db; border-radius: 16px; padding: 24px; max-width: 600px; margin: auto; box-shadow: 0 12px 24px rgba(0,0,0,0.08); }");
            out.println("        h1 { color: #1d4ed8; }");
            out.println("        p { line-height: 1.6; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class=\"card\">");
            out.println("        <h1>User Details Received</h1>");
            out.println("        <p><strong>Name:</strong> " + (name == null || name.isEmpty() ? "(not provided)" : escapeHtml(name)) + "</p>");
            out.println("        <p><strong>Age:</strong> " + (age == null || age.isEmpty() ? "(not provided)" : escapeHtml(age)) + "</p>");
            out.println("        <p><a href=\"userForm.html\">Return to form</a></p>");
            out.println("    </div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
