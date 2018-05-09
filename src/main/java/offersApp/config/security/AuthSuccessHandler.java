package offersApp.config.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        if (role.contains("ROLE_ADMINISTRATOR")) return "/admin/adminMenu";
        if (role.contains("ROLE_AGENT")) return "/agent/agentMenu";
        if (role.contains("ROLE_USER")) return "/user/userMenu";
        return "/error";
    }
}