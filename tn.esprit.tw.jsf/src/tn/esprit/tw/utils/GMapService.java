package tn.esprit.tw.utils;


import java.math.BigDecimal;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;


public class GMapService {
		
	private Geocoder geocoder; 
	
	private BigDecimal lat;
	private BigDecimal lng;
	
	public GMapService(){
		
		this.geocoder = new Geocoder();
	}
	
	
	public String getGeocode(String address){
	GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
	
	GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
	LatLng ltn = geocoderResponse.getResults().get(0).getGeometry().getLocation();
	this.lat=ltn.getLat();
	this.lng=ltn.getLng();
	return ltn.getLat()+","+ltn.getLng();
	
	}
	public Geocoder getGeocoder() {
		return geocoder;
	}

	public void setGeocoder(Geocoder geocoder) {
		this.geocoder = geocoder;
	}


	public BigDecimal getLat() {
		return lat;
	}


	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}


	public BigDecimal getLng() {
		return lng;
	}


	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
}
