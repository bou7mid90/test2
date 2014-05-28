package tn.esprit.tw.jsf.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.http.client.ClientProtocolException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;





import tn.esprit.tw.ejb.domain.Category;
import tn.esprit.tw.ejb.domain.Citizen;
import tn.esprit.tw.ejb.domain.Complaint;
import tn.esprit.tw.ejb.domain.Contributor;
import tn.esprit.tw.ejb.domain.Document;
import tn.esprit.tw.ejb.domain.Location;
import tn.esprit.tw.ejb.domain.Priority;
import tn.esprit.tw.ejb.domain.State;
import tn.esprit.tw.ejb.domain.Status;
import tn.esprit.tw.ejb.domain.TypeDoc;
import tn.esprit.tw.ejb.domain.User;
import tn.esprit.tw.ejb.interfaces.CategoryManagementLocal;
import tn.esprit.tw.ejb.interfaces.ComplaintManagementLocal;
import tn.esprit.tw.ejb.interfaces.UserManagementLocal;
import tn.esprit.tw.utils.EmailSender;
import tn.esprit.tw.utils.SmsSender;

@ManagedBean
@SessionScoped
public class ComplaintBean {

	@EJB
	private ComplaintManagementLocal complaintManagement;
	@EJB
	private CategoryManagementLocal categoryManagement;
	@EJB
	private UserManagementLocal userManagement;
	
	@ManagedProperty(value="#{mapBean}")
	MapBean mapBean;
	@ManagedProperty(value="#{authBean.user}")
	User citizen;
	
	
	private String destination="E:\\tmp\\";
	 private List<UploadedFile> uploadedFiles;
	 
	 private String comment;
	 
	 private String reference;
	 
	 private Integer rating;
	 
	 private Date date;
	
	List<Category> categories;
	
	List<Category> subcategories;
	
	private Category montherCategory = new Category();
	
	private Category subCategory = new Category();
	
	private Complaint complaint = new Complaint();
	
	private Complaint lastComplaint = new Complaint();
	
	private Location location = new Location();
	
	private List<Document> docs;
	
	private Status status = new Status();
	
	
	private int selectedCategory;
	private int selectedSubcategory;
	
	private boolean skip;
	private static Logger logger = Logger.getLogger(ComplaintBean.class.getName());

	
	public ComplaintBean(){
		
		
		
	}
	@PostConstruct
    public void init(){
		
		categories= categoryManagement.getMonthercategories();
		uploadedFiles = new ArrayList<UploadedFile>();
		setDocs(new ArrayList<Document>());
    }

	public Category getMontherCategory() {
		return montherCategory;
	}

	public void setMontherCategory(Category montherCategory) {
		this.montherCategory = montherCategory;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}

