package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.model.EmployeeObject;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empRepository;

    public EmployeeObject getEmpById(int id) {
        EmployeeObject emp = null;
        try {
            emp = empRepository.findById(id).get();
        } catch(Exception e) {
            System.out.println("Document not found" + e.getMessage());
        }
        return emp;
    }

    public EmployeeObject saveOrUpdate(EmployeeObject emp) {
        return empRepository.save(emp);
    }
}
