package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("===  TEST 1: Seller findById ===\n");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n===  TEST 2: Seller findByDepartment ===\n");
		List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));
		sellerList.forEach(System.out::println);
		
		System.out.println("\n===  TEST 3: Seller findAll ===\n");
		sellerList = sellerDao.findAll();
		sellerList.forEach(System.out::println);
	}

}
