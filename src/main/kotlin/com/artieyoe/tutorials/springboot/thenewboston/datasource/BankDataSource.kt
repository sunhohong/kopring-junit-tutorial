package com.artieyoe.tutorials.springboot.thenewboston.datasource

import com.artieyoe.tutorials.springboot.thenewboston.model.Bank

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
}