package com.example.role_based.auth.service;

import com.example.role_based.auth.entity.MyUserDetails;
import com.example.role_based.auth.entity.User;
import com.example.role_based.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);

         User u = user.get();
         if(u==null)
             throw  new UsernameNotFoundException("User not found");
        return new MyUserDetails(u);
    }
}
