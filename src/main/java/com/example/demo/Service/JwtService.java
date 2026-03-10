package com.example.demo.Service;


import com.example.demo.Model.Users;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    String secretKey=null;

    public JwtService() throws Exception{
        KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
        javax.crypto.SecretKey key=keyGenerator.generateKey();
        secretKey= Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public String generateToken(Users user) {
        Map<String,Object> claims=new HashMap<>();
        System.out.println("UserId from user object: " + user.getUserId());
        System.out.println("Role from user object: " + user.getRole());
        claims.put("UserId",user.getUserId());
        claims.put("Role",user.getRole());
        System.out.println(claims.get("Role")+" "+claims.get("UserId"));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserId()+"")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+(1000*60*30*30)))
                .signWith(getKey())
                .compact();

    }
    public Key getKey(){
        byte[] keysBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keysBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
