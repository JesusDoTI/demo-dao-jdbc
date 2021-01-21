package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class ProgramDepartment {
	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("===  TEST 1: Department insert ===\n");
		Department dp =  new Department(null, "Toys");
		departmentDao.insert(dp);
		System.out.println("Operation successfully completed! new ID: " + dp.getId());
	}
}
