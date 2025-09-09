package conexion;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;



public class EnviarCorreos {
    public  void enviarcorreo(String encabezado, String cuerpo) {
        String destinatario = "elpalitoinsano@gmail.com";
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
    
    public void EnviarDocumentos(
            int id,
            String nombres_apellidos,
            String tipoDocumento,
            String numeroDocumento,
            String fechaNacimiento,
            String lugarNacimiento,
            String nivelEscolaridad,
            String ocupacionActual,
            String direccion,
            String telefono,
            String correo,
            String estadoCivil,
            String tribunalEclesiastico,
            String conceptoTribunal,
            String archivoConcepto,
            String nombreConyuge,
            String numeroHijos,
            String nombresHijos,
            String fechaConversion,
            String haEstadoApartado,
            String fechaReconciliacion,
            String fechaRecepcionEspiritu,
            String fechaBautismo,
            String congregacionBautismo,
            String pastorBautismo,
            String cargosIglesia,
            String password
    ) {
        String destinatario = correo;
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
            message.setSubject("📌 Datos del Usuario Registrado");

            String cuerpo = String.format(
                "ID: %d%n" +
                "Nombre Completo: %s%n" +
                "Tipo Documento: %s%n" +
                "Número Documento: %s%n" +
                "Fecha Nacimiento: %s%n" +
                "Lugar Nacimiento: %s%n" +
                "Nivel Escolaridad: %s%n" +
                "Ocupación Actual: %s%n" +
                "Dirección: %s%n" +
                "Teléfono: %s%n" +
                "Correo: %s%n" +
                "Estado Civil: %s%n" +
                "Tribunal Eclesiástico: %s%n" +
                "Concepto Tribunal: %s%n" +
                "Archivo Concepto: %s%n" +
                "Nombre Cónyuge: %s%n" +
                "Número Hijos: %s%n" +
                "Nombres Hijos: %s%n" +
                "Fecha Conversión: %s%n" +
                "Ha estado apartado: %s%n" +
                "Fecha Reconciliación: %s%n" +
                "Fecha Recepción Espíritu Santo: %s%n" +
                "Fecha Bautismo: %s%n" +
                "Congregación Bautismo: %s%n" +
                "Pastor Bautismo: %s%n" +
                "Cargos Iglesia: %s%n" +
                "Contraseña: %s%n",
                id, nombres_apellidos, tipoDocumento, numeroDocumento, fechaNacimiento, lugarNacimiento,
                nivelEscolaridad, ocupacionActual, direccion, telefono, correo, estadoCivil,
                tribunalEclesiastico, conceptoTribunal, archivoConcepto, nombreConyuge,
                numeroHijos, nombresHijos, fechaConversion, haEstadoApartado,
                fechaReconciliacion, fechaRecepcionEspiritu, fechaBautismo,
                congregacionBautismo, pastorBautismo, cargosIglesia, password
            );

            message.setText(cuerpo);

            Transport.send(message);
            System.out.println("✅ Correo enviado exitosamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    	
    }

