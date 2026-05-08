package com.example.demo.Service;

import com.example.demo.Model.Supervisor;
import com.example.demo.Model.UserPrincipal;
import com.example.demo.Model.Users;
import com.example.demo.repo.SupRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=userRepo.findByUsername(username);   //it is requires for at login after login so username is best to keep
//        Users user=userRepo.findByEmail(username);  //THe coming one is email
        if(user==null){
            System.out.println("Not found");
            throw new UsernameNotFoundException("Not found");
        }
        System.out.println("Successfully fetched the user"+user);
        return new UserPrincipal(user);
    }
}
