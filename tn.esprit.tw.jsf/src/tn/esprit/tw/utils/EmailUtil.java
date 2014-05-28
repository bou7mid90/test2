package tn.esprit.tw.utils;


 
import java.util.Date;
 
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class EmailUtil {
 
    /**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendEmail(Session session, String toEmail, String subject, String ref,String sujet){
        try
        {
          MimeMessage msg = new MimeMessage(session);
          //set message headers
          msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
          msg.addHeader("format", "flowed");
          msg.addHeader("Content-Transfer-Encoding", "8bit");
 
          msg.setFrom(new InternetAddress("noreply@tunisianwatch.tn", "NoReply-TunisianWatch"));
 
          msg.setReplyTo(InternetAddress.parse("noreply@tunisianwatch.tn", false));
 
          msg.setSubject(subject, "UTF-8");
          
         
 
          msg.setContent("<p>Bonjour et bienvenue sur TunisianWatch cher citoyen</p>" +
          		"<p></p>" +
          		"<p>Nous vous informons que votre reclamation portant sur "+sujet+" est bien enregistre dans notre plateforme sous le numero de reference <strong>"+ref+"</strong>.</p>" +
          		"<p></p>" +
          		"<p>Votre reclamation serait approuve par nos administrateurs pour etre prise par une authorite qualifiee pour traiter votre demande.</p>"+
          		"<p>Pour vous tenir plus pres de nos services, nous mettons a  votre disposition un espace de suivi des reclamations sur notre site pour suivre l'etat de votre reclamation </p>" +
          		"<p>Utilisez le code de reference de votre reclamation: <strong>"+ref+"</strong> pour suivre l'etat de votre reclamation. Cependant, nous continuons a vous informer par email des qu'il y'aura de nouveaux a propos de votre demande.</p>" +
          		"<p> </p>" +
          		"<p>Merci pour la confiance que vous accordez a nos services.</p>" +
          		"<p></p>" +
          		"<p><em>Tunisian Watch, La Plateforme au service de ses citoyens </em></p>",
        		  
        		  "text/html");
 
          msg.setSentDate(new Date());
 
          msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
          //System.out.println("Message is ready");
          Transport.send(msg);  
 
          System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
          e.printStackTrace();
        }
    }
}