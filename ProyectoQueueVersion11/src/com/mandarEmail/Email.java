package com.mandarEmail;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.Properties;

public class Email {
	
	
	 private Transport transport ;
	 private    MimeMessage mimeMessage ;
	 
	private String emailUsuario;
	private String codigoActivacion;
	 
	public Email( String emailUsuario,String codigoActivacion) {
			// TODO Auto-generated constructor stub

		this.emailUsuario=emailUsuario;
		this.codigoActivacion=codigoActivacion;
		
	}
	 
	 
	private void generarEmail() throws MessagingException {

  
        // crear un fichero de confuguracion y guarda
        Properties properties = new Properties();

        properties.setProperty("mail.host","smtp.163.com");

        properties.setProperty("mail.transport.protocol","smtp");

        properties.setProperty("mail.smtp.auth","true");
       
        properties.setProperty("mail.smtp.socketFactory.class" , "javax.net.ssl.SSLSocketFactory");
        
        properties.setProperty("mail.smtp.socketFactory.fallback" , "false");
        
        properties.setProperty("mail.smtp.port" , "465");// configurar puerto del Servidor de correo saliente
        
        properties.setProperty("mail.smtp.socketFactory.port" , "465");
        
        

        // crear un objeto de seession
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
            	
            	// pone el nombre completo de correro , y su contraseÃ±a 
            	// (en este caso para correo del 163 es el codigo de autorizaciÃ³n )
                return new PasswordAuthentication("queueproyectodam@163.com" , "QLOEAXCEBJNEZBSC");
            }									
        });

        // abrir modo debug
        session.setDebug(true);


        //abrir conexion

	
     
		transport = session.getTransport();
		

        // conectar al servidor
    	// pone el nombre completo de correro , y su contraseå¸½a 
    	// (en este caso para correo del 163 es el cè´¸digo de autorizaciè´¸n )
        transport.connect("smtp.163.com","queueproyectodam@163.com" , "QLOEAXCEBJNEZBSC");



	     mimeMessage = new MimeMessage(session);

 
        // el correo de expedidor
      
			mimeMessage.setFrom(new InternetAddress("queueproyectodam@163.com" ));
		
	
	        // el correo de destinatarios
	        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(emailUsuario));

	
	        // titulo
	        mimeMessage.setSubject("Verifica tu nueva cuenta de Queue");
	       
	
		// crear objeto para poner texto
        MimeBodyPart text = new MimeBodyPart();
		
      
        
        // pone su texto ,  en formato "text/html;charset=UTF-8" 
        // evita el mojibake ( es un error que ocurre cuando un programa es incapaz de reconocer un carè°©cter)
	
        
        
        
        String texto="Introduce el siguiente c¨®digo para activar su cuenta:<br>"
        			+"<div  style =\"font-size: 20px;color :red \" > "+codigoActivacion+"</div><br>"
        			+"No compartas este c¨®digo con nadie.<br>"
        			+"<br>"
        			+"<br>"
        			+"<b>Gracias por su colaboraci¨®n. Esperamos volver a verte pronto.";
        
        

			text.setContent(texto,"text/html;charset=UTF-8");
			
			
			
		
	//  crear contenedor
			MimeMultipart mp = new MimeMultipart();
		
			
			mp.addBodyPart(text);	
				
			// poner contenido
			mimeMessage.setContent(mp);
			
			
			// guardar cambios
			mimeMessage.saveChanges();
			
		
	
	
	}
	
	
	public void mantar() {
		
	 /*   try {
	    
	    	
	    	generarEmail();
	    	
	
			// enviar mensaje
			transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
		
		      // cerrar conexion
		      transport.close();
		      
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
			
	}
	
	
}
