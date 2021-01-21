package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class ProgramDepartment {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		List<Department> departmentList = new ArrayList<>();
		
		System.out.println("===  TEST 1: Department findById ===\n");
		Department dp = departmentDao.findById(1);
		System.out.println(dp);
		
		System.out.println("\n===  TEST 2: Seller findAll ===\n");
		departmentList = departmentDao.findAll();
		departmentList.forEach(System.out::println);
		
		System.out.println("\n===  TEST 3: Department insert ===\n");
		dp =  new Department(null, "Toys");
		departmentDao.insert(dp);
		System.out.println("Operation successfully completed! new ID: " + dp.getId());
		
		System.out.println("\n===  TEST 4: Department update ===\n");
		dp.setId(6);
		dp.setName("Home");
		departmentDao.update(dp);
		System.out.println("Updated successfully!");
		
		System.out.println("\n===  TEST 5: Department delete ===\n");
		System.out.print("Enter id for delete test: ");
		departmentDao.deleteById(sc.nextInt());
		System.out.println("Successfully deleted!");
		
		
		
		sc.close();
	}
}
