package org.payroll.model;

import lombok.*;

import java.util.Date;
import java.util.HashMap;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private String empId;
    private String employeeFirstName;
    private String employeeLastName;
    private String designation;
    private String onboardingMonth;
    private String exitMonth;
    private HashMap<Event,String> eventData;
    HashMap<Event,Date> eventDateHashMap;
    private HashMap<Integer,String> listOfSequenceNote;


    public Employee(Employee employee) {
        this.empId = employee.getEmpId();
        this.employeeFirstName = employee.getEmployeeFirstName();
        this.employeeLastName = employee.getEmployeeLastName();
        this.designation = employee.getDesignation();
        this.eventData = new HashMap<>(employee.getEventData());
        this.eventDateHashMap = new HashMap<>(employee.getEventDateHashMap());
        this.listOfSequenceNote = new HashMap<>(employee.getListOfSequenceNote());
    }
}
