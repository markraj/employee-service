package com.paypal.bfs.test.employeeserv.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.model.AddressObject;
import com.paypal.bfs.test.employeeserv.model.EmployeeObject;
import com.paypal.bfs.test.employeeserv.model.ResponseObject;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.google.common.base.Charsets.UTF_8;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeService empService;

    @Value("${employee.json.schema.location}")
    private String schemaLocation;

    @Override
    public ResponseEntity<ResponseObject> employeeGetById(final Integer id) {

        EmployeeObject employeeObj = empService.getEmpById(id);

        if (null == employeeObj) {
            return new ResponseEntity<>(new ResponseObject(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null),
                    HttpStatus.NOT_FOUND);
        }

        Employee emp = prepareResponse(employeeObj);
        System.out.println("Returned Employee Response:" + emp.toString());

        return new ResponseEntity<>(new ResponseObject(HttpStatus.OK.value(), "SUCCESS", emp), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> saveEmployee(final Employee emp) {

        try {
            isValid(emp);
        } catch (Exception e) {
            System.out.println("Exception occurred" + e.getMessage());
            return new ResponseEntity<>(new ResponseObject(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), emp),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        EmployeeObject empObject = prepareEntity(emp);
        System.out.println("**** Employee data to be persisted ****\n" + empObject.toString());

        empObject = empService.saveOrUpdate(empObject);

        Employee empResponse = prepareResponse(empObject);
        System.out.println("Returned Employee Response:" + empResponse.toString());
        return new ResponseEntity<>(new ResponseObject(HttpStatus.ACCEPTED.value(), "SUCCESS", empResponse),
                HttpStatus.ACCEPTED);
    }

    /**
     * Copies the incoming request data to the backend Entity to be persisted.
     *
     * @param emp
     * @return
     */
    private EmployeeObject prepareEntity(final Employee emp) {

        EmployeeObject empObject = new EmployeeObject();
        BeanUtils.copyProperties(emp, empObject);

        if (null != emp.getAddress()) {
            AddressObject addressObject = new AddressObject();
            BeanUtils.copyProperties(emp.getAddress(), addressObject);
            System.out.println("**** Address: ****\n" + addressObject.toString());
            empObject.setAddress(addressObject);
        }
        System.out.println("**** Employee: ****\n" + empObject.toString());
        return empObject;
    }

    /**
     * Copies the entity data across to response object.
     *
     * @param empObject
     * @return
     */
    private Employee prepareResponse(final EmployeeObject empObject) {
        Employee emp = new Employee();
        BeanUtils.copyProperties(empObject, emp);

        if (null != empObject.getAddress()) {
            Address address = new Address();
            BeanUtils.copyProperties(empObject.getAddress(), address);
            System.out.println("**** Address response: ****\n" + address.toString());
            System.out.println(address.toString());

            emp.setAddress(address);
        }
        System.out.println("**** Employee response: ****\n" + emp.toString());
        return emp;
    }

    private boolean isValid(final Employee employee) throws IOException {

        String empAsString = new ObjectMapper().writeValueAsString(employee);
        System.out.println("Emp object as String:" + empAsString);
        JSONObject empJsonObject = new JSONObject(empAsString);

        String jsonSchemaAsString = Resources.toString(Resources.getResource(schemaLocation), UTF_8);

        JSONObject jsonSchema = new JSONObject(jsonSchemaAsString);
        System.out.println("jsonSchema: "+jsonSchema);
        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(empJsonObject);

        return true;
    }

}
