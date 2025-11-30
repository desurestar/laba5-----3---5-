package ru.zagrebin.laba11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.zagrebin.laba11.entity.University;
import ru.zagrebin.laba11.repository.UniversityRepositoryTemplate;

import java.util.List;

@Service
public class ServiceUniversity {
    @Autowired
    private UniversityRepositoryTemplate universityRepository;

    @Autowired
    @Lazy // Добавляем @Lazy для разрыва цикла
    private ServiceStudentGroup serviceStudentGroup;

    public List<University> findAllUniversity() {
        List<University> universities = universityRepository.findAll();
        for (University university : universities) {
            fillUniversityAssociations(university);
        }
        return universities;
    }

    public University findUniversityById(Long id) {
        University university = universityRepository.findById(id).orElse(null);
        if (university != null) {
            fillUniversityAssociations(university);
        }
        return university;
    }

    public void saveUniversity(University university) {
        universityRepository.save(university);
    }

    public void deleteUniversityById(Long id) {
        universityRepository.deleteById(id);
    }

    // В методах поиска НЕ заполняем связи
    public List<University> findByName(String name) {
        return universityRepository.findByNameContainingIgnoreCase(name);
    }

    private void fillUniversityAssociations(University university) {
        if (university.getId() != null) {
            university.setStudentGroups(serviceStudentGroup.findByUniversityId(university.getId()));
        }
    }
}