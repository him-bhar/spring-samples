/*
 * Copyright 2013 Himanshu Bhardwaj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/
package gmc.gestaxi.controller;

import java.util.Collection;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.himanshu.r2r.common.email.EmailSenderUtility;
import com.himanshu.r2r.data.security.dao.ActivateUserDao;
import com.himanshu.r2r.data.security.dao.RoleDao;
import com.himanshu.r2r.data.security.dao.UserDao;
import com.himanshu.r2r.data.security.model.Role;
import com.himanshu.r2r.data.security.model.User;
import com.himanshu.r2r.data.security.model.UserApi;
import com.himanshu.r2r.security.api.UserService;
import com.himanshu.r2r.security.api.roles.Roles;

@Named("userDetailsService")
public class UserServiceImpl implements UserService {

	private Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource(name="userDao")
	private UserDao userDao;

	@Resource(name="roleDao")
	private RoleDao roleDao;

	@Resource(name="activateUserDao")
	private ActivateUserDao activateUserDao;

	@Resource(name="encoder")
	private PasswordEncoder encoder;

	@Inject
	@Named("emailSender")
	private EmailSenderUtility emailUtility;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.debug("Called loadUserByUsername for : {}", username);
		
		User user = new User();
		user.setUsername(username);
		Role r = new Role();
		r.setRoleName(username);
		user.addRoleMapping(r);
		return userDao.findUserByUsername(username);
	}

	@Override
	public User loadUserByUserid(long userid) throws UsernameNotFoundException {
		LOG.debug("Called loadUserByUserid for : {}", userid);
		return userDao.findUserByUserid(userid);
	}

	@Override
	public User loadUserByEmail(String email) throws UsernameNotFoundException {
		LOG.debug("Called loadUserByEmail for : {}", email);
		return userDao.findUserByEmail(email);
	}

	@Override
	public String getLoggedInUsername () {
		Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
		if (userAuth != null) {
			return userAuth.getName();
		} else {
			return "anonymous";
		}
	}

	@Override
	public UserApi getLoggedInUser () {
		Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
		if (userAuth != null) {
			return (UserApi)userAuth.getPrincipal();
		} else {
			return null;
		}
	}

	@Override
	public boolean isUserAuthorizedWithRole (String roleToTest) {
		boolean isAuthorized = false;
		Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
		if (userAuth != null) {
			Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) userAuth.getAuthorities();
			for(GrantedAuthority role : roles) {
				if (role.getAuthority().equalsIgnoreCase(roleToTest)) {
					isAuthorized = true;
				}
			}
		}
		return isAuthorized;
	}

	@Override
	public boolean isUserAdmin () {
		return isUserAuthorizedWithRole(Roles.ROLE_ADMIN.getRole());
	}

	@Override
	public User createUser(User user, String password, String activationByEmailContextUri) throws Exception {
		user = registerUser(user, password, activationByEmailContextUri);
		String activationUrl = activationByEmailContextUri.concat("/").concat(user.getUsername().concat("/eouihe3tuh34.onocewoweuuo.raadf67npgigpreigpn==").concat("/").concat(user.getActivateUser().getActivationCode()));
		emailUtility.sendEmail("New User created", "Click here to activate your account:".concat(activationUrl), "himanshubhardwajin@gmail.com");
		return user;
	}
	
	@Override
	@Transactional
	public User updateUser(User user) throws Exception {
		user = updateUserDetails(user);
		//String activationUrl = activationByEmailContextUri.concat("/").concat(user.getUsername().concat("/eouihe3tuh34.onocewoweuuo.raadf67npgigpreigpn==").concat("/").concat(user.getActivateUser().getActivationCode()));
		//emailUtility.sendEmail("New User created", "Click here to activate your account:".concat(activationUrl), "himanshubhardwajin@gmail.com");
		return user;
	}

	//If using @Transactional, then has to be on the exposed service methods, as they will be actually be maintaining transactions with proxied object (here service).
	private User updateUserDetails(User user) throws Exception {
		try {
			return userDao.update(user);
		} catch (Exception e) {
			LOG.error("Error updating user {}", user.getUsername());
			throw e;
		}
	}

	@Transactional
	private User registerUser(User user, String password, String activationByEmailContextUri) throws Exception {
		user.setAccountNonExpired(Boolean.TRUE);
		user.setCredentialsNonExpired(Boolean.TRUE);
		user.setEnabled(Boolean.FALSE);
		user.setAccountNonLocked(Boolean.FALSE);
		String encryptedPassword = encoder.encode(password);
		user.setPassword(encryptedPassword);
		user.addRoleMapping(roleDao.findRoleByRoleName(Roles.ROLE_USER.getRole()));
		user.setActivationCode(UUID.randomUUID().toString());
		userDao.save(user);
		return user;
	}

	@Transactional
	@Override
	public User activateUserByActivationCode(String username, String activationCode) throws Exception {
		try {
			//String username = EncryptionDecryptionUtil.decrypt(encUsername);
			User user = userDao.findUserByUsername(username);
			if (user.getActivateUser() != null && user.getActivateUser().getActivationCode().equalsIgnoreCase(activationCode)) {
				user.setEnabled(Boolean.TRUE);
				user.setAccountNonLocked(Boolean.TRUE);
				user.getActivateUser().setExpired(true);
				activateUserDao.delete(user.getActivateUser());
				user.setActivateUser(null);
			}
			userDao.update(user);
			return user;
		} catch (Exception e) {
			//LOG.error("Error activating user {}", username.concat(", ").concat(activationCode), e);
			LOG.error("Error activating user {}", username.concat(", ").concat(activationCode));
			throw e;
		}
	}
	
}
