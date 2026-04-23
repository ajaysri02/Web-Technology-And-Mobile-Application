package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PreferenceServlet extends HttpServlet {
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30 days

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String theme = "Light";
        String language = "English";
        boolean saved = false;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("theme".equals(cookie.getName())) {
                    theme = cookie.getValue();
                    saved = true;
                } else if ("language".equals(cookie.getName())) {
                    language = cookie.getValue();
                    saved = true;
                }
            }
        }

        String message = request.getParameter("message");
        String error = request.getParameter("error");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\" />");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
            out.println("<title>ResumeCraft | Preferences</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background: #f5f8ff; margin: 0; padding: 24px; }");
            out.println(".card { max-width: 640px; margin: 0 auto; background: white; border-radius: 22px; padding: 32px; box-shadow: 0 22px 44px rgba(31, 65, 118, 0.12); }");
            out.println("h1 { margin-top: 0; color: #1f4a7d; }");
            out.println("label { display: block; margin-top: 18px; margin-bottom: 8px; font-weight: 700; color: #2c4464; }");
            out.println("select { width: 100%; padding: 12px 14px; border-radius: 12px; border: 1px solid #d4dae5; font-size: 1rem; }");
            out.println("button { margin-top: 22px; padding: 12px 22px; border: none; border-radius: 999px; background: #1f4a7d; color: white; cursor: pointer; font-weight: 700; }");
            out.println("button:hover { background: #16345e; }");
            out.println(".notice { margin-top: 18px; color: #1f4a7d; }");
            out.println(".error { margin-top: 18px; color: #b71c1c; }");
            out.println(".preference-summary { margin-top: 20px; padding: 18px; background: #eef4ff; border-radius: 16px; border: 1px solid #d7e3f1; }");
            out.println(".preference-summary p { margin: 10px 0; color: #385177; }");
            out.println(".nav-link { display: inline-block; margin-top: 18px; color: #1f4a7d; text-decoration: none; font-weight: 700; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"card\">");
            out.println("<h1>Preferences</h1>");

            if (message != null && !message.isEmpty()) {
                out.println("<div class=\"notice\">" + escapeHtml(message) + "</div>");
            }
            if (error != null && !error.isEmpty()) {
                out.println("<div class=\"error\">" + escapeHtml(error) + "</div>");
            }

            out.println("<form method=\"post\" action=\"" + request.getContextPath() + "/preferences\">");
            out.println("<label for=\"theme\">Theme</label>");
            out.println("<select id=\"theme\" name=\"theme\">");
            out.println("<option value=\"Light\"" + ("Light".equals(theme) ? " selected" : "") + ">Light</option>");
            out.println("<option value=\"Dark\"" + ("Dark".equals(theme) ? " selected" : "") + ">Dark</option>");
            out.println("</select>");
            out.println("<label for=\"language\">Language</label>");
            out.println("<select id=\"language\" name=\"language\">");
            out.println("<option value=\"English\"" + ("English".equals(language) ? " selected" : "") + ">English</option>");
            out.println("<option value=\"Spanish\"" + ("Spanish".equals(language) ? " selected" : "") + ">Spanish</option>");
            out.println("<option value=\"French\"" + ("French".equals(language) ? " selected" : "") + ">French</option>");
            out.println("</select>");
            out.println("<button type=\"submit\">Save Preferences</button>");
            out.println("</form>");

            out.println("<div class=\"preference-summary\">");
            if (saved) {
                out.println("<p>Your saved preferences:</p>");
                out.println("<p>Theme: <strong>" + escapeHtml(theme) + "</strong></p>");
                out.println("<p>Language: <strong>" + escapeHtml(language) + "</strong></p>");
            } else {
                out.println("<p>No saved preferences yet. Choose theme and language then save.</p>");
            }
            out.println("</div>");
            out.println("<a class=\"nav-link\" href=\"" + request.getContextPath() + "/index.html\">Back to Home</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String theme = request.getParameter("theme");
        String language = request.getParameter("language");

        if (theme == null || language == null) {
            String encodedError = URLEncoder.encode("Please select both theme and language.", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/preferences?error=" + encodedError);
            return;
        }

        Cookie themeCookie = new Cookie("theme", theme);
        themeCookie.setMaxAge(COOKIE_MAX_AGE);
        themeCookie.setPath(request.getContextPath() + "/");

        Cookie languageCookie = new Cookie("language", language);
        languageCookie.setMaxAge(COOKIE_MAX_AGE);
        languageCookie.setPath(request.getContextPath() + "/");

        response.addCookie(themeCookie);
        response.addCookie(languageCookie);

        String encodedMessage = URLEncoder.encode("Preferences saved successfully.", StandardCharsets.UTF_8);
        response.sendRedirect(request.getContextPath() + "/preferences?message=" + encodedMessage);
    }

    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}
