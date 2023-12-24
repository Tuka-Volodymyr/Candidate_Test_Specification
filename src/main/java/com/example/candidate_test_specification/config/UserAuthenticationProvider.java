package com.example.candidate_test_specification.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.candidate_test_specification.domain.User;
import com.example.candidate_test_specification.domain.exceptions.UserNotFoundException;
import com.example.candidate_test_specification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {
    private final UserRepository userRepository;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;



    public String createToken(String login) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + 360000000);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(login)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }
    @Transactional
    public Authentication validateToken(String token) throws JSONException {
        String[] tokenParts = token.split("\\.");
        String payload = tokenParts[1];
        byte[] decodedPayload = Base64.getDecoder().decode(payload);
        String decodedPayloadString = new String(decodedPayload, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(decodedPayloadString);
        String email = jsonObject.getString("sub");
        User user = userRepository
                .findByUsername(email)
                .orElseThrow(UserNotFoundException::new);
//        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName());

        return new UsernamePasswordAuthenticationToken(user, null,null);
    }

}