package com.Bertazz1.demo_park_api.web.controller;


import com.Bertazz1.demo_park_api.jwt.JwtToken;
import com.Bertazz1.demo_park_api.jwt.JwtUserDetailsService;
import com.Bertazz1.demo_park_api.web.dto.UserLoginDto;
import com.Bertazz1.demo_park_api.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletRequest request){
        log.info("Authenticating user: {}", userLoginDto.getUsername());
      try {
          UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken jwtToken = jwtUserDetailsService.getTokenAuthenticated(userLoginDto.getUsername());
            return ResponseEntity.ok(jwtToken);

      }catch (AuthenticationException ex){
            log.warn("Bad Credentials for user: '{}'", userLoginDto.getUsername());

        }
      return ResponseEntity
              .badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                      "Invalid username or password"));
      }

    }


