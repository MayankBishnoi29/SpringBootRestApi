
package in.sp.main.Controllar;

import in.sp.main.entity.Employee;
import in.sp.main.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyControllar {
    @Autowired
    private EmployeeServices employeeServices;

    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee employee) {
    return employeeServices.createEmployee(employee);
}

    @GetMapping("/employee")
    public List<Employee> getAllEmployee() {
        return employeeServices.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeServices.getById(id).orElse(null);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/employeeName/{name}")
    public List<Employee> getByName(@PathVariable String name) {
      return   employeeServices.getByName(name);

        }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployeeDetails(@PathVariable Long id, @RequestBody Employee newemployee) {
        Employee updateEmployee = employeeServices.updateEmployeeDetails(id, newemployee);
        if (updateEmployee != null) {
            return ResponseEntity.ok().body(updateEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeServices.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/employee/page")
    public Page<Employee> getAllByPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return employeeServices.getAllByPage(PageRequest.of(page, size));
    }
}