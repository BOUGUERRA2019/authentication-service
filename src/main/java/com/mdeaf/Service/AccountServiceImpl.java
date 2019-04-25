package com.mdeaf.Service;

import com.mdeaf.Entities.AppRole;
import com.mdeaf.Entities.AppUser;
import com.mdeaf.Repository.AppRoleRepository;
import com.mdeaf.Repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
    	//verifier si utilisateur n'existe pas dans la base de donnee
        AppUser user=appUserRepository.findByUsername(username);
        //si utilisateur existe deja donc je genere une exception
        if(user!=null)throw new RuntimeException("utilisateur existe déja, Veuillez saisir un autre nom utilisateur !");
        
        //si le contenu des deux champs password et confirmedPassword sont différent donc je genere une exception
        if(!password.equals(confirmedPassword)) throw new RuntimeException("Veuillez Confirmer votre mot de passe !");
        
        /*si c'est bon je récupère le contenu des champs saisie par l'utilisateur et les ajouter
        	à l'instance appUser de l'objet AppUser via les setters*/
        AppUser appUser=new AppUser();
        appUser.setUsername(username);
        //je crypte le mot de passe saisie par utilisateur et je l'envoi vers la base de donnée
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUser.setActived(true);
        
        //enregistrer le nouveau utilisateur
        appUserRepository.save(appUser);
        
        //Affecter à cette utilisateur le role USER
        addRoleToUser(username,"USER");
        return appUser;
    }

    @Override
    public AppRole saveRole(AppRole r) {
        return appRoleRepository.save(r);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRole(role);
        appUser.getRoles().add(appRole);

    }
}
