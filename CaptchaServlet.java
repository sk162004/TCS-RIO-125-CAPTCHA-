package com.captcha;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class CaptchaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String inputCaptcha = request.getParameter("captchaInput");

        boolean isValidationAttempt = inputCaptcha != null;
        String errorMessage = null;

        // Retrieve previous CAPTCHA for validation if it's a resubmission
        String sessionCaptcha = (String) session.getAttribute("captcha");

        // CAPTCHA validation (case-sensitive)
        if (isValidationAttempt) {
            if (sessionCaptcha != null && sessionCaptcha.equals(inputCaptcha)) {
                // Success: show success message
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head><title>CAPTCHA Verified</title>");
                out.println("<style>");
                out.println("body { background-color: black; color: white; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; font-family: Arial, sans-serif; font-size:20px;}");
                out.println(".container { text-align: center; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h1>CAPTCHA Verified!</h1>");
                out.println("<p>Name: " + session.getAttribute("name") + "</p>");
                out.println("<p>Email: " + session.getAttribute("email") + "</p>");
                out.println("</div>");
                out.println("</body></html>");

                return;
            } else {
                // Failure: regenerate captcha, display error
                errorMessage = "Invalid CAPTCHA. Please try again.";
            }
        }


        // Generate a new CAPTCHA
        String captchaText = CaptchaGenerator.generateCaptchaText();
        BufferedImage image = CaptchaGenerator.generateCaptchaImage(captchaText);
        session.setAttribute("captcha", captchaText);
        session.setAttribute("name", name);
        session.setAttribute("email", email);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        String base64 = java.util.Base64.getEncoder().encodeToString(imageBytes);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>CAPTCHA Verification</title>");
        out.println("<style>");
        out.println("body { background: url('https://www.transparenttextures.com/patterns/cubes.png'), #2a0008; #0a0f2c; color: #fff; font-family: 'Segoe UI'; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }");
        out.println(".container { background: black; padding: 40px 50px; border-radius: 20px; box-shadow: 0 0 25px rgb(255 0 0 / 20%); max-width: 450px; width: 100%; text-align: center; }");
        out.println(".container h2 { color: #ff1b1b; font-size: 26px; margin-bottom: 20px; border-bottom: 1px solid #ff1b1b; padding-bottom: 10px; }");
        out.println("form { display: flex; flex-direction: column; gap: 20px; }");
        out.println(".captcha-box { background-color: #3b1e1e; padding: 15px; border-radius: 10px; display: inline-block; margin: 0 auto; }");
        out.println("input[type='text'], input[type='submit'] { padding: 12px; border-radius: 8px; border: none; font-size: 16px; }");
        out.println("input[type='text'] { background-color: #3b1e1e; color: white; outline: none; }");
        out.println("input[type='submit'] { background-color: #ff1b1b; color: white; cursor: pointer; }");
        out.println("input[type='submit']:hover { background-color: #ff1b1b; }");
        out.println(".error { color: red; font-size: 14px; }");
        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Verify CAPTCHA</h2>");
        if (errorMessage != null) {
            out.println("<p class='error'>" + errorMessage + "</p>");
        }

        out.println("<form method='post' action='CaptchaServlet'>");
        out.println("<input type='hidden' name='name' value='" + (name != null ? name : "") + "'/>");
        out.println("<input type='hidden' name='email' value='" + (email != null ? email : "") + "'/>");
        out.println("<div class='captcha-box'>");
        out.println("<img src='data:image/png;base64," + base64 + "' style='width: 100%; max-width: 300px; height: auto;'/>");
        out.println("</div>");
        out.println("<input type='text' name='captchaInput' placeholder='Enter CAPTCHA' required>");
        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("</div></body></html>");
    }
}
