package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Model.Users;
import com.example.demo.dtoUser.LoginReq;
import com.example.demo.dtoUser.RegisterRequest;
import com.example.demo.enums.Roles;
import com.example.demo.repo.SupRepo;
import com.example.demo.dtoUser.LoginRes;
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

    @Autowired
    OtpService otpService;

    @Transactional
    public Users createUser(RegisterRequest request){
        System.out.println(request);
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encryptPass(request.getPassword()));
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

    public LoginRes verify(LoginReq req){
        System.out.println(req);
        LoginRes resp=new LoginRes();
        String username=req.getIdentifier();
        if(req.getIdentifier().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$")) {
            Users user = userRepo.findByEmail(username);
            if (user == null){
//                resp.setSuccess(false);
                resp.setMessage("Email is not registered yet!!");
                return resp;
            }
            resp.setSupervisor(user.getSupervisor());
            username = user.getUsername();
        }
        else resp.setSupervisor(userRepo.findByUsername(username).getSupervisor());
        Authentication authentication=
                authManager.authenticate(new UsernamePasswordAuthenticationToken(
                        username,req.getPassword()));//we need to confirm that whether it works even the sup enters with gmail or not
//        System.out.println("Hello"+authentication.isAuthenticated());
        System.out.println(authentication.isAuthenticated());
        if(authentication.isAuthenticated()){
            resp.setJwt(jwtService.generateToken(userRepo.findByUsername(username)));
            resp.setSuccess(true);
            resp.setMessage("Successfully login!");
        }
        else resp.setMessage("fail");//here we don't want to return;
        return resp;
    }

    public String encryptPass(String pass){
        return encoder.encode(pass);
    }
}
