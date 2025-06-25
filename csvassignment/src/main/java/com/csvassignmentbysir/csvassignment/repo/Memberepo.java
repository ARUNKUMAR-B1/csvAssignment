package com.csvassignmentbysir.csvassignment.repo;

import com.csvassignmentbysir.csvassignment.EntityDto;
import com.csvassignmentbysir.csvassignment.entity.EmbeddedMemberClass;
import com.csvassignmentbysir.csvassignment.entity.MemberClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Memberepo extends JpaRepository<EmbeddedMemberClass,MemberClass> {

    @Query("SELECT new com.csvassignmentbysir.csvassignment.EntityDto(m.siNo, m.id.fname, m.id.lname, m.id.dob, m.id.gender, " +
            "m.address1, m.address2, m.city, m.company, m.education, m.house, m.mobile, m.monthlysalary, m.pincode) " +
            "FROM EmbeddedMemberClass m WHERE m.id.dob BETWEEN :startDate AND :endDate")
    List<EntityDto> getDataBetweenTheYears(@Param("startDate") String startDate,
                                           @Param("endDate") String endDate);

//
//    @Query("SELECT new com.csvassignmentbysir.csvassignment.EntityDto(" +
//            "CAST(m.id.dob AS string), m.id.firstName, m.id.gender, m.id.lastName, " +
//            "m.address1, m.address2, m.city, m.company, m.education, m.house, m.mobile, m.monthlysalary, m.pincode, m.siNo) " +
//            "FROM MemberClass m WHERE CAST(m.monthlysalary AS integer) >= 20000")
//    List<EntityDto> fetchBySalaryGreaterThan20k();


}
