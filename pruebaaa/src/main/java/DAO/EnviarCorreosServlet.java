package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EnviarCorreosServlet")
public class EnviarCorreosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Configuración para Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // ✅ Usa siempre una contraseña de aplicación, no tu clave normal
        final String username = "florezrincon2@gmail.com";
        final String password = "yxkq hdty bpfr cajn";

        Session session = Session.getInstance(props,
            new jakarta.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Crear mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "Caleb 😎")); // nombre opcional
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("elpalitoinsano@gmail.com")); // destinatario
            message.setSubject("Prueba Jakarta Mail en Tomcat 10.1");
            message.setText("¡Hola! Este es un correo de prueba enviado desde un Servlet.");

            // Enviar correo
            Transport.send(message);

            out.println("<h3>✅ Correo enviado correctamente</h3>");

        } catch (MessagingException e) {
            out.println("<h3>❌ Error al enviar el correo: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
