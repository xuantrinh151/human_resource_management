package com.trinhnx151.human_resource_management.database;

import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.entity.Record;
import com.trinhnx151.human_resource_management.repository.EmployeeRepo;
import com.trinhnx151.human_resource_management.repository.RecordRepo;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trinhnx151.human_resource_management.entity.Department;
import com.trinhnx151.human_resource_management.repository.DepartmentRepo;

import java.sql.Timestamp;

@Configuration
public class H2Database {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(H2Database.class);

    @Bean
    CommandLineRunner initDatabase(DepartmentRepo departmentRepo, EmployeeRepo employeeRepo, RecordRepo recordRepo) {

        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                Department departmentA = new Department("Phong A", "A", 1);
                Department departmentB = new Department("Phong B", "B", 1);
                Department departmentC = new Department("Phong C", "C", 1);
                Employee employeeA = new Employee(1L, "Code A", "NV A", true, "Image A", Timestamp.valueOf("2001-09-01 09:01:15"), 1000, Employee.Level.LEVEL_1, "Email A", "Phone A", Employee.Status.IN_USE,1L);
                Employee employeeB = new Employee(2L, "Code B", "NV B", true, "Image B", Timestamp.valueOf("2000-10-01 09:01:15"), 1500, Employee.Level.LEVEL_2, "Email B", "Phone B", Employee.Status.IN_USE,1L);
                Employee employeeC = new Employee(3L, "Code C", "NV C", false, "Image C", Timestamp.valueOf("2002-11-01 09:01:15"), 2000, Employee.Level.LEVEL_3, "Email C", "Phone C", Employee.Status.IN_USE,1L);
                Employee employeeD = new Employee(4L, "Code D", "NV D", true, "Image D", Timestamp.valueOf("2001-12-01 09:01:15"), 1000, Employee.Level.LEVEL_1, "Email D", "Phone D", Employee.Status.IN_USE,2L);
                Employee employeE = new Employee(5L, "Code E", "NV E", false, "Image E", Timestamp.valueOf("2000-11-02 09:01:15"), 1500, Employee.Level.LEVEL_1, "Email E", "Phone E", Employee.Status.IN_USE,2L);
                Employee employeeAA = new Employee(6L, "Code AA", "NV AA", true, "Image AA", Timestamp.valueOf("2002-11-01 09:01:15"), 2000, Employee.Level.LEVEL_2, "Email AA", "Phone AA", Employee.Status.IN_USE,2L);
                Employee employeeAAA = new Employee(7L, "Code AAA", "NV AAA", false, "Image AAA", Timestamp.valueOf("2001-09-01 09:01:15"), 1000, Employee.Level.LEVEL_3, "Email AAA", "Phone AAA", Employee.Status.IN_USE,3L);
                Employee employeeAAAA = new Employee(8L, "Code AAAA", "NV AAAA", true, "Image AAAA", Timestamp.valueOf("2000-11-01 09:01:15"), 1500, Employee.Level.LEVEL_1, "Email AAAA", "Phone AAAA", Employee.Status.IN_USE,3L);
                Employee employeeCC = new Employee(9L, "Code CC", "NV CC", true, "Image CC", Timestamp.valueOf("2002-09-01 09:01:15"), 2000, Employee.Level.LEVEL_10, "Email CC", "Phone CC", Employee.Status.IN_USE,3L);
                Employee employeeA1 = new Employee(10L, "Code A1", "NV A1", true, "Image A1", Timestamp.valueOf("2000-12-01 09:01:15"), 1500, Employee.Level.LEVEL_1, "Email A1", "Phone A1", Employee.Status.IN_USE,3L);
                Employee employeeC1 = new Employee(11L, "Code C1", "NV C1", true, "Image C1", Timestamp.valueOf("2002-09-01 09:01:15"), 2000, Employee.Level.LEVEL_10, "Email C1", "Phone C1", Employee.Status.IN_USE,3L);


                Record record1 = new Record(1L,true,"reason1",Record.Status.IN_USE,1L,Timestamp.valueOf("2022-01-01 09:01:15"));
                Record record2 = new Record(2L,true,"reason2",Record.Status.IN_USE,1L,Timestamp.valueOf("2022-02-01 09:01:15"));
                Record record3 = new Record(3L,false,"reason3",Record.Status.IN_USE,1L,Timestamp.valueOf("2022-03-01 09:01:15"));
                Record record4 = new Record(4L,true,"reason4",Record.Status.IN_USE,5L,Timestamp.valueOf("2022-04-01 09:01:15"));
                Record record5 = new Record(5L,true,"reason5",Record.Status.IN_USE,4L,Timestamp.valueOf("2022-05-01 09:01:15"));
                Record record6 = new Record(6L,false,"reason6",Record.Status.IN_USE,3L,Timestamp.valueOf("2022-06-01 09:01:15"));
                Record record7 = new Record(7L,true,"reason7",Record.Status.IN_USE,2L,Timestamp.valueOf("2022-07-01 09:01:15"));
                Record record8 = new Record(8L,false,"reason8",Record.Status.IN_USE,2L,Timestamp.valueOf("2022-08-01 09:01:15"));
                Record record9 = new Record(9L,true,"reason9",Record.Status.IN_USE,6L,Timestamp.valueOf("2022-01-01 09:01:15"));
                Record record10 = new Record(10L,true,"reason10",Record.Status.IN_USE,7L,Timestamp.valueOf("2022-02-01 09:01:15"));
                Record record11 = new Record(11L,false,"reason11",Record.Status.IN_USE,8L,Timestamp.valueOf("2022-03-01 09:01:15"));
                Record record12 = new Record(12L,true,"reason12",Record.Status.IN_USE,9L,Timestamp.valueOf("2022-04-01 09:01:15"));
                Record record13 = new Record(13L,true,"reason13",Record.Status.IN_USE,10L,Timestamp.valueOf("2022-05-01 09:01:15"));
                Record record14 = new Record(14L,false,"reason14",Record.Status.IN_USE,11L,Timestamp.valueOf("2022-06-01 09:01:15"));
                Record record15 = new Record(15L,true,"reason15",Record.Status.IN_USE,3L,Timestamp.valueOf("2022-07-01 09:01:15"));
                Record record16 = new Record(16L,false,"reason16",Record.Status.IN_USE,7L,Timestamp.valueOf("2022-08-01 09:01:15"));
                Record record17 = new Record(16L,true,"reason17",Record.Status.IN_USE,8L,Timestamp.valueOf("2022-01-01 09:01:15"));
                Record record18 = new Record(18L,true,"reason18",Record.Status.IN_USE,10L,Timestamp.valueOf("2022-02-01 09:01:15"));
                Record record19 = new Record(19L,false,"reason19",Record.Status.IN_USE,1L,Timestamp.valueOf("2022-03-01 09:01:15"));
                Record record20 = new Record(20L,true,"reason20",Record.Status.IN_USE,5L,Timestamp.valueOf("2022-04-01 09:01:15"));
                Record record21 = new Record(21L,true,"reason21",Record.Status.IN_USE,4L,Timestamp.valueOf("2022-05-01 09:01:15"));
                Record record22 = new Record(22L,false,"reason22",Record.Status.IN_USE,3L,Timestamp.valueOf("2022-06-01 09:01:15"));
                Record record23 = new Record(23L,true,"reason23",Record.Status.IN_USE,2L,Timestamp.valueOf("2022-07-01 09:01:15"));
                Record record24 = new Record(24L,false,"reason24",Record.Status.IN_USE,2L,Timestamp.valueOf("2022-08-01 09:01:15"));








                System.out.println("insert data: " + departmentRepo.save(departmentA));
                System.out.println("insert data: " + departmentRepo.save(departmentB));
                System.out.println("insert data: " + departmentRepo.save(departmentC));
                System.out.println("insert data: " + employeeRepo.save(employeeA));
                System.out.println("insert data: " + employeeRepo.save(employeeB));
                System.out.println("insert data: " + employeeRepo.save(employeeC));
                System.out.println("insert data: " + employeeRepo.save(employeeD));
                System.out.println("insert data: " + employeeRepo.save(employeE));
                System.out.println("insert data: " + employeeRepo.save(employeeAA));
                System.out.println("insert data: " + employeeRepo.save(employeeAAA));
                System.out.println("insert data: " + employeeRepo.save(employeeAAAA));
                System.out.println("insert data: " + employeeRepo.save(employeeCC));
                System.out.println("insert data: " + employeeRepo.save(employeeA1));
                System.out.println("insert data: " + employeeRepo.save(employeeC1));

                System.out.println(recordRepo.save(record1));
                System.out.println(recordRepo.save(record2));
                System.out.println(recordRepo.save(record3));
                System.out.println(recordRepo.save(record4));
                System.out.println(recordRepo.save(record5));
                System.out.println(recordRepo.save(record6));
                System.out.println(recordRepo.save(record7));
                System.out.println(recordRepo.save(record8));
                recordRepo.save(record9);
                recordRepo.save(record10);
                recordRepo.save(record11);
                recordRepo.save(record12);
                recordRepo.save(record13);
                recordRepo.save(record14);
                recordRepo.save(record15);
                recordRepo.save(record16);
                recordRepo.save(record17);
                recordRepo.save(record18);
                recordRepo.save(record19);
                recordRepo.save(record20);
                recordRepo.save(record21);
                recordRepo.save(record22);
                recordRepo.save(record23);
                recordRepo.save(record24);

            }

        };
    }
}
