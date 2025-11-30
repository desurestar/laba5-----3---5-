//package ru.zagrebin.laba11.repository;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import ru.zagrebin.laba11.entity.StudentGroup;
//
//import java.util.List;
//
//@Repository
//public interface StudentGroupRepository extends CrudRepository<StudentGroup, Long> {
//    List<StudentGroup> findByNameContainingIgnoreCase(String name);
//    List<StudentGroup> findBySpecialityContainingIgnoreCase(String speciality);
//
//    @Query("select g from StudentGroup g where g.university.name like %:universityName%")
//    List<StudentGroup> findByUniversityName(@Param("universityName") String universityName);
//
//}
