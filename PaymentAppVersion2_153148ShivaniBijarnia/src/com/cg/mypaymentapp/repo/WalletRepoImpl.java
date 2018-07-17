package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.util.DBUtil;

public class WalletRepoImpl implements WalletRepo {

	private Connection con;

	public boolean save(Customer customer) {
		try (Connection con = DBUtil.getConnection()) {
			String name = customer.getName();
			String mobile = customer.getMobileNo();
			BigDecimal balance = customer.getWallet().getBalance();

			PreparedStatement pstmt = con.prepareStatement("insert into customer values(?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, mobile);
			pstmt.setBigDecimal(3, balance);
			pstmt.executeUpdate();

		} catch (Exception e) {
			return false;
		}

		return true;

	}

	public Customer findOne(String mobileNo) {
		Customer customer = null;
		try (Connection con = DBUtil.getConnection()) {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from customer where mobile='" + mobileNo + "'");
			if (rs.first()) {
				customer = new Customer();
				customer.setName(rs.getString(1));
				customer.setMobileNo(rs.getString(2));
				customer.setWallet(new Wallet(rs.getBigDecimal(3)));

			}

		} catch (Exception e) {
			return null;
		}

		// System.out.println("Customer:"+customer.toString());
		return customer;

	}

	@Override
	public boolean update(Customer customer) {
		try (Connection con = DBUtil.getConnection()) {
			String mobile = customer.getMobileNo();
			BigDecimal balance = customer.getWallet().getBalance();

			PreparedStatement pstmt = con.prepareStatement("update customer set balance=? where mobile=?");
			pstmt.setBigDecimal(1, balance);
			pstmt.setString(2, mobile);
			pstmt.executeUpdate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
