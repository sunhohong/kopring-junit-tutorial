package com.artieyoe.tutorials.springboot.thenewboston.controller

import com.artieyoe.tutorials.springboot.thenewboston.model.Bank
import com.artieyoe.tutorials.springboot.thenewboston.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(private var service: BankService) {

    @GetMapping
    fun getBanks(): Collection<Bank> {
        return service.getBanks()
    }
}