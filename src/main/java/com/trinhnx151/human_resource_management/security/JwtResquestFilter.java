package com.trinhnx151.human_resource_management.security;

import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.repository.EmployeeRepo;
import com.trinhnx151.human_resource_management.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtResquestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final EmployeeRepo employeeRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String token = null;
        TokenPayload tokenPayload = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Token ")) {
            token = requestTokenHeader.substring(6).trim();
            try {
                tokenPayload = jwtTokenUtil.getTokenPayLoad(token);
            } catch (SignatureException e) {
                System.out.println("Invalid JWT signature");
            } catch (IllegalArgumentException ex) {
                System.out.println("Unable to get JWT");
            } catch (ExpiredJwtException e) {
                System.out.println("Token has expired");
            }

        } else {
            System.out.println("jwt dont not start with 'Token' ");
        }
        if (tokenPayload != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Employee> employee = employeeRepo.findById(tokenPayload.getId());
            if (employee.isPresent()) {
                if (jwtTokenUtil.validate(token, employee)) {
                    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
                    UserDetails userDetails = new org.springframework.security.core.userdetails.User(employee.get().getEmail(), employee.get().getPassword(), new ArrayList<>());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
