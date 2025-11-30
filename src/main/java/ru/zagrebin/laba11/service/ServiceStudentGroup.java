package ru.zagrebin.laba11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.zagrebin.laba11.entity.StudentGroup;
import ru.zagrebin.laba11.repository.StudentGroupRepositoryTemplate;

import java.util.List;

@Service
public class ServiceStudentGroup {
    @Autowired
    private StudentGroupRepositoryTemplate studentGroupRepository;

    @Autowired
    @Lazy // Добавляем @Lazy для разрыва цикла
    private ServiceUniversity serviceUniversity;

    @Autowired
    @Lazy // Добавляем @Lazy для разрыва цикла
    private ServiceStudent serviceStudent;

    public List<StudentGroup> findAllStudentGroup() {
        List<StudentGroup> groups = studentGroupRepository.findAll();
        for (StudentGroup group : groups) {
            fillGroupAssociations(group);
        }
        return groups;
    }

    public StudentGroup findStudentGroupById(Long id) {
        StudentGroup group = studentGroupRepository.findById(id).orElse(null);
        if (group != null) {
            fillGroupAssociations(group);
        }
        return group;
    }

    public void saveStudentGroup(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }

    public void deleteStudentGroup(Long id) {
        studentGroupRepository.deleteById(id);
    }

    // В методах поиска НЕ заполняем связи
    public List<StudentGroup> findByName(String name) {
        return studentGroupRepository.findByNameContainingIgnoreCase(name);
    }

    public List<StudentGroup> findBySpeciality(String speciality) {
        return studentGroupRepository.findBySpecialityContainingIgnoreCase(speciality);
    }

    public List<StudentGroup> findByUniversityName(String universityName) {
        return studentGroupRepository.findByUniversityName(universityName);
    }

    public List<StudentGroup> findByUniversityId(Long universityId) {
        return studentGroupRepository.findByUniversityId(universityId);
    }

    private void fillGroupAssociations(StudentGroup group) {
        if (group.getUniversity() != null && group.getUniversity().getId() != null) {
            group.setUniversity(serviceUniversity.findUniversityById(group.getUniversity().getId()));
        }

        if (group.getId() != null) {
            group.setStudents(serviceStudent.findByGroupId(group.getId()));
        }
    }
}