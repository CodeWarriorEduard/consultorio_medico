package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.DoctorRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.request.DoctorUserRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.DoctorDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Doctor;
import com.rafael.consultorio_medico_actividad.entity.Roles;
import com.rafael.consultorio_medico_actividad.entity.User;
import com.rafael.consultorio_medico_actividad.enumeration.RolesEnum;
import com.rafael.consultorio_medico_actividad.exception.notFound.DoctorNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.DoctorMapper;
import com.rafael.consultorio_medico_actividad.repository.DoctorRepository;
import com.rafael.consultorio_medico_actividad.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
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
        User user = new User();
        user.setFull_name(doctor.full_name());
        user.setEmail(doctor.email());
        user.setPassword(doctor.password());

        Roles roles = new Roles();
        roles.setRole(RolesEnum.DOCTOR);

        user.setRole(roles);

        // Create a doctor

        Doctor doctor1 = new Doctor();
        doctor1.setAvaliable_from(doctor.avaliable_from());
        doctor1.setAvaliable_to(doctor.avaliable_to());
        doctor1.setSpecialty(doctor.specialty());
        doctor1.setUser(user);

        return doctorMapper.toDoctorDtoResponse(doctorRepository.save(doctor1));
    }

    @Override
    public DoctorDTOResponse updateDoctor(Long id, DoctorRegisterDTORequest update_data) {
        Doctor response = doctorRepository.findById(id)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor with id: "+ id + " not found"));

        response.setSpecialty(update_data.specialty());
        response.setAvaliable_from(update_data.avaliable_from());
        response.setAvaliable_to(update_data.avaliable_to());
        return doctorMapper.toDoctorDtoResponse(doctorRepository.save(response));
    }
}
