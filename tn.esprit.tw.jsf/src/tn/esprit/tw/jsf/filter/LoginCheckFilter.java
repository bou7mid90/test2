package tn.esprit.tw.jsf.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tn.esprit.tw.ejb.domain.User;


/**
 * Servlet Filter implementation class UserCheckFilter
 */
public class LoginCheckFilter extends AbstractFilter implements Filter {
	private static List<String> allowedURIs;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		if(allowedURIs == null){
			allowedURIs = new ArrayList<String>();
			allowedURIs.add(fConfig.getInitParameter("loginActionURI"));
			allowedURIs.add("/TunisianWatch/javax.faces.resource/project_style.css.xhtml");		
			allowedURIs.add("/TunisianWatch/javax.faces.resource/jsf.js.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/primefaces.js.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/primefaces.css.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/footer.js.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/jsf.js.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/login.js.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/theme.css.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/jquery/jquery.js.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/jquery/jquery-plugins.js.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/jsf.js.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/france.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/anglais.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/bootstrap.min.css.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/DT_bootstrap.css.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/arab.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/profil.jpg.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/Skype-icon.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/lock-icon.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/favicon.ico.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/LogoTW.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/messages/messages.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/bootstrap-responsive.min.css.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/ui-icons_38667f_256x240.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/ui-icons_898989_256x240.png.xhtml");
			allowedURIs.add("/TunisianWatch/javax.faces.resource/images/ui-icons_898989_256x240.png.xhtml");
			allowedURIs.add("/TunisianWatch/rest/");
			


		}
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.isNew()) {
			doLogin(request, response, req);
			return;
		}

		User user = (User) session.getAttribute("user");

		if (user == null && !allowedURIs.contains(req.getRequestURI())) {
			System.out.println(req.getRequestURI());
			doLogin(request, response, req);
			return;
		}

		chain.doFilter(request, response);
	}
}