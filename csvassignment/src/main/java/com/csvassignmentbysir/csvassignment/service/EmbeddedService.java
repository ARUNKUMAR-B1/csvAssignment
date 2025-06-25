package com.csvassignmentbysir.csvassignment.service;

import com.csvassignmentbysir.csvassignment.EntityDto;
import com.csvassignmentbysir.csvassignment.repo.Memberepo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class EmbeddedService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    Memberepo memberepo;

    public String sortTheData(MultipartFile file) throws IOException, CsvValidationException {
        Set<String> uniqueCheck = new HashSet<>();
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        String[] line;
        int lineNumber = 0;
        int insertedCount = 0;

        List<Object[]> batchArgs = new ArrayList<>();
        int batchSize = 100;

        String sql = """
            INSERT IGNORE INTO members_table (
                `Records#`, `fname`, `lname`, `dob`, `gender`,
                `education`, `House#`, `address1`, `address2`, `city`,
                `pincode`, `Mobile#`, `company`, `monthly_salary`
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        while ((line = reader.readNext()) != null) {
            lineNumber++;

            if (line.length < 14) {
                System.out.println("Line " + lineNumber + ": less than 14 fields");
                continue;
            }

            String records = line[0].trim();
            String fName = line[1].trim();
            String lName = line[2].trim();
            String dob = line[3].trim();
            String gender = line[4].trim();
            String education = line[5].trim();
            String house = line[6].trim();
            String address1 = line[7].trim();
            String address2 = line[8].trim();
            String city = line[9].trim();
            String pincode = line[10].trim();
            String mobile = line[11].trim();
            String company = line[12].trim();
            String monthlySalary = line[13].trim();

            if (Stream.of(records, fName, lName, dob, gender, education, house, address1, address2, city, pincode, mobile, company, monthlySalary)
                    .anyMatch(String::isEmpty)) {
                System.out.println("Line " + lineNumber + ": one or more fields are empty");
                continue;
            }

            String uniqueKey = fName + "_" + lName + "_" + gender + "_" + dob;
            if (!uniqueCheck.add(uniqueKey)) {
                System.out.println("Line " + lineNumber + ": duplicate record");
                continue;
            }

            if (!mobile.matches("^[789]\\d{9}$")) {
                System.out.println("Line " + lineNumber + ": invalid mobile number");
                continue;
            }

            LocalDate birthDate;
            try {
                // First try format M/d/yyyy (like 3/7/1998)
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yyyy");
                birthDate = LocalDate.parse(dob, formatter1);
            } catch (Exception e1) {
                try {
                    // Then try format dd-MM-yyyy
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    birthDate = LocalDate.parse(dob, formatter2);
                } catch (Exception e2) {
                    System.out.println("Line " + lineNumber + ": DOB parse error for input '" + dob + "'");
                    continue;
                }
            }

            LocalDate today = LocalDate.now();
            int age = Period.between(birthDate, today).getYears();
            if (birthDate.isAfter(today) || age > 100) {
                System.out.println("Line " + lineNumber + ": invalid DOB");
                continue;
            }

            batchArgs.add(new Object[]{
                    records, fName, lName, dob, gender,
                    education, house, address1, address2, city,
                    pincode, mobile, company, monthlySalary
            });

            if (batchArgs.size() == batchSize) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
                insertedCount += batchArgs.size();
                batchArgs.clear();
                System.out.println("Inserted a batch of 100 records");
            }
        }

        if (!batchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(sql, batchArgs);
            insertedCount += batchArgs.size();
            System.out.println("Inserted last batch of " + batchArgs.size() + " records");
        }

        return insertedCount > 0
                ? "Inserted " + insertedCount + " record(s) successfully"
                : "No valid records found";
    }


    //get the from the database
    //like firstname and lastname startswith
    public List<EntityDto> getStartsWithFnameLname(String fname, String lname) {
        String sql = """
                    SELECT 
                        `dob`, `fname`, `gender`, `lname`, `address1`, `address2`, 
                        `city`, `company`, `education`, `House#`, `Mobile#`, 
                        `monthly_salary`, `pincode`, `Records#`
                    FROM 
                        members_table
                    WHERE 
                        `fname` LIKE ? AND `lname` LIKE ?
                """;

        return jdbcTemplate.query(sql, new Object[]{fname + "%", lname + "%"}, (rs, rowNum) -> {
            EntityDto dto = new EntityDto();
            dto.setDob(rs.getString("dob"));
            dto.setFname(rs.getString("fname"));
            dto.setGender(rs.getString("gender"));
            dto.setLname(rs.getString("lname"));
            dto.setAddress1(rs.getString("address1"));
            dto.setAddress2(rs.getString("address2"));
            dto.setCity(rs.getString("city"));
            dto.setCompany(rs.getString("company"));
            dto.setEducation(rs.getString("education"));
            dto.setHouseNo(rs.getString("House#"));
            dto.setMobile(rs.getString("Mobile#"));
            dto.setMonthlySalary(rs.getString("monthly_salary"));
            dto.setPincode(rs.getString("pincode"));
            dto.setRecordNo(rs.getString("Records#"));
            return dto;
        });
    }
    public List<EntityDto> getRecordsBetweenDobRange(String year1, String year2) {
//        LocalDate start = parseFlexibleDate(year1);
//        LocalDate end = parseFlexibleDate(year2);
        return memberepo.getDataBetweenTheYears(year1, year2);
    }

    private LocalDate parseFlexibleDate(String input) {
        List<String> patterns = List.of("dd-MM-yyyy", "dd/MM/yyyy");
        for (String pattern : patterns) {
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern(pattern));
            } catch (Exception ignored) {}
        }
        throw new IllegalArgumentException("Date format not supported: " + input);
    }


    //fetchthese records
    public List<EntityDto> filterBySalary() {
        String sql = """
        SELECT 
            dob, fname, gender, lname, address1, address2, city,
            company, education, `House#`, `Mobile#`, monthly_salary, 
            pincode, `Records#`
        FROM 
            members_table
        WHERE 
            CAST(monthly_salary AS UNSIGNED) >= 20000
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            EntityDto dto = new EntityDto();
            dto.setDob(rs.getString("dob"));
            dto.setFname(rs.getString("fname"));
            dto.setGender(rs.getString("gender"));
            dto.setLname(rs.getString("lname"));
            dto.setAddress1(rs.getString("address1"));
            dto.setAddress2(rs.getString("address2"));
            dto.setCity(rs.getString("city"));
            dto.setCompany(rs.getString("company"));
            dto.setEducation(rs.getString("education"));
            dto.setHouseNo(rs.getString("House#"));
            dto.setMobile(rs.getString("Mobile#"));
            dto.setMonthlySalary(rs.getString("monthly_salary"));
            dto.setPincode(rs.getString("pincode"));
            dto.setRecordNo(rs.getString("Records#"));
            return dto;
        });
    }

}

//get data is based on the yeras

