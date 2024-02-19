package com.example.practicespringdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.practicespringdata.entity.Employee;
import com.example.practicespringdata.filter.EmployeeFilter;
import com.example.practicespringdata.service.EmployeeService;

@SpringBootApplication
@EnableJpaRepositories("com.example.practicespringdata.repo")
public class PracticeSpringDataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PracticeSpringDataApplication.class, args);
	}

	@Autowired
	private EmployeeService employeeService;

	public void saveEmployees() {
		for (int i = 1; i <= 20; i++) {
			employeeService.save(new Employee("name " + i, 25 + i));
		}
	}

	public void searchEmployees() {
		Employee emp = new Employee();
		emp.setId(1);
		Example<Employee> example = Example.of(emp);

		System.out.println("------------ Example ------------------------");
		employeeService.searchEmployee(example).stream().forEach(System.out::println);

		System.out.println("--------------- Search default list ---------------------");
		employeeService.findAllEmployeesByIds(List.of(2, 3, 12)).stream().forEach(System.out::println);

		System.out.println("---------------- Search derived query list--------------------");
		employeeService.findAllEmployeesByNames(List.of("name 1", "name 9", "name 18")).stream()
				.forEach(System.out::println);

	}

	public void searchEmployeesWithCriteria() {
		EmployeeFilter filter = new EmployeeFilter();
		filter.setName("name 6");
		filter.setAge(31);

		System.out.println("---------------- Search with criteria --------------------");
		employeeService.searchEmployeesWithCriteria(filter).stream().forEach(System.out::println);
	}
	
	public void searchEmployeesWithSpecifications() {
		EmployeeFilter filter = new EmployeeFilter();
		filter.setName("name 9");
		filter.setAge(34);

		System.out.println("---------------- Search with criteria --------------------");
		employeeService.searchEmployeesWithSpecifications(filter).stream().forEach(System.out::println);
	}

	@Override
	public void run(String... args) throws Exception {
		// saveEmployees();
		// searchEmployees();
		// searchEmployeesWithCriteria();
		searchEmployeesWithSpecifications();
		System.out.println("work completed...");
	}

}
