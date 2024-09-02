package com.portal.bid.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.portal.bid.config.PermissionConfig;
import com.portal.bid.entity.User;
import com.portal.bid.security.CustomUserDetails;
import com.portal.bid.service.*;
import com.portal.bid.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil util;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRolePermissionService userRolePermissionService;

    @Autowired
    private UserRolesService userRolesService;



    @Autowired
    private UserService userService;

    @Autowired
    private PermissionConfig permissionConfig;

    private Map<String, String> permissionEndpointMap;
    @Autowired
    public SecurityFilter(PermissionConfig permissionConfig) {
        this.permissionEndpointMap = permissionConfig.permissionEndpointMap();
    }


    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        System.out.println("Entering Security Filter");

        // Extract the Authorization header
        String token = request.getHeader("Authorization");
        System.out.println("Authorization header: " + token);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            System.out.println("Extracted token: " + token);

            String username = util.getSubject(token);
            System.out.println("Extracted username: " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = userDetailsService.loadUserByUsername(username);
                System.out.println("Loaded user details: " + user);

                boolean isValid = util.isValidToken(token, user.getUsername());
                System.out.println("Is token valid: " + isValid);

                if (isValid) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication set in SecurityContext");
                }
            }
        } else {
            System.out.println("Token is null or does not start with 'Bearer '");
        }

        // Check if the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            System.out.println("User is authenticated: " + user);

            // Bypass permission check for the specific user
            if ("pragya.dechalwal@gmail.com".equals(user.getUsername())) {
                System.out.println("Bypassing permission check for user: " + user.getUsername());
                filterChain.doFilter(request, response);  // Proceed with the filter chain
                return;
            }

//            // Fetch user roles
//            Set<Integer>  userRoles = userRolesService.getRolesForUser(user.getId());
//            //Fetch roles permission
//
            //after that store it in userpermssion
            Set<String> userPermissions = userRolePermissionService.getPermissionsForUser(user.getId());
            System.out.println("User permissions: " + userPermissions);

            // Get the method and URI
            String methodAndUri = request.getMethod() + ":" + request.getRequestURI();
            System.out.println("Request method and URI: " + methodAndUri);

            // Implement logic to check permissions against the current request
            if (!hasPermissionForResource(userPermissions, methodAndUri)) {
                System.out.println("User does not have permission for resource");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("You don't have permission to access this resource.");
                return;
            }
        } else {
            System.out.println("User is not authenticated or principal is not of type UserDetails");
        }

        filterChain.doFilter(request, response);
        System.out.println("Filter chain processed");
    }


    private boolean hasPermissionForResource(Set<String> permissions, String methodAndUri) {
        for (String permission : permissions) {
            String mappedUri = permissionEndpointMap.get(permission);
            System.out.println("permission "+permission+" mappedURI "+mappedUri);
            if (mappedUri != null && matchesUriPattern(methodAndUri, mappedUri)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesUriPattern(String requestUri, String mappedUri) {
        // Split URIs into method and path
        String[] requestParts = requestUri.split(":", 2);
        String[] mappedParts = mappedUri.split(":", 2);
//        for(int i =0;i<requestParts.length;i++){
//            System.out.println(requestUri +" pragya "+ mappedUri);
//            System.out.println(mappedParts[i]);
//        }

        if (requestParts.length != 2 || mappedParts.length != 2) {
            return false;
        }

        String requestPath = requestParts[1];
        String mappedPath = mappedParts[1];

        // Check if the HTTP methods match
        if (!requestParts[0].equals(mappedParts[0])) {
//            System.out.println(requestParts[0]+" "+mappedParts[0]);
            return false;
        }


        // Replace variable segments in paths with a regex pattern
        String regexPath = mappedPath.replaceAll("\\{[^/]+\\}", "[^/]+");

        // Match the request path against the regex pattern
        return requestPath.matches(regexPath);
    }

}

