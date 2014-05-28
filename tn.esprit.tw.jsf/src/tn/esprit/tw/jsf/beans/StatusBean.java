package tn.esprit.tw.jsf.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import tn.esprit.tw.ejb.domain.Complaint;
import tn.esprit.tw.ejb.interfaces.ComplaintManagementLocal;


@ManagedBean(name = "statusBean")
@SessionScoped
public class StatusBean {
	
	@EJB
	private ComplaintManagementLocal complaintManagement;
	
	private Complaint complaint = new Complaint();
	
	private String ref;
	
	public String getComplaintStatus(){
		System.out.println("Ref "+getRef());
		complaint=complaintManagement.getComplaintByRef(getRef());
		
		if (complaint!= null){
				
			return "/citizen/complaintStatus";
			 
		}
		else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur", "Numero de reference incorrect !"));  

		return null;
		}
		
	}


	public Complaint getComplaint() {
		return complaint;
	}


	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}


	public String getRef() {
		return ref;
	}


	public void setRef(String ref) {
		this.ref = ref;
	}
	
	

}
