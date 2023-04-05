package com.jabaprac.webapp;

import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Accounts;
import com.jabaprac.webapp.dbobjects.History;
import com.jabaprac.webapp.pageconf.AccountConfiguration;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(locations={"classpath:applicationContextMVC.xml"})
public class AccountBasicOperationsTest {
    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;

    @Test
    public void saveReadRemove() {
        AccountConfiguration acc = new AccountConfiguration(1L, 1L, 1L, 2342L);

        Accounts created = bank.persistAccount(acc);

        assertNotNull(created);

        Accounts found = bank.accountById(created.getId());

        assertEquals(created, found);

        bank.closeAccount(created);
        found = bank.accountById(created.getId());

        assertNotNull(found.getClose_date());

        bank.removeAccount(created);
        found = bank.accountById(created.getId());

        assertNull(found);
    }

    @Test
    public void verifyTest() {
        assertEquals("Неопределённый счёт", bank.verifyAccountToClose(null));
        Accounts acc = new Accounts();
        acc.setClose_date(bank.currentTime());
        assertEquals("Счёт уже закрыт", bank.verifyAccountToClose(acc));
        acc.setClose_date(null);
        assertNull(bank.verifyAccountToClose(acc));
    }

    @Test
    public void successTransaction() {
        Long sum = 10L;
        AccountConfiguration conf = new AccountConfiguration(1L, 1L, 2L, 2342L);;

        Accounts created = bank.persistAccount(conf);

        assertNotNull(created);

        Long oldBalance = created.getBalance();

        HashSet<History> prevHist = new HashSet<>(bank.historyByAccount(created));

        try {
            assertNull(bank.performTransaction(created, sum));
        } catch (Exception e) {
            fail();
        }

        Accounts found = bank.accountById(created.getId());

        assertNotNull(found);
        assertEquals(oldBalance + sum, found.getBalance());

        HashSet<History> newHist = new HashSet<>(bank.historyByAccount(created));
        assertEquals(prevHist.size() + 1, newHist.size());

        System.out.println(prevHist.toString());
        System.out.println(newHist.toString());

        newHist.removeAll(prevHist);


        assertEquals(1, newHist.size());
        assertEquals(sum, newHist.stream().findFirst().get().getSum());

        bank.removeHistory(newHist.stream().findFirst().get());
        bank.removeAccount(found);
    }

    @Test
    public void failedTransaction() {
        assertEquals("неопределённый счёт", bank.performTransaction(null, 10L));

        assertEquals("неопределённая сумма", bank.performTransaction(new Accounts(), 0L));

        Accounts acc = new Accounts();
        acc.setClose_date(bank.currentTime());

        assertEquals("счёт закрыт", bank.performTransaction(acc, 10L));

        acc.setClose_date(null);
        acc.setType(bank.accountTypeById(1L));


        assertEquals("счёт закрыт для пополнений", bank.performTransaction(acc, 10L));
        assertEquals("счёт закрыт для снятий", bank.performTransaction(acc, -10L));

        acc.setType(bank.accountTypeById(3L));
        acc.setBalance(0L);

        assertEquals("недостаточно средств или выход за пределы кредитного лимита", bank.performTransaction(acc, -10001L));
    }
}
