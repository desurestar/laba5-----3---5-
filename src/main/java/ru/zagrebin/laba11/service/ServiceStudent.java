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

    // В методах поиска заполняем только минимально необходимые связи для отображения
    public List<Student> findByLastName(String lastName) {
        List<Student> students = studentRepository.findByLastNameContainingIgnoreCase(lastName);
        fillSearchResultAssociations(students);
        return students;
    }

    public List<Student> findByGpaGreaterThanEqual(Double gpa) {
        List<Student> students = studentRepository.findByGpaGreaterThanEqual(gpa);
        fillSearchResultAssociations(students);
        return students;
    }

    public List<Student> findByCity(String city) {
        List<Student> students = studentRepository.findByCity(city);
        fillSearchResultAssociations(students);
        return students;
    }

    public List<Student> findByGroupName(String groupName) {
        List<Student> students = studentRepository.findByGroupName(groupName);
        fillSearchResultAssociations(students);
        return students;
    }

    public List<Student> findBySpeciality(String speciality) {
        List<Student> students = studentRepository.findBySpeciality(speciality);
        fillSearchResultAssociations(students);
        return students;
    }

    public List<Student> findByLastNameAndGpa(String lastName, Double gpa) {
        List<Student> students = studentRepository.findByLastNameAndGpa(lastName, gpa);
        fillSearchResultAssociations(students);
        return students;
    }

    public List<Student> findByCityAndSpeciality(String city, String speciality) {
        List<Student> students = studentRepository.findByCityAndSpeciality(city, speciality);
        fillSearchResultAssociations(students);
        return students;
    }

    public List<Student> findByFirstNameAndGroupName(String firstName, String groupName) {
        List<Student> students = studentRepository.findByLastNameAndGroupName(firstName, groupName);
        fillSearchResultAssociations(students);
        return students;
    }

    public List<Student> searchCombined(String lastName, String city, Double minGpa) {
        String ln = normalize(lastName);
        String ct = normalize(city);
        List<Student> students = studentRepository.searchCombined(ln, ct, minGpa);
        fillSearchResultAssociations(students);
        return students;
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

    // Fill only address and group for search results (no deep associations to avoid cycles)
    private void fillSearchResultAssociations(List<Student> students) {
        for (Student student : students) {
            if (student.getAddress() != null && student.getAddress().getId() != null) {
                student.setAddress(serviceAddress.findAddressById(student.getAddress().getId()));
            }
            if (student.getStudentGroup() != null && student.getStudentGroup().getId() != null) {
                student.setStudentGroup(serviceStudentGroup.findStudentGroupById(student.getStudentGroup().getId()));
            }
        }
    }

    private String normalize(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}