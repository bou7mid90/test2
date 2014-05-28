package tn.esprit.tw.jsf.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="language")
@SessionScoped
public class LanguageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    private static Map<String, String> availableLanguages;

    static {
        availableLanguages = new LinkedHashMap<String, String>();
        availableLanguages.put("Arabe", "ar");
        availableLanguages.put("English", "en");
        availableLanguages.put("Français", "fr");
    }

    public Map<String, String> getAvailableLanguages() {
        return availableLanguages;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

}