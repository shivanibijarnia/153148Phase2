package com.cg.mypaymentapp.service;

import java.math.BigDecimal;

import com.cg.mypaymentapp.beans.Customer;

public interface WalletService {
	public Customer createAccount(String name, String mobileno, BigDecimal amount); // pass double or integer, object of
																					// bigdecimal

	public Customer showBalance(String mobileno);

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount); // return source
																									// object

	public Customer depositAmount(String mobileNo, BigDecimal amount);

	public Customer withdrawAmount(String mobileNo, BigDecimal amount);

}

// 5 positive test cases 15 negative test cases