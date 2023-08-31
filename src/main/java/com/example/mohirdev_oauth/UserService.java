package com.example.mohirdev_oauth;

import com.example.mohirdev_oauth.domain.User;
import com.example.mohirdev_oauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User getUserFromAuthentication(AbstractAuthenticationToken authToken){
        Map<String,Object> atributes;

        if (authToken instanceof OAuth2AuthenticationToken){
            atributes = ((OAuth2AuthenticationToken) authToken).getPrincipal().getAttributes();

        }else {
            throw new IllegalArgumentException("error");
        }

        User user = getUser(atributes);
        if (user !=null){
            userRepository.save(user);
        }
        return user;
    }
    private User getUser(Map<String,Object> atributes){
        User user = new User();

        if (atributes.get("uid")!= null){
            user.setUid((String) atributes.get("uid"));
        } if (atributes.get("given_name")!= null){
            user.setFirstName((String) atributes.get("given_name"));
        } if (atributes.get("family_name")!= null){
            user.setLastName((String) atributes.get("family_name"));
        } if (atributes.get("locale")!= null){
            user.setLangKey((String) atributes.get("locale"));
        } if (atributes.get("email_verified")!= null){
            user.setActivated((Boolean) atributes.get("email_verified"));
        } if (atributes.get("email")!= null){
            user.setEmail((String) atributes.get("email"));
        } if (atributes.get("picture")!= null){
            user.setImageUrl((String) atributes.get("picture"));
        }
        return user;
    }
}
