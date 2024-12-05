package com.bm.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@Tag(name = "Account Management")
@Slf4j
public class AccountController {

    @Operation(summary = "Connect to account")
    @PostMapping
    public ResponseEntity<String> connects() {
        log.info("Connecting ...");
        return ResponseEntity.ok("Connected!!");
    }

    @Operation(summary = "Disconnect from account")
    @DeleteMapping
    public ResponseEntity<String> disconnects() {
        log.info("Disconnecting ...");
        return ResponseEntity.ok("Disconnected!!");
    }
}
