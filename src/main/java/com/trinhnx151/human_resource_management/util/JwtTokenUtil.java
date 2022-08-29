package com.trinhnx151.human_resource_management.util;

import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.security.TokenPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component

public class JwtTokenUtil {
    private String secret = "NXT151";



    public String generrateToken(Employee employee, long expiredDate) {
        Map<String, Object> claims = new HashMap<>();

        TokenPayload tokenPayload = TokenPayload.builder()
                .id(employee.getId())
                .email(employee.getEmail()).build();
        claims.put("payload",tokenPayload);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredDate * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

//    public static TokenPayload getTokenPayLoad(String token) {
//        return getClaimsFromToken(token,(Claims claim) ->{
//            Map<String, Object> mapResult = (Map<String, Object>) claim.get("payload");
//            return TokenPayload.builder()
//                    .email().build();
//        });
//    }

    private <T> T getClaimsFromToken(String token, Function<Claims,T> claimResolver){
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimResolver.apply(claims);
    }
}
