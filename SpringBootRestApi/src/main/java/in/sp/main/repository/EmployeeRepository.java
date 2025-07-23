package in.sp.main.repository;

import in.sp.main.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


    public interface EmployeeRepository extends JpaRepository<Employee, Long> {


        List<Employee> findByName(String name);
    }




