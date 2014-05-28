package tn.esprit.tw.utils;


import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
 
public class EmailSender {
 
	
	
	private String objet;
	private String destination;
	private String message;
	private String code;
	private String sujet;
	public EmailSender() {
		
	}
	

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	


	public String getSujet() {
		return sujet;
	}


	public void setSujet(String sujet) {
		this.sujet = sujet;
	}


	public void envoi() {
		final String fromEmail = "@gmail.com"; //requires valid gmail id
        final String password = ""; // correct password for gmail id
         
        //System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
         
                //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
         
        EmailUtil.sendEmail(session, destination,"TunisianWatch Claim Validation", code,sujet);
		
	}


	
}