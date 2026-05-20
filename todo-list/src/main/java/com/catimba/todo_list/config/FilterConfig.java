package com.catimba.todo_list.config;

import com.catimba.todo_list.filter.FilterTaskAuth;
import com.catimba.todo_list.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private IUserRepository iUserRepository;

    @Bean
    public FilterRegistrationBean<FilterTaskAuth> taskFilter(){
        FilterRegistrationBean<FilterTaskAuth> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new FilterTaskAuth(iUserRepository));

        //Rotas que serão interceptada por este filtro
        registrationBean.addUrlPatterns("/task/*");

        //Definindo a ordem de execução do filter
        registrationBean.setOrder(1);

        return registrationBean;
    }
}

