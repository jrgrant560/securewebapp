package com.securewebapp.auth;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// service that performs db interactions
@Service
public class MySQLUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository; //connection to the db

	@Autowired
	private PasswordEncoder passwordEncoder; //encrypts the password before saving it to the database

    //loads a user by username
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) { //throws an exception if the returned user is null
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities()); //???
	}

    //saves a new user to the db; encrypts the given password before saving to the database
	public UserDetails Save(User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		User savedUser = userRepository.save(newUser);
		return new org.springframework.security.core.userdetails.User(savedUser.getUsername(), savedUser.getPassword(), getAuthorities());
	}

    //retrieves the authorities for the user; by default, it selects 'ROLE_USER'
	private List<SimpleGrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authList;
	}

}