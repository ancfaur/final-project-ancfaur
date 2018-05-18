package offersApp.config.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static offersApp.constants.ApplicationConstants.Roles.ADMINISTRATOR;
import static offersApp.constants.ApplicationConstants.Roles.AGENT;
import static offersApp.constants.ApplicationConstants.Roles.CUSTOMER;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        if (role.contains("ROLE_"+ ADMINISTRATOR)) return "/admin/menu";
        if (role.contains("ROLE_"+ AGENT)) return "/agent/menu";
        if (role.contains("ROLE_"+ CUSTOMER)) return "/customer/showOffers";
        return "/error";
    }
}