package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller sl) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO seller " 
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, sl.getName());
			st.setString(2, sl.getEmail());
			st.setDate(3, new java.sql.Date(sl.getBirthDate().getTime()));
			st.setDouble(4, sl.getBaseSalary());
			st.setInt(5, sl.getDepartment().getId());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					sl.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DBException("Unexpected Error: no rows affected");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Seller sl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " 
							+ "WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dp = instantiateDepartment(rs);

				Seller sl = instantiateSeller(rs, dp);
				return sl;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller sl = new Seller();
		sl.setId(rs.getInt("Id"));
		sl.setName(rs.getString("Name"));
		sl.setEmail(rs.getString("Email"));
		sl.setBaseSalary(rs.getDouble("BaseSalary"));
		sl.setBirthDate(rs.getDate("BirthDate"));
		sl.setDepartment(dp);
		return sl;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("DepName"));
		return dp;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> sellerList = new ArrayList<>();
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " 
							+ "ORDER BY Name");
			rs = st.executeQuery();

			Map<Integer, Department> depMap = new HashMap<>();

			while (rs.next()) {
				Department dep = depMap.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					depMap.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				sellerList.add(seller);
			}
			return sellerList;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department dp) {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> sellerList = new ArrayList<>();
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " 
							+ "WHERE DepartmentId = ? " + "ORDER BY Name");
			st.setInt(1, dp.getId());
			rs = st.executeQuery();

			Map<Integer, Department> depMap = new HashMap<>();

			while (rs.next()) {
				Department dep = depMap.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					depMap.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				sellerList.add(seller);
			}
			return sellerList;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
