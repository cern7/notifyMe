package com.notifyme.application.repository;

import com.notifyme.application.dto.EmployeeDTO;
import com.notifyme.application.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select innerEts.employee_id, innerEts.lastname, innerEts.firstname from \n" +
            "(\n" +
            "select ets.employee_id, ets.service_id, u.firstname, u.lastname\n" +
            "from employee e \n" +
            "join employee_to_service ets on e.user_iid = ets.employee_id \n" +
            "join usertbl u on e.user_iid =u.iid \n" +
            ") as innerEts\n" +
            "join service s on innerEts.service_id = s.iid where s.iid = ?1", nativeQuery = true)
    List<EmployeeDTO> getAllByServiceID(Long serviceID);
}