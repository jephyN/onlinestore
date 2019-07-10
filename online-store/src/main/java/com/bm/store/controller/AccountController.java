package com.bm.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/account")
@Api(value = "Account Management")
@Slf4j
public class AccountController {

	@ApiOperation(value = "Connect to account")
	@PostMapping
	public ResponseEntity<String> connects() {
		log.info("Connecting ...");
		return ResponseEntity.ok("Connected!!");
	}

	@ApiOperation(value = "Disconnect from account")
	@DeleteMapping
	public ResponseEntity<String> disconnects() {
		log.info("Disconnecting ...");
		return ResponseEntity.ok("Disonnected!!");
	}
}
