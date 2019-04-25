package com.mdeaf.Service;

import com.mdeaf.Entities.AppRole;
import com.mdeaf.Entities.AppUser;

public interface AccountService {
    public AppUser saveUser(String username, String password, String confirmedPassword);
    public AppRole saveRole(AppRole r);
    public AppUser findUserByUsername(String username);
    public void addRoleToUser(String username,String role);
}
