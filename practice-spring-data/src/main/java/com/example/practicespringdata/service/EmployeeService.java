package com.example.practicespringdata.service;

import static com.example.practicespringdata.specifications.EmployeeSpecifications.hasAge;
import static com.example.practicespringdata.specifications.EmployeeSpecifications.hasId;
import static com.example.practicespringdata.specifications.EmployeeSpecifications.hasName;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.practicespringdata.entity.Employee;
import com.example.practicespringdata.filter.EmployeeFilter;
import com.example.practicespringdata.repo.EmployeeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class EmployeeService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee save(Employee emp) {
		return employeeRepository.save(emp);
	}

	public List<Employee> searchEmployee(Example<Employee> example) {
		return employeeRepository.findAll(example);
	}

	public List<Employee> findAllEmployeesByIds(List<Integer> ids) {
		return employeeRepository.findAllById(ids);
	}

	public List<Employee> findAllEmployeesByNames(List<String> names) {
		return employeeRepository.findAllByNameIn(names);
	}

	public List<Employee> searchEmployeesWithCriteria(EmployeeFilter filter) {
		CriteriaBuilder critBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Employee> critQuery = critBuilder.createQuery(Employee.class);
		Root<Employee> empRoot = critQuery.from(Employee.class);
		List<Predicate> predicates = new ArrayList<>();

		if (filter.getId() != null) {
			predicates.add(critBuilder.equal(empRoot.get("id"), filter.getId()));
		}

		if (filter.getName() != null) {
			predicates.add(critBuilder.equal(empRoot.get("name"), filter.getName()));
		}

		if (filter.getAge() != null) {
			predicates.add(critBuilder.equal(empRoot.get("age"), filter.getAge()));
		}

		critQuery.where(predicates.toArray(new Predicate[0]));
		return em.createQuery(critQuery).getResultList();
	}
	
	public List<Employee> searchEmployeesWithSpecifications(EmployeeFilter filter){
		return employeeRepository.findAll(Specification
				.where(filter.getId()==null ? null : hasId(filter.getId()))
				.and(filter.getName() ==null ? null : hasName(filter.getName()))
				.and(filter.getAge() == null ? null : hasAge(filter.getAge())));
	}
}
