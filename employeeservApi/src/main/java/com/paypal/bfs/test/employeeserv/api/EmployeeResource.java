package com.paypal.bfs.test.employeeserv.api;



import javax.validation.Valid;

import com.paypal.bfs.test.employeeserv.model.EmployeeObject;
import com.paypal.bfs.test.employeeserv.model.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paypal.bfs.test.employeeserv.api.model.Employee;



/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @return {@link Employee} resource.
     */
    @RequestMapping("/v1/bfs/employees/{id}")
    ResponseEntity<ResponseObject> employeeGetById(@PathVariable("id") Integer id);
    // ----------------------------------------------------------
    // TODO - add a new operation for creating employee resource.
    // ----------------------------------------------------------

    @PostMapping("/v1/bfs/employee")
    ResponseEntity<ResponseObject> saveEmployee(@RequestBody Employee emp);
    
}
