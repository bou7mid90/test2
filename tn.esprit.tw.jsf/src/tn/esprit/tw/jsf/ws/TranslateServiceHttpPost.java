package tn.esprit.tw.jsf.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.4.6
 * 2014-05-05T23:31:50.716+01:00
 * Generated source version: 2.4.6
 * 
 */
@WebService(targetNamespace = "http://www.webservicex.net", name = "TranslateServiceHttpPost")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface TranslateServiceHttpPost {

    /**
     * Convert text from one lanaguage to another language .Supported languages are English to Chinese,English to French,English to German,English to Italian,English to Japanese,English to Korean,English to Portuguese,English to Spanish,Chinese to English,French to English,French to German,German to English,German to French,Italian to English,Japanese to English,Korean to English,Portuguese to English,Russian to English,Spanish to English.
     */
    @WebResult(name = "string", targetNamespace = "http://www.webservicex.net", partName = "Body")
    @WebMethod(operationName = "Translate")
    public java.lang.String translate(
        @WebParam(partName = "LanguageMode", name = "LanguageMode", targetNamespace = "")
        java.lang.String languageMode,
        @WebParam(partName = "Text", name = "Text", targetNamespace = "")
        java.lang.String text
    );
}
