package com.example.kursovaya_rabota.appUser;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

    private final String USER_NOT_FIND_MSG = "user with id %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FIND_MSG, email)));
    }

    public void createUserWithRoles(AppUser appUser, AppUserRole role){
        signUpUser(appUser);
        appUser.setAppUserRole(role);
        appUser.setEnabled(true);
        appUserRepository.save(appUser);

    }

    public boolean signUpUser(AppUser appUser){

        boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExist) {
            return false;
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        //TODO: change this
        appUser.setEnabled(true);
        appUserRepository.save(appUser);

//
//        String token = UUID.randomUUID().toString();
//        ConfirmationToken confirmationToken = new ConfirmationToken(
//                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),appUser
//        );
//        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO: Send Email

//        return token;
        return true;
    }

//    public int enableAppUser(String email) {
//        return appUserRepository.enableAppUser(email);
//    }

    public AppUser getUserByPrincipal(Principal principal){
        if(principal == null){
            return new AppUser();
        }
        return appUserRepository.findByEmail(principal.getName()).orElse(null);
    }

    public boolean isPrincipalHasRole(AppUser user , AppUserRole role){
        if (user == null) {
            return false;
        } else{
            return user.getAppUserRole() == role;
        }
    }

    public Map<String, Boolean> getAllRoles(Principal principal){
        Map<String, Boolean> map = new HashMap<>();
        AppUser user = getUserByPrincipal(principal);
        map.put("isAdmin", isPrincipalHasRole(user, AppUserRole.ROLE_ADMIN));
        map.put("isCook", isPrincipalHasRole(user, AppUserRole.ROLE_COOK));
        map.put("isWaiter", isPrincipalHasRole(user, AppUserRole.ROLE_WAITER));
        map.put("isAnalyst", isPrincipalHasRole(user, AppUserRole.ROLE_ANALYST));
        map.put("isUser", isPrincipalHasRole(user, AppUserRole.ROLE_USER));
        if (map.containsValue(true)){
            map.put("isNoRole", false);
        }   else {
            map.put("isNoRole", true);
        }
        return map;
    }

}


