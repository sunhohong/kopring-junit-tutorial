package com.artieyoe.tutorials.springboot.thenewboston.service

import com.artieyoe.tutorials.springboot.thenewboston.datasource.BankDataSource
import com.artieyoe.tutorials.springboot.thenewboston.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }

    fun getBank(accountNumber: String): Bank {
        return dataSource.retrieveBank(accountNumber)
    }

    fun addBank(bank: Bank): Bank {
        return dataSource.createBank(bank)
    }

    fun updateBank(bank: Bank): Bank {
        return dataSource.updateBank(bank)
    }

    fun deleteBank(accountNumber: String): Unit {
        dataSource.deleteBank(accountNumber)
    }

}
