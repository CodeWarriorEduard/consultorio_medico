package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.UserLoginDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.TokenDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.User;
import com.rafael.consultorio_medico_actividad.mapper.TokenMapper;
import com.rafael.consultorio_medico_actividad.mapper.UserMapper;
import com.rafael.consultorio_medico_actividad.repository.RolesRepository;
import com.rafael.consultorio_medico_actividad.repository.UserRepository;
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
    private final RolesRepository rolesRepository;
    private final TokenMapper tokenMapper;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository, UserMapper userMapper, JwtService jwtService, RolesRepository rolesRepository, TokenMapper tokenMapper) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.rolesRepository = rolesRepository;
        this.tokenMapper = tokenMapper;
    }

    public TokenDTOResponse login(UserLoginDTORequest input){

        User user = userRepository.findByEmail(input.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));


        if(!passwordEncoder.matches(input.password(), user.getPassword())){
            throw new UsernameNotFoundException("User or password incorrect");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );


        return tokenMapper.toDTO(jwtService.getToken(user), user.getRole().getRole().name());
    }


}