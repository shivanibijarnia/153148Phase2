
package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {

	private WalletRepo repo;

	public WalletServiceImpl() {
		repo = new WalletRepoImpl();
	}

	/*
	 * public WalletServiceImpl(Map<String, Customer> data) { repo = new
	 * WalletRepoImpl(data); }
	 */
	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) {

		if ((name == null) || (mobileNo == null) || (amount == null)) {
			throw new InvalidInputException("None of the entries can be empty");
		}

		if (!validateMobile(mobileNo))
			throw new InvalidInputException("Invalid Mobile number");
		Customer customer = new Customer(name, mobileNo, new Wallet(amount));
		boolean result = repo.save(customer);
		if (!result) {
			throw new InvalidInputException("Mobile no already exists");
		}

		return customer;

	}

	public Customer showBalance(String mobileNo) {
		Customer customer = repo.findOne(mobileNo);
		if (customer != null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");

	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {

		if ((sourceMobileNo == null) || (targetMobileNo == null) || amount == null) {
			throw new InvalidInputException("None of the entries can be empty");
		}
		if (sourceMobileNo.equals(targetMobileNo)) {
			throw new InvalidInputException("Both mobile numbers cannot be same");
		}
		Customer source = withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		return source;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {

		Customer customer = repo.findOne(mobileNo);
		if (customer == null) {
			throw new InvalidInputException("Mobile number does not exist");
		}
		Wallet wallet = customer.getWallet();
		BigDecimal amt = wallet.getBalance();
		BigDecimal total = amt.add(amount);
		customer.setWallet(new Wallet(total));
		repo.update(customer);
		return customer;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {

		Customer customer = repo.findOne(mobileNo);
		if (customer == null) {
			throw new InvalidInputException("Mobile number does not exist");
		}
		Wallet wallet = customer.getWallet();
		BigDecimal amt = wallet.getBalance();

		if (amount.compareTo(amt) != 1) {
			BigDecimal total = amt.subtract(amount);
			customer.setWallet(new Wallet(total));
		} else {
			throw new InsufficientBalanceException("You do not have sufficient balance");
		}

		repo.update(customer);
		return customer;

	}

	public boolean validateMobile(String mobileNo) {
		String pattern = "[7-9][0-9]{9}";
		if (mobileNo.matches(pattern)) {
			return true;
		} else {
			return false;
		}

	}
}
