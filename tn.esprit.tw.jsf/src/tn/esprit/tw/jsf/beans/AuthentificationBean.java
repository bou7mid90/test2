package tn.esprit.tw.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import tn.esprit.tw.ejb.domain.Admin;
import tn.esprit.tw.ejb.domain.Category;
import tn.esprit.tw.ejb.domain.Citizen;
import tn.esprit.tw.ejb.domain.Complaint;
import tn.esprit.tw.ejb.domain.Contributor;
import tn.esprit.tw.ejb.domain.Document;
import tn.esprit.tw.ejb.domain.Location;
import tn.esprit.tw.ejb.domain.Priority;
import tn.esprit.tw.ejb.domain.Role;
import tn.esprit.tw.ejb.domain.State;
import tn.esprit.tw.ejb.domain.Status;
import tn.esprit.tw.ejb.domain.TypeDoc;
import tn.esprit.tw.ejb.domain.User;
import tn.esprit.tw.ejb.interfaces.CategoryManagementLocal;
import tn.esprit.tw.ejb.interfaces.ComplaintManagementLocal;
import tn.esprit.tw.ejb.interfaces.UserManagementLocal;

@ManagedBean(name="authBean")
@SessionScoped
public class AuthentificationBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@EJB
	private UserManagementLocal userManagement;
	@EJB
	private CategoryManagementLocal categoryManagement;
	@EJB
	private ComplaintManagementLocal complaintManagement;
	private String email;
	private String password;
	private User user;
	private String location;
	public String hidden1;

	

public void itShouldCreateCategory(){
		
		Category cat = new Category();
		
		cat.setTitle("Category One");
		cat.setDescription("This the category one");
		
		categoryManagement.createCategory(cat);
	}
public void itShouldCreateSubcategories(){
	
	Category subcat1 = new Category();
	subcat1.setTitle("Subcategory 1");
	subcat1.setDescription("This the first subcategory of the category One");
	
	Category subcat2 = new Category();
	subcat2.setTitle("Subcategory 2");
	subcat2.setDescription("This the second subcategory of the category One");
	
	Category cat = categoryManagement.findCategory(1);
	
	subcat1.setMontherCategory(cat);
	subcat2.setMontherCategory(cat);
	
	cat.getSubcategories().add(subcat1);
	cat.getSubcategories().add(subcat2);
	
	categoryManagement.updateCategory(cat);
		
}

public void itShouldCreateComplaint(){
	
	Category subcat = categoryManagement.findCategory(10);
	Complaint complaint= new Complaint();
	Status statut = new Status();
	Document doc = new Document();
	Location loc = new Location();
	
	statut.setComment("RS");
	statut.setDate(new Date());
	statut.setStatus(State.IN_PROGRESS);
	
	doc.setType(TypeDoc.PDF);
	doc.setPath("TW/pdf1");
	
	loc.setAddress("Esprit");
	
	
	Citizen cit = (Citizen) userManagement.findUserById(5);
	
	complaint.setDate(new Date());
	complaint.setPriority(Priority.HIGH);
	complaint.setComment("My third complaint");
	complaint.setReference("CSD323d461");
	
	doc.setComplaint(complaint);
	subcat.getComplaints().add(complaint);
	complaint.setStatus(statut);
	complaint.setCitizen(cit);
	complaint.setLocation(loc);
	complaint.setCategory(subcat);
	List<Document> docs = new ArrayList<Document>();
	docs.add(doc);
	complaint.setDocs(docs);
	complaintManagement.createComplaint(complaint);
	//complaint.getDocs().add(doc);
	

}

public void itShouldDeleteComplaint(){
	
	Complaint comp = complaintManagement.getComplaintById(18);
	System.out.println(comp.getReference());
	complaintManagement.removeComplaint(comp);
}

	public String login(){
		String value = FacesContext.getCurrentInstance().
				getExternalContext().getRequestParameterMap().get("hidden1");
			    setHidden1(value);
		
		
		String navigateTo =null;
		System.out.println(email+" *******"+password);
		User found = userManagement.isValidLogin(email, password); 
		if (found == null)
		{
			//itShouldCreateCategory();
			//itShouldCreateSubcategories();
			//itShouldCreateComplaint();
			//itShouldDeleteComplaint();
			FacesMessage msg = null;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "Verifier vos identifiants");

			Contributor admin = new Contributor();
			admin.setFirstname("Rifi");
			admin.setLastname("Mariem");
			admin.setAddress("Tunis");
			admin.setMail("mariem@rifi.tn");
			admin.setPassword("rifi");
			admin.setRole(Role.CONTRIBUTOR);
			admin.setTelephone("123456");
			
			Complaint complaint=complaintManagement.getComplaintByRef("RQDPCM");
			System.out.println(complaint.getComment());
			//admin.setCin("093344566");
			
			//userManagement.createContributor(admin);
			
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
		else if (found.isAdmin()){
			
			Category cat = categoryManagement.findCategory(1);
			List<Category> listCat = cat.getSubcategories();
			
			System.out.println(listCat.get(0).getDescription());
			
			user = found;
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			navigateTo ="/admin/admin";
		}
		else if (found.isCitizen()){
			user = found;
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			navigateTo ="/citizen/citizen";
		}
		else if (found.isContributor()){
			user = found;
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			navigateTo ="/contributor/contributor";
		}
		return navigateTo;
		
	}
	
	
	public boolean isAdmin() {
		return user.isAdmin();
	}
	
	public boolean isCitizen() {
		return user.isCitizen();
	}
	public boolean isContributor() {
		return user.isContributor();
	}
	
	public UserManagementLocal getUserManagement() {
		return userManagement;
	}


	public void setUserManagement(UserManagementLocal userManagement) {
		this.userManagement = userManagement;
	}


	public String logout() {
		getRequest().getSession().invalidate();
		System.out.println(email+" *******"+password);
		return "/public/login";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHidden1() {
		return hidden1;
	}
	public void setHidden1(String hidden1) {
		this.hidden1 = hidden1;
	}
}
