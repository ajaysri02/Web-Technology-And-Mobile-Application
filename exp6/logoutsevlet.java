package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ("user".equals(username) && "pass123".equals(password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("loginTime", System.currentTimeMillis());
            response.sendRedirect("session");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("    <meta charset=\"UTF-8\">");
                out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("    <title>Login Failed</title>");
                out.println("    <style>");
                out.println("        body { font-family: Arial, sans-serif; background: #f8fafc; color: #111827; margin: 0; padding: 24px; } ");
                out.println("        .card { max-width: 580px; margin: auto; padding: 24px; background: #ffffff; border: 1px solid #d1d5db; border-radius: 16px; } ");
                out.println("        .error { color: #b91c1c; margin-top: 16px; }");
                out.println("        a { color: #1d4ed8; text-decoration: none; }");
                out.println("    </style>");
                out.println("</head>");
                out.println("<body>");
                out.println("    <div class=\"card\">");
                out.println("        <h1>Login Failed</h1>");
                out.println("        <p class=\"error\">Invalid username or password. Please try again.</p>");
                out.println("        <p><a href=\"login.html\">Return to login page</a></p>");
                out.println("    </div>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}