	public Category getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Category subCategory) {
		this.subCategory = subCategory;
	}
	public boolean isSkip() {
		return skip;
	}
	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	public String onFlowProcess(FlowEvent event) {
		logger.info("Current wizard step:" + event.getOldStep());
		logger.info("Next step:" + event.getNewStep());

		if (skip) {
			skip = false; // reset in case project goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}
	public int getSelectedCategory() {
		return selectedCategory;
	}
	public void setSelectedCategory(int selectedCategory) {
		this.selectedCategory = selectedCategory;
		this.montherCategory =categoryManagement.findCategory(selectedCategory);
	}
	public List<Category> getCategories() {
		categories = categoryManagement.getMonthercategories();
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public List<Category> getSubcategories() {
		 montherCategory = categoryManagement.findCategory(selectedCategory);
		subcategories = categoryManagement.getSubcategoriesByCategory(montherCategory);
		return subcategories;
	}
	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}
	public int getSelectedSubcategory() {
		return selectedSubcategory;
	}
	public void setSelectedSubcategory(int selectedSubcategory) {
		this.selectedSubcategory = selectedSubcategory;
		this.subCategory=categoryManagement.findCategory(selectedSubcategory);
	}
	
	public String save(){
		
		
		String ref = "R"+generate(3)+"F"+generate(2);
		saveFiles();
		
		location.setAddress(mapBean.getAddress());
		location.setLatln(mapBean.getGeo());
		
		status.setStatus(State.APRROVED);
		status.setDate(new Date());
		status.setComment("Réclamation approuvée par l'administrateur");
		
		Citizen cit = userManagement.findCitizenById(citizen.getId());
		
		complaint.setCategory(subCategory);
		complaint.setComment(comment);
		complaint.setDate(date);
		complaint.setReference(ref);
		complaint.setAddedDate(new Date());
		complaint.setDocs(getDocs());
		complaint.setLocation(location);
		complaint.setStatus(status);
		complaint.setCitizen(cit);
		Contributor cont = userManagement.findContributorById(61);
		complaint.setContributor(cont);
		// Setting complaint priority
		if (rating==3) complaint.setPriority(Priority.MEDIUM);
		else if (rating>3) complaint.setPriority(Priority.HIGH);
		else complaint.setPriority(Priority.LOW);
		
		System.out.println("Saving complaint...");
		
		complaintManagement.createComplaint(complaint);
		
		
		// Enovi de mail
		String sujet = complaint.getCategory().getMontherCategory().getTitle()+","+complaint.getCategory().getTitle();
		
		EmailSender sendMail = new EmailSender();
		sendMail.setCode(complaint.getReference());
		sendMail.setDestination(complaint.getCitizen().getMail());
		sendMail.setSujet(sujet);
		sendMail.envoi();		
		
		
		// Enovi SMS
		
		String msg = "Votre+reclamation+portant+sur+"+sujet+"+est+enregistre+sous+la+reference+"+complaint.getReference()+".Elle+sera+examinee+par+une+authorite+qualifiee+chez+TW.+";
		String msg1 = "Une+reclamation+portant+sur+"+sujet+"+vient+de+vous+etre+affecte+par+la+plateforme+Tunisian+Watch.Veuillez+proceder+a+son+suivi+.Merci.";

		SmsSender sms = new SmsSender();
		sms.setMessage(msg);
		sms.setPhoneNumber(complaint.getCitizen().getTelephone());
		SmsSender sms1 = new SmsSender();
		sms1.setMessage(msg1);
		sms1.setPhoneNumber(complaint.getContributor().getTelephone());
		/*try {
			sms.send();
			sms1.send();
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}*/
		lastComplaint= complaint;
		clean();
		
		String navigateTo ="/citizen/validateComplaint";
		
		return navigateTo;
		
	}
	
	public void clean(){
		
		complaint = new Complaint();
		status = new Status();
		montherCategory = new Category();		
		subCategory = new Category();
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public MapBean getMapBean() {
		return mapBean;
	}
	public void setMapBean(MapBean mapBean) {
		this.mapBean = mapBean;
	}
	
	
	public void upload(FileUploadEvent event) {
		
		uploadedFiles.add(event.getFile());
		
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        

    }  
	
	public void saveFiles(){
		
		for (UploadedFile uploadedFile : uploadedFiles) {
			Document doc = new Document();
			
			if (uploadedFile.getContentType().contains("pdf"))
			doc.setType(TypeDoc.PDF);
			else 
				doc.setType(TypeDoc.PICTURE);
			
			doc.setPath(destination+uploadedFile.getFileName());
			doc.setComplaint(getComplaint());
			
			docs.add(doc);
			
			try {
	            copyFile(uploadedFile.getFileName(), uploadedFile.getInputstream());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
        
        
	}
    public void copyFile(String fileName, InputStream in) {
           try {
             
             
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));
             
                int read = 0;
                byte[] bytes = new byte[1024];
             
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
             
                in.close();
                out.flush();
                out.close();
             
                System.out.println("New file created!");
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
    }
	
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<UploadedFile> getUploadedFiles() {
		return uploadedFiles;
	}
	public void setUploadedFiles(List<UploadedFile> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Document> getDocs() {
		return docs;
	}
	public void setDocs(List<Document> docs) {
		this.docs = docs;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	 public User getCitizen() {
			return citizen;
		}
		public void setCitizen(User citizen) {
			this.citizen = citizen;
		}
		public String getReference() {
			return reference;
		}
		public void setReference(String reference) {
			this.reference = reference;
		}
		
		public String generate(int length)
		{
			    String chars = "ABC1234567890"; // Tu supprimes les lettres dont tu ne veux pas
			    String pass = "";
			    for(int x=0;x<length;x++)
			    {
			       int i = (int)Math.floor(Math.random() * 13); // Si tu supprimes des lettres tu diminues ce nb
			       pass += chars.charAt(i);
			    }
			  //  System.out.println(pass);
			    return pass;
		}
		public Complaint getLastComplaint() {
			return lastComplaint;
		}
		public void setLastComplaint(Complaint lastComplaint) {
			this.lastComplaint = lastComplaint;
		}
	
}
