package com.Bertazz1.demo_park_api.web.controller;


import com.Bertazz1.demo_park_api.jwt.JwtToken;
import com.Bertazz1.demo_park_api.jwt.JwtUserDetailsService;
import com.Bertazz1.demo_park_api.web.dto.UserLoginDto;
import com.Bertazz1.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Authentication", description = "Operations related to user authentication")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Authenticate", description = " Authenticates a user and returns a JWT token",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "User authenticated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid username or password",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
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


