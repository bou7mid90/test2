package tn.esprit.tw.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import tn.esprit.tw.ejb.domain.Complaint;
import tn.esprit.tw.ejb.domain.Contributor;
import tn.esprit.tw.ejb.domain.State;
import tn.esprit.tw.ejb.domain.User;
import tn.esprit.tw.ejb.interfaces.ComplaintManagementLocal;
import tn.esprit.tw.ejb.interfaces.UserManagementLocal;

@ManagedBean(name = "contributerBean")
@SessionScoped
public class ContributerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ComplaintManagementLocal complaintManagement;
	@EJB
	private UserManagementLocal userManagement;

	@ManagedProperty(value = "#{authBean.user}")
	User contributer;
	
	private Complaint selected = new Complaint();
	
	private Complaint selectedClaim = new Complaint();

	List<Complaint> claims = new ArrayList<Complaint>();
	
	private MapModel advancedModel;

	private Marker marker;

	public ContributerBean() {
		
		

	}
	@PostConstruct
	public void init() {
		advancedModel = new DefaultMapModel();
		Contributor cont = userManagement.findContributorById(contributer
				.getId());
		claims = complaintManagement.getComplaintsByContributor(cont);
		

		for (int i = 0; i < claims.size(); i++) {
			

			String str = claims.get(i).getLocation().getLatln();
			String[] temp;
			String delimiter = ",";

			temp = str.split(delimiter, 2);

			double lat = Double.parseDouble(temp[0]);
			double lng = Double.parseDouble(temp[1]);

			System.out.println(lat + "--- " + lng);
			LatLng coord1 = new LatLng(lat, lng);

			
			if(claims.get(i).getStatus().getStatus().equals(State.APRROVED))
			{
			
			advancedModel.addOverlay(new Marker(coord1, claims.get(i).getCategory().getMontherCategory().getTitle()+" "
					+claims.get(i).getCategory().getTitle()+" \n", claims.get(i).getComment()
					+" #Faite par Hedhli Meher # ("+claims.get(i).getStatus().getStatus()+")"
					
					));
			}
			else {
				
				advancedModel.addOverlay(new Marker(coord1, claims.get(i).getCategory().getMontherCategory().getTitle()+" "
						+claims.get(i).getCategory().getTitle()+" \n", claims.get(i).getComment()
						+" #Faite par Hedhli Meher # ("+claims.get(i).getStatus().getStatus()+")"
						
						,
						"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
				
			}
			
		}

	}

	
	public void saveClaim(){
		
		
	}
	
	public void updateClaim(){
		
		if (getSelectedClaim() != null) {
			complaintManagement.updateComplaint(getSelectedClaim());
			clear();
			loadClaims();
		}
		
	}
	public void loadClaims() {
		claims.clear();
		
	}
	public void clear() {
		selectedClaim = new Complaint();
		
	    selectedClaim = new Complaint();
		
	}
	public void update(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage("La reclamation " + selectedClaim.getReference()
						+ " est mise a jour"));
	}
	
	
	public MapModel getAdvancedModel() {
		return advancedModel;
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		marker = (Marker) event.getOverlay();
		//System.out.println(claims.get(0).getReference());
	}

	public Marker getMarker() {
		return marker;
	}

	public User getContributer() {
		return contributer;
	}

	public void setContributer(User contributer) {
		this.contributer = contributer;
	}

	public List<Complaint> getClaims() {
		Contributor cont = userManagement.findContributorById(contributer
				.getId());
		claims = complaintManagement.getComplaintsByContributor(cont);
		return claims;
	}

	public void setClaims(List<Complaint> claims) {
		this.claims = claims;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}


	public Complaint getSelectedClaim() {
		return selectedClaim;
	}

	public void setSelectedClaim(Complaint selectedClaim) {
		this.selectedClaim = selectedClaim;
	}
	public Complaint getSelected() {
		return selected;
	}
	public void setSelected(Complaint selected) {
		this.selected = selected;
	}

}
