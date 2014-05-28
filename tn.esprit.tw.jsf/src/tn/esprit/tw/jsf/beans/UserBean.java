package tn.esprit.tw.jsf.beans;


import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import tn.esprit.tw.ejb.domain.Admin;
import tn.esprit.tw.ejb.domain.Citizen;
import tn.esprit.tw.ejb.domain.Contributor;
import tn.esprit.tw.ejb.domain.User;
import tn.esprit.tw.ejb.interfaces.UserManagementLocal;


@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private UserManagementLocal userManagement;

	private User user;
	private List<User> users;
	private User selectedUser = new User();
	private User selected = new User();

	//Save methode
	public void saveUser() {
		// Persist project
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getMaximumSeverity() != null)

		{
			if (selectedUser != null) {
				
				
			/*	if (selectedUser.isAdmin()){ 
					Admin admin = new Admin();
					admin.setAddress(selected.getAddress());
					
					
					userManagement.createAdmin(admin);
					
				}
				if (selectedUser.isCitizen()){ Citizen citizen = (Citizen)selectedUser; userManagement.createCitizen(citizen);}
				if (selectedUser.isContributor()){ Contributor contributor = (Contributor)selectedUser; userManagement.createContributor(contributor);}
				*/
				userManagement.createUser(getSelectedUser());
				loadUsers();
				clear();
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "Paramètres manquants",
						"Veuillez remplir les champs");
				FacesContext.getCurrentInstance().addMessage(null, message);

				FacesContext.getCurrentInstance().getExternalContext()
						.getFlash().setKeepMessages(true);
			}
		}

	}

	public void save(ActionEvent actionEvent) {
		// Persist user
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage("User " + selectedUser.getFirstname()
						+ " ajouté avec succès"));

	}
	
	
	
	//update methode
	public void updateUser() {
		// Persist project

		if (getSelectedUser() != null) {
			userManagement.updateUser(getSelectedUser());
			clear();
			loadUsers();
		}
	}

	public void update(ActionEvent actionEvent) {
		// Persist user
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage("User " + selectedUser.getFirstname()
						+ " modifier avec succes"));

	}
	
	
	//delete methode

	public void deleteUser() {
		if (getSelectedUser().getId() != 0) {
			userManagement.removeUser(getSelectedUser());
			clear();
			loadUsers();
		}

	}

	public void delete(ActionEvent actionEvent) {

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage("User " + selected.getFirstname()
						+ " supprimer avec succes"));

	}

	
	//load list methode
	public void loadUsers() {
		users.clear();
		getUser();
	}

	//clear list methode
	public void clear() {
		selectedUser = new User();
		selected = new User();
	}

	//Getters&Setters
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		users = userManagement.getAllUsers();
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public User getSelected() {
		return selected;
	}

	public void setSelected(User selected) {
		this.selected = selected;
	}
}
