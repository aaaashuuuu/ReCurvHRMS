package com.hrms.service.user_service;

import com.hrms.dto.auth_dto.UserDetails;
import com.hrms.entity.User;
import com.hrms.repository.user_repository.UserRepository;
import com.hrms.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {



    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private PasswordUtil passwordUtil;

//    public UserService(UserRepository userRepository, PasswordUtil passwordUtil) {
//        this.userRepository = userRepository;
//        this.passwordUtil = passwordUtil;
//    }

   public UserService(){
 }



    @Override
    public Optional<User> findByEmail(String email) {
        logger.debug("🔍 Looking up user by email: {}", email);
        Optional<User> user = userRepository.findByEmailWithRole(email);
        logger.debug("User found: {}", user.isPresent());
        return user;
    }

    @Override
    public Optional<UserDetails> findUserDetailsByEmail(String email) {
        logger.debug("🔍 Looking up user details by email: {}", email);
        Optional<User> userOpt = userRepository.findByEmailWithRole(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDetails details = new UserDetails(
                    user.getUserId(),
                    user.getEmail(),
                    user.getRole().getRoleName(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getStatus().name()
            );
            logger.debug("User details found: {} {}", details.getFirstName(), details.getLastName());
            return Optional.of(details);
        }
        logger.debug("User details NOT found for email: {}", email);
        return Optional.empty();
    }

    @Override
    public boolean validateUserCredentials(String email, String password) {
        logger.debug("🔐 Validating credentials for: {}", email);
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String storedHash = user.getHashPassword();
            logger.debug("Stored hash: {}", storedHash);
            logger.debug("Stored hash length: {}", storedHash != null ? storedHash.length() : "NULL");

            boolean matches = passwordUtil.matches(password, storedHash);
            logger.debug("Password matches: {}", matches);

            return matches;
        }

        logger.debug("❌ User not found for credential validation");
        return false;
    }

    @Override
    public void updateUserLoginInfo(User user) {
        logger.debug("Updating login info for user: {}", user.getEmail());
        userRepository.save(user);
    }


    public void testauth2(Authentication authentication){

        System.out.println("================Printed from user service =======================");

        System.out.println(authentication.getPrincipal());
        String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");



        System.out.println(role);
        System.out.println("=============================================================");
    }


}