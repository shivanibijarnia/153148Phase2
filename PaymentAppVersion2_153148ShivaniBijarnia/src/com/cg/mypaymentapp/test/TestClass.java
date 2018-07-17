package com.cg.mypaymentapp.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;
import com.cg.mypaymentapp.util.DBUtil;
import com.mysql.jdbc.PreparedStatement;

public class TestClass {

	WalletService services;

	@Before
	public void initData() {
		/* Map<String,Customer> data= new HashMap<String, Customer>(); */
		services = new WalletServiceImpl();
		
	}

	@Test
	public void testCreateAccountPassed() {
		services.createAccount("Amit", "9900112212", new BigDecimal(9000));
		services.createAccount("Ajay", "9963242422", new BigDecimal(6000));
		services.createAccount("Yogini", "9922950519", new BigDecimal(7000));

		Customer customer = services.createAccount("Shivani", "9929577596", new BigDecimal(0));
		assertNotNull(customer);
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccountFailed() {
		Customer customer = services.createAccount(null, null, null);

	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccountFailed1() {
		Customer customer = services.createAccount("Shivani", "9929577597", null);

	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccountFailed2() {
		Customer customer = services.createAccount("Shivani", null, new BigDecimal(0));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccountFailed3() {
		Customer customer = services.createAccount(null, "9929577597", new BigDecimal(0));

	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccountFailed4() {
		Customer customer = services.createAccount("Shivani", "992957759", new BigDecimal(0));

	}

	@Test(expected = InsufficientBalanceException.class)
	public void testWithdraw() {
		// Customer customer=services.createAccount("Shivani", "9929577597", new
		// BigDecimal(500));
		services.withdrawAmount("9922950519", new BigDecimal(8000));

	}

	@Test
	public void testWithdraw1() {
		// Customer customer=services.createAccount("Shivani", "9929577597", new
		// BigDecimal(500));
		services.withdrawAmount("9922950519", new BigDecimal(200));
	}

	@Test
	public void testShowBalance() {
		// Customer customer=services.createAccount("Shivani", "9929577597", new
		// BigDecimal(500));
		services.showBalance("9922950519");
	}

	@Test(expected = InvalidInputException.class)
	public void testDeposit() {
		services.depositAmount("9929577597", new BigDecimal(21000));

	}

	@Test
	public void testDeposit1() {
		Customer customer = services.depositAmount("9900112212", new BigDecimal(1000));
		assertEquals(new BigDecimal(10000), customer.getWallet().getBalance());

	}

	@Test
	public void testWithdraw2() {
		Customer customer = services.withdrawAmount("9900112212", new BigDecimal(1000));
		assertEquals(new BigDecimal(9000), customer.getWallet().getBalance());

	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer() {
		services.fundTransfer("9929574436", "9768766349", new BigDecimal(1000));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer1() {
		services.fundTransfer("9922950519", "9768766349", new BigDecimal(1000));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer2() {
		services.fundTransfer("9922950519", "9922950519", new BigDecimal(1000));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testFundTransfer3() {
		services.fundTransfer("9922950519", "9900112212", new BigDecimal(10000));
	}

	@Test
	public void testFundTransfer4() {
		services.fundTransfer("9922950519", "9900112212", new BigDecimal(1000));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer5() {
		services.fundTransfer(null, "9900112212", new BigDecimal(1000));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer6() {
		services.fundTransfer("9900112212", null, new BigDecimal(1000));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer7() {
		services.fundTransfer("9900112212", "9922950519", null);
	}

	@AfterClass
	public static void clean() {
		try (Connection con = DBUtil.getConnection()) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("truncate table customer");
		} catch (Exception e) {

		}
	}
}
