package org.payroll.controller;

import org.payroll.service.PayrollService;
//import org.payroll.data.util.PayrollUtil;

import java.io.IOException;
import java.text.ParseException;

public class PayrollController {

    public static void readEmployeeData(String filname)
    {
        try {
            PayrollService.readEmployeeData(filname);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
