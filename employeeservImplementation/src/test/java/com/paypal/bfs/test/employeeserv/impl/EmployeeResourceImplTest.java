package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.model.EmployeeObject;
import com.paypal.bfs.test.employeeserv.model.ResponseObject;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeResourceImplTest {

    private static final int EMP_NOT_FOUND = 121;
    private static final int EMP_EXISTS = 122;
    private static final int EMP_CREATED = 123;

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeResourceImpl impl;

    @Before
    public void setUp() {
        when(service.getEmpById(EMP_NOT_FOUND)).thenReturn(null);
        when(service.getEmpById(EMP_EXISTS)).thenReturn(prepareDummyResponse(EMP_EXISTS));
        when(service.saveOrUpdate(any())).thenReturn(prepareDummyResponse(EMP_CREATED));
        ReflectionTestUtils.setField(impl, "schemaLocation", "v1/schema/employee.json");
    }

    @Test
    public void getEmpById_Returns_404_When_Emp_Notfound() {
        ResponseEntity<ResponseObject> resp = impl.employeeGetById(EMP_NOT_FOUND);
        Assert.assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }

    @Test
    public void getEmpById_Returns_Record_When_Emp_Found() {
        ResponseEntity<ResponseObject> resp = impl.employeeGetById(EMP_EXISTS);
        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertEquals(new Integer(EMP_EXISTS), resp.getBody().getEmployeeData().getId());
    }

    @Test
    public void saveEmployee_Returns_Success_When_Emp_Data_Is_Valid() {

        Employee emp = new Employee();
        BeanUtils.copyProperties(prepareDummyResponse(EMP_CREATED), emp);

        ResponseEntity<ResponseObject> resp = impl.saveEmployee(emp);
        Assert.assertEquals(HttpStatus.ACCEPTED, resp.getStatusCode());
        Assert.assertEquals(new Integer(EMP_CREATED), resp.getBody().getEmployeeData().getId());
    }

    @Test
    public void saveEmployee_Returns_Error_When_Mandatory_Data_Missing() {

        Employee emp = new Employee();
        BeanUtils.copyProperties(missingMandatoryFirstName(EMP_CREATED), emp);

        ResponseEntity<ResponseObject> resp = impl.saveEmployee(emp);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, resp.getStatusCode());
        Assert.assertEquals(new Integer(EMP_CREATED), resp.getBody().getEmployeeData().getId());
    }

    private EmployeeObject prepareDummyResponse(final int empId) {

        //Test object
        EmployeeObject obj = new EmployeeObject();

        obj.setId(empId);
        obj.setFirstName("First Name");
        obj.setLastName("Last Name");

        return obj;
    }

    private EmployeeObject missingMandatoryFirstName(final int empId) {

        //Test object
        EmployeeObject obj = new EmployeeObject();

        obj.setId(empId);
        //obj.setFirstName("First Name");
        obj.setLastName("Last Name");

        return obj;
    }
}
