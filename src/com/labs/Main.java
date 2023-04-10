package com.labs;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {

		// Q1 :: Find out the sum of salary of all employees.
		List<Employee> emp = EmployeeRepository.getEmployees();
		List<Department> dept = EmployeeRepository.getDepartments();
		double sum = emp.stream().collect(Collectors.summingDouble(e -> e.getSalary()));
		System.out.print("total salary is " + sum);
		System.out.println("----------Q1 : sum of salary of all employees -----------");
		System.out.println("total salary is " + sum);

		// Q2 :: List out department names and count of employees in each department.
		System.out.println();
		System.out.println("-----------Q2 : department names and count of employees in each department.-----------");
		Map<String, Long> mp = emp.stream().filter(e -> e.getDepartment() != null).collect(
				Collectors.groupingBy(e -> e.getDepartment().getDepartmentName(), TreeMap::new, Collectors.counting()));
		mp.forEach((department, count) -> System.out.println(department + ": " + count));

		// Q3 :: Find out the senior most employee of an organization.
		System.out.println();
		System.out.println("-----------Q3 : Find out the senior most employee of an organization. -----------");
		Optional<Employee> name = emp.stream().filter(e -> e.getHireDate() != null)
				.collect(Collectors.minBy(Comparator.comparing(e -> e.getHireDate())));
		System.out.print(name.get().getFirstName() + " " + name.get().getLastName());

		// Q4 :: List employee name and duration of their service in months and days.
		System.out.println();
		System.out.println("-----------Q4 : employee name and duration of their service in months and days-----------");
		List<String> results = emp.stream()
				.map(e -> String.format("%s : %d months, %d days", e.getFirstName(),
						Period.between(e.getHireDate(), LocalDate.now()).getYears() * 12
								+ Period.between(e.getHireDate(), LocalDate.now()).getMonths(),
						Period.between(e.getHireDate(), LocalDate.now()).getDays()))
				.collect(Collectors.toList());

		results.forEach(System.out::println);

		// Q5 :: Find out employees without department.
		System.out.println();
		System.out.println("-----------Q5 : employees without department-----------");
		List<Employee> res = emp.stream().filter(e -> e.getDepartment() == null).collect(Collectors.toList());
		for (Employee e : res)
			System.out.println(e.getFirstName());

		// Q6
//       List<Department> dep=dept.stream().filter(e->e.).collect(Collectors.toList());
//		List<Employee> resDept = emp.stream().filter(e -> e.getDepartment() == ).collect(Collectors.toList());
		List<Employee> resDept = emp.stream()
	            .filter(e -> dept.stream().filter(g -> g.getDepartmentName()!=null)
	              .noneMatch(f -> e.getDepartment().getDepartmentName().equals(f.getDepartmentName())))
	            .collect(Collectors.toList());
		System.out.println("-----------Q6 :  department without employees-----------");
       for(Employee e:resDept)
    	   System.out.println(e.getDepartment().getDepartmentName());

		// Q7:: Find departments with highest count of employees.
		System.out.println();
		System.out.println("-----------Q7 : departments with highest count of employees-----------");
		Stream<Map<String, Long>> stream = Stream.of(mp);

		long max = mp.values().stream().max(Comparator.naturalOrder()).get();
		String departmentName = mp.entrySet().stream().filter(e -> e.getValue() == max).map(Map.Entry::getKey)
				.collect(Collectors.toList()).get(0);
		System.out.println(departmentName);

	}
}