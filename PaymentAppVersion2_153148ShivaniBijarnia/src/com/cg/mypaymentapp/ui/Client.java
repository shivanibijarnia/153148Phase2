package com.cg.mypaymentapp.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {

	public static void main(String[] args) {

		System.out.println("****Welcome to E-Wallet****");

		WalletService services;
		{

			services = new WalletServiceImpl();

		}

		Scanner sc = new Scanner(System.in);
		String ans;
		int no;

		loop: do {

			System.out.println("1. Create Account");
			System.out.println("2. Show Balance");
			System.out.println("3. Fund Transfer");
			System.out.println("4. Deposit Amount");
			System.out.println("5. Withdraw Amount");
			System.out.println("6. Exit");

			System.out.print("Enter choice:");
			no = sc.nextInt();
			String name = null, mobileNo = null;
			int amt = 0;
			Customer customer = null;
			switch (no) {

			case 1:
				System.out.println("Enter your Name:");
				sc.nextLine();
				name = sc.nextLine();
				System.out.println("Enter your Mobile Number:");
				mobileNo = sc.nextLine();
				System.out.println("Enter your Wallet Opening Amount:");
				amt = sc.nextInt();
				sc.nextLine();

				try {
					customer = services.createAccount(name, mobileNo, new BigDecimal(amt));
					System.out.println("Your account is successfully created! Kudos!");
					System.out.println("Details of Your Created account:" + customer.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println("" + e.getMessage());
				}

				break;

			case 2:
				System.out.println("Enter Mobile Number:");
				sc.nextLine();
				mobileNo = sc.nextLine();
				try {
					customer = services.showBalance(mobileNo);
					Wallet wallet = customer.getWallet();
					BigDecimal amount = wallet.getBalance();
					System.out.println("Balance:" + amount);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.err.println("" + e1.getMessage());
				}
				break;

			case 3:
				System.out.println("Enter Your Mobile Number:");
				sc.nextLine();
				String source = sc.nextLine();
				System.out.println("Enter Mobile Number in which you want to transfer:");
				/* sc.nextLine(); */
				String target = sc.nextLine();
				System.out.println("Enter Amount:");
				amt = sc.nextInt();
				try {
					customer = services.fundTransfer(source, target, new BigDecimal(amt));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println("" + e.getMessage());
				}
				break;

			case 4:
				System.out.println("Enter Mobile Number:");
				sc.nextLine();
				mobileNo = sc.nextLine();
				System.out.println("Enter Amount:");
				amt = sc.nextInt();
				try {
					customer = services.depositAmount(mobileNo, new BigDecimal(amt));
					System.out.println("Deposit successful");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println("" + e.getMessage());
				}
				break;

			case 5:
				System.out.println("Enter Mobile Number:");
				sc.nextLine();
				mobileNo = sc.nextLine();
				System.out.println("Enter Amount:");
				amt = sc.nextInt();
				try {
					customer = services.withdrawAmount(mobileNo, new BigDecimal(amt));
					System.out.println("Withdraw successful");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println("" + e.getMessage());
				}
				break;

			case 6:
				break loop;

			default:
				System.out.println("Choose from Available Choices");
				break;
			}

			System.out.println("Do you want to continue?(Yes/No):");
			ans = sc.next();

		} while (ans.equals("yes") || ans.equals("y") || ans.equals("Yes"));

		System.out.println("Thank You for taking the services");

	}
}
