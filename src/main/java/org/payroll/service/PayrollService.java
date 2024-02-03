package org.payroll.service;

import org.payroll.model.Employee;
import org.payroll.model.Event;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PayrollService {
    public static List<Employee> onbordedEmployee=new ArrayList<>();
    public static List<Employee> exitedEmployee=new ArrayList<>();
    public static int totalEmployee=0;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static void readEmployeeData(String filename) throws IOException, ParseException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                processEmployeeRecord(line);
            }
        }
    }

    private static void processEmployeeRecord(String line) throws ParseException {
        String[] data = line.split(", ");
        Event event=getEvent(data);
     // Process event-specific fields based on the event type
        switch (event) {
            case ONBOARD:
                processOnboarding(data);
                break;
            case SALARY:
                processSalary(data);
                break;
            case BONUS:
                processBonus(data);
                break;

            case EXIT:
                processExit(data);
                break;

            case REIMBURSEMENT:
                processReimbursement(data);
                break;
        }
    }

    private static void processExit(String[] data) throws ParseException {
        totalEmployee--;
        for(Employee employee:onbordedEmployee) {
            if (employee.getEmpId().equals(data[1])) {
                employee.getEventData().put(Event.EXIT, data[3]);
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                Date date = sdf.parse(data[3]);
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                String month = monthFormat.format(date);
                employee.getListOfSequenceNote().put(Integer.parseInt(data[0]), data[5]);
                employee.setExitMonth(month);
                try {
                    employee.getEventDateHashMap().put(Event.EXIT, parseDate(data[4]));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Employee exitEmployee = new Employee(employee);
                exitEmployee.setExitMonth(month);
                exitedEmployee.add(exitEmployee);
                return;
            }
        };
    }

    private static void processReimbursement(String[] data) throws ParseException {
        for (Employee employee : onbordedEmployee) {
            if (employee.getEmpId().equals(data[1])) {
                employee.getEventData().put(Event.REIMBURSEMENT, data[3]);
                employee.getListOfSequenceNote().put(Integer.parseInt(data[0]), data[5]);
                employee.getEventDateHashMap().put(Event.REIMBURSEMENT, parseDate(data[4]));
            }
        }
    }

    private static void processBonus(String[] data) throws ParseException {
        for (Employee employee : onbordedEmployee) {
        if(employee.getEmpId().equals(data[1]))
        {
            employee.getEventData().put(Event.BONUS,data[3]);
            employee.getListOfSequenceNote().put(Integer.parseInt(data[0]),data[5]);
            employee.getEventDateHashMap().put(Event.BONUS,parseDate(data[4]));
        }

    }
    }

    private static void processSalary(String[] data) throws ParseException {
        for(Employee employee:onbordedEmployee)
        {
            if(employee.getEmpId().equals(data[1]))
            {
                employee.getEventData().put(Event.SALARY,data[3]);
                employee.getListOfSequenceNote().put(Integer.parseInt(data[0]),data[5]);
                employee.getEventDateHashMap().put(Event.SALARY,parseDate(data[4]));
            }
        }
    }

    private static void processOnboarding(String[] data) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date = sdf.parse(data[6]);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        String month = monthFormat.format(date);
        HashMap<Event,String> objectsHashMap=new HashMap<>();
        HashMap<Integer,String> sequenceNotes=new HashMap<>();
        HashMap<Event,Date> eventDateHashMap=new HashMap<>();
        eventDateHashMap.put(Event.SALARY,parseDate(data[7]));
        sequenceNotes.put(Integer.parseInt(data[0]),data[8]);
        objectsHashMap.put(Event.ONBOARD,parseDate(data[6]).toString());
        Employee employee= Employee.builder().empId(data[1]).onboardingMonth(month).employeeFirstName(data[2]).employeeLastName(data[3]).designation(data[4]).eventData(objectsHashMap).eventDateHashMap(eventDateHashMap).listOfSequenceNote(sequenceNotes).build();
        onbordedEmployee.add(employee);
        PayrollService.totalEmployee++;
    }

    private static Event getEvent(String[] data) {
        for (String empData : data) {
            switch (empData) {
                case "ONBOARD":
                    return Event.ONBOARD;

                case "SALARY":
                    return Event.SALARY;

                case "BONUS":
                    return Event.BONUS;

                case "EXIT":
                    return Event.EXIT;

                case "REIMBURSEMENT":
                    return Event.REIMBURSEMENT;
            }
        }
        return Event.INVALID;
    }
    private static Date parseDate(String dateString) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
    }
}
