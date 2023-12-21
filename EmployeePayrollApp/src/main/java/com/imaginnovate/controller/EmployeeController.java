package com.imaginnovate.controller;

import com.imaginnovate.DO.Employee;
import com.imaginnovate.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @PostMapping(value = "/addEmployee")
    public ResponseEntity<String> addEmployee (@RequestBody Employee employee) throws Exception {
        try {
            employeeService.addEmployee(employee);
        }catch(Exception e){
            return new ResponseEntity<>("Invalid Input",HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("Employee added successfully",HttpStatus.OK);
    }
    @GetMapping(value = "/taxDetails")
    public ResponseEntity<String> getTaxDetails(@RequestParam("empId") String empId,@RequestParam("lop") Integer lop){
        try {
            String response = employeeService.getTaxDetails(empId,lop);
            if(response==null){
                return new ResponseEntity<>("Employee Details not found.",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Something Went Wrong",HttpStatus.BAD_REQUEST);
        }
    }

}
