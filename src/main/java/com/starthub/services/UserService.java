package com.starthub.services;

import com.starthub.models.User;
import com.starthub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Harrison on 03/03/2018.
 */

@Service
public class UserService extends AbstractService<User, Long> implements UserDetailsService {

    private UserRepository repository;

    @Value("${security.encoding-strength}")
    private int ENCODING_STRENGTH;

    public UserService(@Autowired UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(ENCODING_STRENGTH);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repository.save(user);
        }catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
        return user;
    }
}
