package tn.esprit.tw.jsf.beans;

import java.io.Serializable;  

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;  
  


import org.primefaces.event.map.MarkerDragEvent;  
import org.primefaces.model.map.DefaultMapModel;  
import org.primefaces.model.map.LatLng;  
import org.primefaces.model.map.MapModel;  
import org.primefaces.model.map.Marker;  

import tn.esprit.tw.utils.GMapService;

@ManagedBean(name="mapBean")
@SessionScoped
public class MapBean implements Serializable {  
  
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{authBean.user.address}")
    private String location; // +setter

	private MapModel draggableModel;
    private Marker marker;
    
    private String address;
    private String geo;
    
    public MapBean() {  
        
    	
    }  
    
    @PostConstruct
    public void init() {
    	GMapService gs=new GMapService();
        this.setGeo(gs.getGeocode(location));
    	
    	draggableModel = new DefaultMapModel();
        
          
    	 Marker m=new Marker(new LatLng(gs.getLat().doubleValue(), gs.getLng().doubleValue()), "This is your current position !");
         m.setDraggable(true);
         draggableModel.addOverlay(m);
  
    }
      
    public MapModel getDraggableModel() {  
        return draggableModel;  
    }  
      
    public void onMarkerDrag(MarkerDragEvent event) {  
        marker = event.getMarker();  
         this.geo= marker.getLatlng().getLat() + "," + marker.getLatlng().getLng(); 
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Dragged", "Lat:" + marker.getLatlng().getLat() + ", Lng:" + marker.getLatlng().getLng()));  
    }  
     
    public void updateMapCenter() {
    	System.out.println(address);
    	GMapService gs=new GMapService();
        //LatLng geo=gs.getGeocode(address);
        this.setGeo(gs.getGeocode(address));
        draggableModel.getMarkers().clear();
        Marker m=new Marker(new LatLng(gs.getLat().doubleValue(), gs.getLng().doubleValue()), "This is your current position !");
        m.setDraggable(true);
        draggableModel.addOverlay(m);
        
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Address changed", "Adresse changé"));  
        
        
    }
    public void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}  