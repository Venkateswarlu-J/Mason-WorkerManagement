package com.example.demo.Service;

import com.example.demo.Model.Supervisor;
import com.example.demo.Model.Users;
import com.example.demo.dtoUser.LoginReq;
import com.example.demo.dtoUser.RegisterRequest;
import com.example.demo.enums.Roles;
import com.example.demo.repo.SupRepo;
import com.example.demo.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SupRepo supRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Transactional
    public Users register(RegisterRequest request){
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(Roles.SUPERVISOR);

        Supervisor supervisor=new Supervisor();
        supervisor.setSup_name(request.getUsername());
        supervisor.setPhone(request.getPhone());

        supervisor.setUser(user);
        user.setSupervisor(supervisor);

//        supRepo.save(supervisor); cascade will take this
        return userRepo.save(user);
    }

    public String verify(LoginReq req){
        Authentication authentication=
                authManager.authenticate(new UsernamePasswordAuthenticationToken(
                        req.getUsername(),req.getPassword()));//we need to confirm that whether it works even the sup enters with gmail or not
//        System.out.println("Hello"+authentication.isAuthenticated());
        return authentication.isAuthenticated()?jwtService.generateToken(userRepo.findByUsername(req.getUsername())):"fail"; //here we don't want to return
    }
}
