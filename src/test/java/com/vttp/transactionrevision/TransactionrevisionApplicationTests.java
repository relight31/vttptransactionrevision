package com.vttp.transactionrevision;

import com.vttp.transactionrevision.repositories.AccountRepo;
import com.vttp.transactionrevision.services.AccountService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionrevisionApplicationTests {
	@Autowired
	JdbcTemplate template;

	@Autowired
	AccountRepo repository;

	@Autowired
	AccountService service;

	@BeforeEach
	void loadTestData() {
		template.update("insert into account (acct_id, balance) values (?, ?)", "john", 6.8);
		template.update("insert into account (acct_id, balance) values (?, ?)", "mary", 6.8);
	}

	@AfterEach
	void unloadTestData() {
		template.update("delete from account where acct_id = ?", "john");
		template.update("delete from account where acct_id = ?", "mary");
	}

	@Test
	void contextLoads() {
	}

	@Test
	void transferShouldPass() {
		service.transfer("john", "mary", 6.0);
		assertEquals(12.8, repository.getBalance("john"));
		assertEquals(0.8, repository.getBalance("mary"));
	}

	@Test
	void transferShouldFail() {
		assertThrows(IllegalArgumentException.class,
				() -> service.transfer("john", "mary", 8.0));
		assertEquals(6.8, repository.getBalance("john"));
		assertEquals(6.8, repository.getBalance("mary"));
	}
}
