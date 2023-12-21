package com.imaginnovate.service;

import com.imaginnovate.DO.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

    public static Map<String,Employee> employeeMap = new HashMap<>();
    public Boolean addEmployee(Employee employee) throws Exception{
        validateEmployee(employee);
        employeeMap.put(employee.getEmployeeId(),employee);
        System.out.println(employeeMap);
        return true;
    }

    public String getTaxDetails(String empId, Integer lop){
        String response = null;
        Employee employee = employeeMap.get(empId);
        if(employee!=null){
            Double yearlySalary = calculateYearlySal(employee,lop);
            Double taxAmt = calculateTax(yearlySalary);
            Double cessAmt = calculateCessAmt(yearlySalary);
            response = generateResponse(employee,yearlySalary,taxAmt,cessAmt);
        }
        return response;
    }

    private Double calculateCessAmt(Double yearlySalary) {
        Double cessAmt = 0d;
        if(yearlySalary>2500000){
            cessAmt = (yearlySalary-2500000)*0.02;
        }
        return cessAmt;
    }

    private Double calculateTax(Double yearlySalary) {
        Double taxAmt = null;
        if(yearlySalary<=250000){
            taxAmt = 0d;
        } else if (yearlySalary>250000 && yearlySalary<=500000) {
            taxAmt = (yearlySalary-250000)*0.05;
        } else if (yearlySalary>500000 && yearlySalary<=1000000) {
            taxAmt = (250000*0.05) + (yearlySalary-500000)*0.1 ;
        }else{
            taxAmt = (250000*0.05) + (500000*0.1) + (yearlySalary-1000000)*0.2;
        }
        return taxAmt;
    }

    private Double calculateYearlySal(Employee employee, Integer lop) {
        LocalDate taxYearStart = LocalDate.of(2023,4,1);
        Double yearlySalary = null;
        if(employee.getDoj().isBefore(taxYearStart)){
            yearlySalary = employee.getSalary()*12;
        }else{
            int monthVal = employee.getDoj().getMonthValue();
            int dayVal = employee.getDoj().getDayOfMonth();
            yearlySalary = employee.getSalary()*(16-monthVal)-((dayVal-1)* employee.getSalary()/30);
        }
        yearlySalary -= (employee.getSalary()/30*lop);
        return yearlySalary;
    }

    private void validateEmployee(Employee employee) throws Exception{
        if(employee.getEmployeeId().equals(null) || employee.getFirstName().equals(null) ||
                employee.getLastName().equals(null) || employee.getEmail().equals(null) ||
                employee.getDoj().equals(null) || employee.getPhoneNumber().equals(null) ||
                employee.getSalary().equals(null)){
            throw new Exception("Invalid Input");
        }
    }
    private String generateResponse(Employee employee, Double yearlySalary, Double taxAmount, Double cessAmount){
        return "{\"EmployeeId\":\""+employee.getEmployeeId()+"\",\"FirstName\":\""+employee.getFirstName()+
                "\",\"LastName\":\""+employee.getLastName()+"\",\"Yearly Salary\":\""+yearlySalary+
                "\",\"Tax Amount\""+taxAmount+"\",\"Cess Amount\":\""+cessAmount+"\"";
    }
}
