package com.appstore.services;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appstore.api.dao.UserDAO;
import com.appstore.entity.User;

@Service("userDetailsService")
public class SecurityCoreUserDetailsServiceImpl implements UserDetailsService  {
	@Autowired
	private UserDAO userDao;
	
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		User user = userDao.getUser(userName);
		return makeUser(user);
	}
	
    private org.springframework.security.core.userdetails.User makeUser(User user) {
    	Collection authorities = Arrays.asList(makeGrantedAuthorities(user));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
        
    }

    private GrantedAuthority[] makeGrantedAuthorities(User user) {
        GrantedAuthority[] result = new GrantedAuthority[1];
        result[0] = new SimpleGrantedAuthority(user.getUserrole());
        return result;
    }
}
