package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Department dp = new Department(2, null);
		System.out.println("===  TEST 1: Seller findById ===\n");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n===  TEST 2: Seller findByDepartment ===\n");
		List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));
		sellerList.forEach(System.out::println);
		
		System.out.println("\n===  TEST 3: Seller findAll ===\n");
		sellerList = sellerDao.findAll();
		sellerList.forEach(System.out::println);
		
		System.out.println("\n===  TEST 4: Seller insert ===\n");
		Seller newSeller = new Seller(null, "Jorge", "jorge@email", new Date(), 4000.00, dp);
		sellerDao.insert(newSeller);
		System.out.println("Operation successfull! new ID: " + newSeller.getId());
	}

}
