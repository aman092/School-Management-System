package com.aman.school.service;

import com.aman.school.dto.StudentDTO;
import com.aman.school.entity.Student;
import com.aman.school.exception.ResourceNotFoundException;
import com.aman.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentDTO create(StudentDTO dto) {
        Student s = Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .dateOfBirth(dto.getDateOfBirth())
                .rollNumber(dto.getRollNumber())
                .build();
        Student saved = studentRepository.save(s);
        dto.setId(saved.getId());
        return dto;
    }

    public StudentDTO getById(Long id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return StudentDTO.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .dateOfBirth(s.getDateOfBirth())
                .rollNumber(s.getRollNumber())
                .build();
    }

    public List<StudentDTO> listAll() {
        return studentRepository.findAll().stream().map(s -> StudentDTO.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .dateOfBirth(s.getDateOfBirth())
                .rollNumber(s.getRollNumber())
                .build()).collect(Collectors.toList());
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
