package com.example.practicespringdata.specifications;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.practicespringdata.entity.Employee;

@Component
public class EmployeeSpecifications {

	public static Specification<Employee> hasId(Integer id) {
		return (root, query, builder) -> builder.equal(root.get("id"), id);
	}

	public static Specification<Employee> hasName(String name) {
		return (root, query, builder) -> builder.equal(root.get("name"), name);
	}

	public static Specification<Employee> hasAge(Integer age) {
		return (root, query, builder) -> builder.equal(root.get("age"), age);
	}

	public static Specification<Employee> hasIdsIn(List<Integer> ids) {
		return (root, query, builder) -> builder.in(root.get("id")).in(ids);
	}
}
