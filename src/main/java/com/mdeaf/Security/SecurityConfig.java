package com.mdeaf.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
/**
 * l'interface UserDetailsService fournit par spring security
 */
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * se baser sur l'interface UserDetailsService pour gerer l'authentification
	 */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception{
    	/**
    	 * pour authentifier un utilisateur on va se baser sur l'interface 
    	 * userDetailsService qui est fournit par spring
    	 * une fois que tu récupère l'utilisateur que je te donne
    	 * crypte le password saisie par l'utilisateur et  vérifie qu'elle corresponds au password dans la base de donnée
    	 * si c'est bon elle passe
    	 */
    	
        authentication.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Redéfinir la methode configure ou on définit les règles
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        /**
         * don't create session
         * car il bloque l'authentification STATELESS
         */
    	
        http.csrf().disable();
        
        /**
         * me baser sur JWT donc utiliser STATELESS
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                
        /**
         * avec (/users)=>ajouter un utilisateur. pour s'authentifier utiliser(/login)
         */
        http.authorizeRequests().antMatchers("/users/**","/login/**").permitAll();
        /**
         * avec (/tasks)=> ajouter une tache je dois etre un ADMIN
         */
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/tasks/**").hasAuthority("ADMIN");
        
        /**
         * tout le reste nécesitte une authentification
         */
        http.authorizeRequests().anyRequest().authenticated();
        
       
        /**
         * Transmettre cette objet(authenticationManager()) au filtre JWTAuthenticationFilter
           qui intervient uniquement au moment de l'authentification
	        La méthode authenticationManager() herite de WebSecurityConfigurerAdapter
	        et qui permet de retourner l'objet authenticationManager de spring
	        l'objet authenticationManager contient le contexte de sécurité de spring::
	        =>tout ce qui concerne l'utilisateur authentifié
	    **/
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        
        /**
         * Le filtre JWTAuthorizationFilter doit intervenir à chaque requête 
         * pour vérifier si la requète contient le token
         */
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


    }

}
