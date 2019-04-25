package com.mdeaf.Web;

import com.mdeaf.Entities.AppUser;
import com.mdeaf.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
public class UserController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/users")
    public AppUser register(@RequestBody RegistrationForm registrationForm){
        return  accountService.saveUser(registrationForm.getUsername(), registrationForm.getPassword(),registrationForm.getRepassword());

    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        AppUser appUser=new AppUser();
        model.addAttribute("user", appUser);
        return "registration";

    }
}
