package com.csvassignmentbysir.csvassignment.controller;

import com.csvassignmentbysir.csvassignment.EntityDto;
import com.csvassignmentbysir.csvassignment.service.EmbeddedService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/file")
public class EmbeddedMemberController {
    @Autowired
    EmbeddedService embeddedService;

    @PostMapping("/upload")
   String validCsvDatabase(@RequestParam("file") MultipartFile file) throws CsvValidationException, IOException {
       return embeddedService.sortTheData(file);
   }

    @GetMapping("/get-details")
    List<EntityDto> getRecordsStartsWithFnameLname(@RequestParam("fname") String fname, @RequestParam("lname") String lname){
    return embeddedService.getStartsWithFnameLname(fname,lname);
    }
    @GetMapping("/between-years")
    public List<EntityDto> getDataBetweenYears(@RequestParam("start") String start,
                                               @RequestParam("end") String end) {
        return embeddedService.getRecordsBetweenDobRange(start, end);
    }

    @GetMapping("/more-than-20-k")
    List<EntityDto> salaryDtoFetch(){
        return embeddedService.filterBySalary();
    }

}
