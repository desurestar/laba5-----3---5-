package ru.zagrebin.laba11.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ru.zagrebin.laba11.entity.Student;
import ru.zagrebin.laba11.repository.StudentRepositoryTemplate;

@Service
public class ServiceStudent {
    @Autowired
    private StudentRepositoryTemplate studentRepository;

    @Autowired
    private ServiceAddress serviceAddress;

    @Autowired
    @Lazy // Добавляем @Lazy для разрыва цикла
    private ServiceStudentGroup serviceStudentGroup;

    @Autowired
    @Lazy // Добавляем @Lazy для разрыва цикла
    private ServiceCourse serviceCourse;

    // остальные методы без изменений...
    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            fillStudentAssociations(student);
        }
        return students;
    }

    public Optional<Student> getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            fillStudentAssociations(student.get());
        }
        return student;
    }

    public void saveStudent(Student student) {
        if (student.getAddress() != null) {
            serviceAddress.SaveAddress(student.getAddress());
        }
        studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }

    // В методах поиска НЕ заполняем связи (чтобы избежать циклов)
    public List<Student> findByLastName(String lastName) {
        return studentRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public List<Student> findByGpaGreaterThanEqual(Double gpa) {
        return studentRepository.findByGpaGreaterThanEqual(gpa);
    }

    public List<Student> findByCity(String city) {
        return studentRepository.findByCity(city);
    }

    public List<Student> findByGroupName(String groupName) {
        return studentRepository.findByGroupName(groupName);
    }

    public List<Student> findBySpeciality(String speciality) {
        return studentRepository.findBySpeciality(speciality);
    }

    public List<Student> findByLastNameAndGpa(String lastName, Double gpa) {
        return studentRepository.findByLastNameAndGpa(lastName, gpa);
    }

    public List<Student> findByCityAndSpeciality(String city, String speciality) {
        return studentRepository.findByCityAndSpeciality(city, speciality);
    }

    public List<Student> findByFirstNameAndGroupName(String firstName, String groupName) {
        return studentRepository.findByLastNameAndGroupName(firstName, groupName);
    }

    public List<Student> searchCombined(String lastName, String city, Double minGpa) {
        String ln = normalize(lastName);
        String ct = normalize(city);
        return studentRepository.searchCombined(ln, ct, minGpa);
    }

    public List<Student> findByAddressId(Long addressId) {
        return studentRepository.findByAddressId(addressId);
    }

    public List<Student> findByCourseId(Long courseId) {
        return studentRepository.findByCourseId(courseId);
    }

    public List<Student> findByGroupId(Long groupId) {
        return studentRepository.findByGroupId(groupId);
    }

    private void fillStudentAssociations(Student student) {
        if (student.getAddress() != null && student.getAddress().getId() != null) {
            student.setAddress(serviceAddress.findAddressById(student.getAddress().getId()));
        }

        if (student.getStudentGroup() != null && student.getStudentGroup().getId() != null) {
            student.setStudentGroup(serviceStudentGroup.findStudentGroupById(student.getStudentGroup().getId()));
        }

        if (student.getId() != null) {
            student.setCourses(serviceCourse.findByStudentId(student.getId()));
        }
    }

    private String normalize(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}