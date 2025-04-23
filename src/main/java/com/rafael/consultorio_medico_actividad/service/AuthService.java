package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.UserLoginDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.UserLoginDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Roles;
import com.rafael.consultorio_medico_actividad.entity.User;
import com.rafael.consultorio_medico_actividad.enumeration.RolesEnum;
import com.rafael.consultorio_medico_actividad.mapper.UserMapper;
import com.rafael.consultorio_medico_actividad.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository, UserMapper userMapper, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }
    // Method for automatically create an admin, just a test.
    public void createAdmin(){
        User user = new User();
        Roles roles = new Roles(); // Create a constructor for this
        roles.setRole(RolesEnum.ADMIN);
        user.setRole(roles);
        user.setFull_name("Rafael Ortiz");
        user.setEmail("admin@admin.com");
        user.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(user);
    }

    public UserLoginDTOResponse login(UserLoginDTORequest input){

        System.out.println("entró");
        System.out.println(userRepository.findByEmail(input.email()));
        User user = userRepository.findByEmail(input.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        System.out.println("Hola");
        System.out.println(user);

        if(!passwordEncoder.matches(input.password(), user.getPassword())){
            throw new UsernameNotFoundException("User or password incorrect");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        // Test

        String token = jwtService.getToken(user);

        return userMapper.toUserLoginDtoResponse(user, token);
    }

}