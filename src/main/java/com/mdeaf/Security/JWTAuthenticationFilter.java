package com.mdeaf.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdeaf.Entities.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }
    
/**
 * La méthode attemptAuthentication sert à aller vers la requête http pour récupérer username et password
 * pour les donner à spring sécurity
 * @param request
 * @param response
 * @return authenticationManager.authenticate()
 * @throws AuthenticationException
 */
    //@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletRequest response)
            throws AuthenticationException {
    	
        AppUser user=null;
        try{
        	
        	/**
        	 * Récupérer username et password qui sont envoyés en format JSON
        	 * =>utiliser JAKSON (ObjectMapper() et readValue)
        	 * je vais dissérialiser à partir de l'objet request via la méthode getInputStream() pour récupérer le corps de la requête
        	 * et stoke le moi dans l'objet  de type AppUser
        	 */
            user=new ObjectMapper().readValue(request.getInputStream(), AppUser.class);

        }
        catch (Exception e){
            throw new RuntimeException(e);

        }
        /**
         * retourner à spring security authenticationManager.authenticate()
         * je demande à spring security authentifie moi un utilisateur
         * - c'est quoi l'objet user? - c'est UsernamePasswordAuthenticationToken qui a besoin de username et password
         */
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        ));

    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
             FilterChain chain, Authentication authResult) throws IOException, ServletException{

    	/**
    	 * retourner l'objet user de spring security qui est authentifié via le paramètre Authentication
    	 * authResult.getPrincipal()
    	 * il accède au contexte pour me donner l'utilisateur authentifié
    	 */
        User springUser=(User) authResult.getPrincipal();
        
        /**
         * Génerer le token jwtToken
         * redéfinir les claims (les revendications)
         */
        String jwtToken= Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                
                /**
                 * signer le token avec ES512 dons j'ai besoin d'un secret
                 * si l'algorthime est RSA jai besoin de clé publique et clé privée
                 */
                .signWith(SignatureAlgorithm.ES512,SecurityConstants.SECRET)
                /**
                 * ajouter mes claims privés son nom  est roles et sa valeur est les authorites
                 */
                .claim("roles",springUser.getAuthorities())
                /**
                 * avec compact pour que le token devient une chaine de caractère
                 */
                .compact();
        /**
         * mettre le token dans l'objet response
         * dans l'objet response je vais ajouter une entete qui s'appelle HEADER_STRING
         * dans l'entete auhtorisation il va mettre le token jwtToken
         * cad j'envoi dans l'entete authorisation Bearer suivit du token
         */
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFFIX+jwtToken);


    }


}
