package org.payroll.util;


import org.payroll.model.Employee;
import org.payroll.model.Event;
import org.payroll.service.PayrollService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.payroll.service.PayrollService.exitedEmployee;
import static org.payroll.service.PayrollService.onbordedEmployee;

public class PayrollUtil {
    public static void generateReports() {
        // 1. Total number of employees in an organization.
        System.out.println("Total number of employees: " + PayrollService.totalEmployee);
        Map<String, List<Map<String, String>>> monthlyEmployeeJoined = onbordedEmployee.stream()
                .collect(Collectors.groupingBy(Employee::getOnboardingMonth,
                        Collectors.mapping(PayrollUtil::getEmployeeDetails, Collectors.toList())));
        System.out.println("Total No Of Employee Joined Monthy..");
        for (String month : monthlyEmployeeJoined.keySet()) {
            System.out.println("Month " + month + "-->" + monthlyEmployeeJoined.get(month).size());
            System.out.println("Joined Employee " + monthlyEmployeeJoined.get(month));
        }
        System.out.println(exitedEmployee);
        Map<String, List<Map<String, String>>> monthlyEmployeeExited = exitedEmployee.stream()
                .collect(Collectors.groupingBy(Employee::getExitMonth,
                        Collectors.mapping(PayrollUtil::getEmployeeDetails, Collectors.toList())));
        System.out.println("Total No Of Employee exited Monthy..");
        for (String month : monthlyEmployeeExited.keySet()) {
            System.out.println("Month " + month + "-->" + monthlyEmployeeExited.get(month).size());
            System.out.println("Exited Employee " + monthlyEmployeeExited.get(month));
        }
        System.out.println("Monthly Total Income..");
        Map<String, Integer> monthlyTotalIncome = onbordedEmployee.stream()
                .collect(Collectors.groupingBy(Employee::getOnboardingMonth,
                        Collectors.summingInt(employee -> {
                            String salaryValue = employee.getEventData().get(Event.SALARY);
                            return (salaryValue != null) ? Integer.parseInt(salaryValue) : 0;
                        })));

        monthlyTotalIncome.forEach((month, totalIncome) ->
                System.out.println("Month: " + month + ", Total Income: " + totalIncome +
                        ", Total Employees: " + getTotalEmployeesForMonth(month)));
        System.out.println("Employee Wise Financial Report..");
        onbordedEmployee.forEach(employee -> {
            int totalAmountPaid = employee.getEventData().values().stream()
                    .mapToInt(value -> {
                        try {
                            return Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    })
                    .sum();
            System.out.println("Employee Id: " + employee.getEmpId() +
                    ", Name: " + employee.getEmployeeFirstName() +
                    ", Surname: " + employee.getEmployeeLastName() +
                    ", Total Amount Paid: " + totalAmountPaid);
        });
        System.out.println("Yearly Financial Report..");
        onbordedEmployee.forEach(employee -> {
            employee.getEventDateHashMap().forEach((event, date) ->
                    System.out.println("Event: " + event +
                            ", Emp Id: " + employee.getEmpId() +
                            ", Event Date: " + date +
                            ", Event Value: " + employee.getEventData().get(event)));
        });
    }

    private static long getTotalEmployeesForMonth(String month) {
        return onbordedEmployee.stream()
                .filter(employee -> employee.getOnboardingMonth().equals(month))
                .count();
    }
    private static int calculateTotalAmount(Map<Event, Integer> eventData) {
        return eventData.values().stream().mapToInt(Integer::intValue).sum();
    }

    private static Map<String, String> getEmployeeDetails(Employee employee) {
        Map<String, String> details = new HashMap<>();
        details.put("Emp Id", employee.getEmpId());
        details.put("Designation", employee.getDesignation());
        details.put("Name", employee.getEmployeeFirstName());
        details.put("Surname", employee.getEmployeeLastName());
        return details;
    }
    private static long getTotalEmployeesForMonthAndEvent(String month, Event event) {
        return onbordedEmployee.stream()
                .filter(employee -> employee.getOnboardingMonth().equals(month) && employee.getEventData().containsKey(event))
                .count();
    }
}
