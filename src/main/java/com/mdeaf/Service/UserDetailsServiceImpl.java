package com.mdeaf.Service;

import com.mdeaf.Entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * l'interface UserDetailsService de spring security definit une seul methode qui est (loadUserByUsername)
 * je dois implémenter cette interface via la classe UserDetailsServiceImpl
 * @author amel bouguerra
 *
 */
//
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;
    
    /**
     * personnalisation de l'authentification
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        AppUser appUser=accountService.findUserByUsername(username);
        if(appUser==null) throw new UsernameNotFoundException(username);
        
        /**
         * jai l'objet user et je sais qu'il contient une collection de role
         * les roles de l'utilisateur est une collection d'objet de type GrantedAuthority
         */
        //
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        
        /**
         * parcourir tous les roles de l'utilisateur
         * et pour chaque role j'utilise authorities.add
         * et après la classe SimpleGrantedAuthority qui implémente cette interface(GrantedAuthority)
         */
        
        
        appUser.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRole()));
        });
        
        /**
         * retourner un objet User de spring
         * password de cet objet provient de la base de donnee qui est déja encodé
         * spring verifie que cet password est la meme que le password saisie par l'utilisateur
         */
        
        
        return new User(appUser.getUsername(),appUser.getPassword(),authorities);
    }
}
