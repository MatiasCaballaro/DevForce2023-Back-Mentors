package com.devForce.learning.controller;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.dto.request.LoginRequest;
import com.devForce.learning.model.dto.request.RegistroDTO;
import com.devForce.learning.model.dto.response.MessageResponse;
import com.devForce.learning.model.dto.response.UserInfoResponse;
import com.devForce.learning.model.entity.ERole;
import com.devForce.learning.model.entity.Role;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.RoleRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.security.jwt.JwtUtils;
import com.devForce.learning.security.services.UserDetailsImpl;
import com.devForce.learning.service.AdminService;
import com.devForce.learning.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UsuarioRepository usuarioRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  AdminService adminService;

  @Autowired
  UsuarioService usuarioService;


  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                   userDetails.getUsername(),
                                   userDetails.getEmail(),
                                   roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<RespuestaDTO> registerUser(@Valid @RequestBody RegistroDTO registroDTO) {
    return adminService.crearUsuario(registroDTO);
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("Usuario deslogueado"));
  }
}
