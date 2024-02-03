import org.junit.jupiter.api.Test;
import org.payroll.service.PayrollService;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayRollServiceTest {

    @Test
    void testReadEmployeeData() throws IOException, ParseException {
        String filename = "src/main/java/org/payroll/data/Employee_details.txt";
        PayrollService.readEmployeeData(filename);

        assertEquals(2, PayrollService.totalEmployee);
        assertEquals(3, PayrollService.onbordedEmployee.size());
    }
}
