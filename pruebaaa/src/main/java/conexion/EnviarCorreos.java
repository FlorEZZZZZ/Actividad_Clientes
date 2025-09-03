package conexion;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EnviarCorreos {
    public  void enviarcorreo(String encabezado, String cuerpo) {
        String destinatario = "florezrincon2@gmail.com";
        String remitente = "florezrincon2@gmail.com";
        String clave = "yxkq hdty bpfr cajn";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(encabezado);
            message.setText(cuerpo);

            Transport.send(message);
            System.out.println("Correo enviado exitosamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
