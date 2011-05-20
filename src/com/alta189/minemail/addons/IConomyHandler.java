package com.alta189.minemail.addons;


import org.bukkit.entity.Player;

import com.iConomy.*;
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;

public class IConomyHandler {


	public Boolean hasAccount(Player player) {
		if(iConomy.hasAccount(player.getName())) {
	        // yes an account exists
			return true;
	    } else {
	        // no account
	    	return false;
	    }
	}
	
	public Boolean hasValidAccount(Player player) {
		Account account = iConomy.getAccount(player.getName());

	    if(account != null) {
	        // Yes the account is valid
	    	return true;
	    } else {
	    	// No the account is not valid
	    	return false;
	    }
	}
	
	public Holdings getHoldings(String accountName) {
		Holdings balance = iConomy.getAccount(accountName).getHoldings();
		return balance;
	}
	
	public Boolean hasEnough(Double amount, Holdings balance) {
		return balance.hasEnough(amount);
	}
	
	public String getBalance(Player player) {
		String balance = null;
			
		if (this.hasAccount(player) && this.hasValidAccount(player)) {
			Double amount = this.getHoldings(player.getName()).balance();
			balance = iConomy.format(amount);
		}
		
		return balance;
	}
	
	public void subtract(Double amount, Holdings balance) {
		balance.subtract(amount);
	}
	
	public void multiply(Double amount, Holdings balance) {
		balance.multiply(amount);
	}
	
	public void add(Double amount, Holdings balance) {
		balance.add(amount);
	}
	
	public void divide(Double amount, Holdings balance) {
		balance.divide(amount);
	}
	
}
