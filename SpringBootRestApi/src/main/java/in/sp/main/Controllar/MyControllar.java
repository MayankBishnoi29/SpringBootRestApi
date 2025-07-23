
package in.sp.main.Controllar;

import in.sp.main.entity.Employee;
import in.sp.main.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
public class MyControllar {
    @Autowired
    private EmployeeServices employeeServices;

    @PostMapping("/employee")
    public Employee addEmployeeDetails(@RequestBody Employee employee) {
        return employeeServices.createEmployee(employee);
    }

    @GetMapping("/employee")
    public List<Employee> getAllEmployee() {
        return employeeServices.getAllEmployees();
    }
    @GetMapping("/employee/{id}")
    public ResponseEntity< Employee> getById(@PathVariable Long id) {
        Employee employee= employeeServices.getById(id).orElse(null);
        if (employee != null) {
            return ResponseEntity.ok().body(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    @GetMapping("/employeeName/{name}")
//    public List<Employee> getByName(@PathVariable String name) {
//        return employeeServices.getByName(name);
//    }
    @GetMapping("/employeeName/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        List<Employee> employees = employeeServices.getByName(name);
        if (employees == null || employees.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employee not found with name: " + name);
            return ResponseEntity.status(404).body(response);
        } else {
            return ResponseEntity.ok(employees);
        }
    }
//    @PutMapping("/employee/{id}")
//    public ResponseEntity <Employee> updateEmployeeDetails(@PathVariable Long id, @RequestBody Employee  newemployee) {
//
//        Employee updateEmployee= employeeServices.updateEmployeeDetails(id,newemployee);
//    if(updateEmployee != null) {
//            return ResponseEntity.ok().body(updateEmployee);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    @PutMapping("/employee/{id}")
//    public ResponseEntity<?> updateEmployeeDetails(@PathVariable Long id, @RequestBody Employee newemployee) {
//        Employee updateEmployee = employeeServices.updateEmployeeDetails(id, newemployee);
//        if (updateEmployee != null) {
//            return ResponseEntity.ok().body(updateEmployee);
//        } else {
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Employee not found with id: " + id);
//            return ResponseEntity.status(404).body(response);
//        }
//    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployeeDetails(@PathVariable Long id, @RequestBody Employee newemployee) {
        Employee updateEmployee = employeeServices.updateEmployeeDetails(id, newemployee);
        Map<String, Object> response = new HashMap<>();
        if (updateEmployee != null) {
            response.put("message", "Employee updated successfully");
            response.put("employee", updateEmployee);
            return ResponseEntity.ok().body(response);
        } else {
            response.put("message", "Employee not updated");
            return ResponseEntity.status(404).body(response);
        }
    }
    @DeleteMapping("/employee/{id}")
            public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
                try {
                    employeeServices.deleteEmployee(id);
                    return ResponseEntity.ok("Employee deleted");
                } catch (RuntimeException e) {
                    return ResponseEntity.status(404).body("Employee not deleted");
                }
            }


    @GetMapping("/employee/page")
    public Page<Employee> getAllByPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return employeeServices.getAllByPage(PageRequest.of(page, size));
    }}