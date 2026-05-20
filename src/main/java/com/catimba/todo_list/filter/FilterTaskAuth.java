package com.catimba.todo_list.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.catimba.todo_list.user.IUserRepository;
import com.catimba.todo_list.user.UserModel;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository iUserRepository;

    public FilterTaskAuth(IUserRepository iUserRepository){
        this.iUserRepository = iUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String authorization = request.getHeader("Authorization");

        String auth_encoded = authorization.substring("Basic ".length());

        byte[] auth_decoded = Base64.getDecoder().decode(auth_encoded);

        String[] credencial_user = new String(auth_decoded).split(":");

        String user_name = credencial_user[0];
        String password = credencial_user[1];

        UserModel user = iUserRepository.findByUsername(user_name);

        if (user == null){
            response.sendError(401);
            return;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (!result.verified){
            response.sendError(401);
            return;
        }

        request.setAttribute("user_id", user.getId());

        chain.doFilter(request,response);
    }
}
