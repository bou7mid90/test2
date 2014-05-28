package tn.esprit.tw.jsf.ws.clientsample;

import tn.esprit.tw.jsf.ws.*;

public class ClientSample {

	public static void main(String[] args) {
	        System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        TranslateService service1 = new TranslateService();
	        System.out.println("Create Web Service...");
	        TranslateServiceSoap port1 = service1.getTranslateServiceSoap();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port1.translate("EnglishTOFrench","Hello my friend !"));
	        //Please input the parameters instead of 'null' for the upper method!
	/*
	        System.out.println("Create Web Service...");
	        TranslateServiceSoap port2 = service1.getTranslateServiceSoap();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port2.translate(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        TranslateServiceSoap port3 = service1.getTranslateServiceSoap12();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port3.translate(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        TranslateServiceSoap port4 = service1.getTranslateServiceSoap12();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port4.translate(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        TranslateServiceHttpGet port5 = service1.getTranslateServiceHttpGet();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port5.translate(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        TranslateServiceHttpGet port6 = service1.getTranslateServiceHttpGet();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port6.translate(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        TranslateServiceHttpPost port7 = service1.getTranslateServiceHttpPost();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port7.translate(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        TranslateServiceHttpPost port8 = service1.getTranslateServiceHttpPost();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port8.translate(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	*/
	        System.out.println("***********************");
	        System.out.println("Call Over!");
	}
}
