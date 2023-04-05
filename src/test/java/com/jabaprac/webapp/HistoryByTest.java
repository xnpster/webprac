package com.jabaprac.webapp;

import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Accounts;
import com.jabaprac.webapp.dbobjects.Clients;
import com.jabaprac.webapp.dbobjects.History;
import com.jabaprac.webapp.pageconf.ClientConfiguration;
import com.jabaprac.webapp.pageconf.ClientType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(locations={"classpath:applicationContextMVC.xml"})
public class HistoryByTest {
    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;

    @Test
    public void historyByAccountTest() {
        Accounts acc = bank.accountById(1L);

        List<History> hst = bank.historyByAccount(acc);

        assertEquals(1, hst.size());

        History ent = hst.get(0);

        assertNotNull(ent);
        assertEquals(10324, ent.getSum());
    }

    @Test
    public void historyByClientTest() {
        Clients cli = bank.clientById(1L);

        List<History> hst = bank.historyByClient(cli);

        assertEquals(1, hst.size());

        History ent = hst.get(0);

        assertNotNull(ent);
        assertEquals(10324, ent.getSum());
    }

}
