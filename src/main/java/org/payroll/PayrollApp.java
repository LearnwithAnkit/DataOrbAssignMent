package org.payroll;
import org.payroll.service.PayrollService;
import org.payroll.util.PayrollUtil;

import org.payroll.controller.PayrollController;

public class PayrollApp {
    public static void main(String[] args)
    {
        final String FILE_PATH = "src/main/java/org/payroll/data/Employee_details.txt";
        PayrollController.readEmployeeData(FILE_PATH);
        if(PayrollService.totalEmployee==0)
        {
            System.out.println("No Record Found");
        }
        else {
            System.out.println("Generating Report...");
            PayrollUtil.generateReports();
        }


    }
}
