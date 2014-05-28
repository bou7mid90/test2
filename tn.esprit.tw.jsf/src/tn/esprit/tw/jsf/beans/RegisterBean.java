package tn.esprit.tw.jsf.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import tn.esprit.tw.ejb.domain.Citizen;
import tn.esprit.tw.ejb.domain.Role;
import tn.esprit.tw.ejb.interfaces.UserManagementLocal;


@ManagedBean(name = "registerBean")
@ViewScoped
public class RegisterBean {
	
	@EJB
	private UserManagementLocal userManagement;

	private Citizen citizen = new Citizen();
	
	 
	public void saveUser(ActionEvent actionEvent) {
		// Persist project
		FacesMessage msg = null;  
		
		if (citizen != null) {
				
				citizen.setRole(Role.CITIZEN);
				userManagement.createCitizen(citizen);
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "C'est fait", "Vous etes inscrit avec succes");
				
					
			} 
			
		else {
				msg = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "Parametres manquants",
						"Veuillez remplir les champs");
				
			}
        FacesContext.getCurrentInstance().addMessage(null, msg);  
		

	}
	
	public Citizen getCitizen() {
		return citizen;
	}

	public void setCitizen(Citizen citizen) {
		this.citizen = citizen;
	}
	
	

}
