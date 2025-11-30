package ru.zagrebin.laba11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zagrebin.laba11.entity.Course;
import ru.zagrebin.laba11.repository.CourseRepositoryTemplate;

import java.util.List;

@Service
public class ServiceCourse {
    @Autowired
    private CourseRepositoryTemplate courseRepository;

    @Autowired
    private ServiceStudent serviceStudent;

    public List<Course> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            fillCourseAssociations(course);
        }
        return courses;
    }

    public Course findCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            fillCourseAssociations(course);
        }
        return course;
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    // В методах поиска НЕ заполняем связи
    public List<Course> findByName(String name) {
        return courseRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Course> findBySemester(String semester) {
        return courseRepository.findBySemesterContainingIgnoreCase(semester);
    }

    public List<Course> findByHoursGreaterThanEqual(String minHours) {
        return courseRepository.findByHoursGreaterThanEqual(minHours);
    }

    public List<Course> findByStudentId(Long studentId) {
        return courseRepository.findByStudentId(studentId);
    }

    private void fillCourseAssociations(Course course) {
        if (course.getId() != null) {
            course.setStudents(serviceStudent.findByCourseId(course.getId()));
        }
    }
}