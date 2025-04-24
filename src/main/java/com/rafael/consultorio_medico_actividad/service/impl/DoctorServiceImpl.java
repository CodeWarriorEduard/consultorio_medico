package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.DoctorUpdateDTORequest;
import com.rafael.consultorio_medico_actividad.dto.request.DoctorUserRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.DoctorDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Doctor;
import com.rafael.consultorio_medico_actividad.entity.Roles;
import com.rafael.consultorio_medico_actividad.entity.User;
import com.rafael.consultorio_medico_actividad.enumeration.RolesEnum;
import com.rafael.consultorio_medico_actividad.exception.UserAlreadyRegistered;
import com.rafael.consultorio_medico_actividad.exception.notFound.DoctorNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.RoleNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.DoctorMapper;
import com.rafael.consultorio_medico_actividad.repository.DoctorRepository;
import com.rafael.consultorio_medico_actividad.repository.RolesRepository;
import com.rafael.consultorio_medico_actividad.repository.UserRepository;
import com.rafael.consultorio_medico_actividad.service.DoctorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper, UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<DoctorDTOResponse> findAllDoctors() {

        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDoctorDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTOResponse getOneDoctor(Long id) {
        Doctor response = doctorRepository.findById(id)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor with id: "+ id + "not found"));

        return doctorMapper.toDoctorDtoResponse(response);
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor response = doctorRepository.findById(id)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor with id: "+ id + "not found"));

        // If exists delete
        doctorRepository.deleteById(response.getDoctor_id());
    }

    @Override
    public DoctorDTOResponse registerADoctor(DoctorUserRegisterDTORequest doctor) {

        // First we create an user with the data provided by the user.
        if(userRepository.existsByEmail(doctor.email())){
            throw new UserAlreadyRegistered("User already exits");
        }
        User user = new User();
        user.setEmail(doctor.email());
        user.setPassword(passwordEncoder.encode(doctor.password())); // Refactor duplicated code

        Roles roles = rolesRepository.findByRole(RolesEnum.DOCTOR)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        user.setRole(roles);
        userRepository.save(user);
        // Create a doctor

        Doctor doctor1 = new Doctor();
        System.out.println(doctor.full_name());
        doctor1.setFull_name(doctor.full_name());
        doctor1.setAvaliable_from(doctor.avaliable_from());
        doctor1.setAvaliable_to(doctor.avaliable_to());
        doctor1.setSpecialty(doctor.specialty());
        doctor1.setUser(user);

        return doctorMapper.toDoctorDtoResponse(doctorRepository.save(doctor1));
    }

    @Override
    public DoctorDTOResponse updateDoctor(Long id, DoctorUpdateDTORequest update_data) {
        Doctor response = doctorRepository.findById(id)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor with id: "+ id + " not found"));

        response.setSpecialty(update_data.specialty());
        response.setAvaliable_from(update_data.avaliable_from());
        response.setAvaliable_to(update_data.avaliable_to());
        return doctorMapper.toDoctorDtoResponse(doctorRepository.save(response));
    }
}
