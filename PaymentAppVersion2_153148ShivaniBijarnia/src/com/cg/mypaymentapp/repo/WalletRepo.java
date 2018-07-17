package com.cg.mypaymentapp.repo;

import com.cg.mypaymentapp.beans.Customer;

public interface WalletRepo {

	public boolean save(Customer customer); // storing data in collections mobile:key customer object:value

	public Customer findOne(String mobileNo); // retrieving customer object from collections using map

	public boolean update(Customer customer);
}
