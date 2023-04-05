package com.jabaprac.webapp;

import com.google.common.collect.Lists;
import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Accounts;
import com.jabaprac.webapp.dbobjects.Clients;
import com.jabaprac.webapp.pageconf.ClientConfiguration;
import com.jabaprac.webapp.pageconf.ClientType;
import com.jabaprac.webapp.pageconf.ClientView;
import com.jabaprac.webapp.pageconf.FindClientConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.util.*;

import static com.jabaprac.webapp.AppTest.getAllClients;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(locations={"classpath:applicationContextMVC.xml"})
public class ClientBasicOperationsTest {
    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;

    @Test
    public void saveReadRemove() {
        ClientConfiguration cli = new ClientConfiguration(ClientType.ONLY_PHYS.getValue(), "Иванов Иван Иванович", "79999999999", "example@email.com", "Адрес");

        Clients created = bank.persistClient(cli);

        assertNotNull(created);

        Clients found = bank.clientById(created.getId());

        assertEquals(found, created);

        String newEmail = "anotheremail@adress.com";
        cli.setEmail(newEmail);

        bank.updateClient(found.getId(), cli);

        found = bank.clientById(created.getId());
        assertEquals(found.getEmail(), newEmail);

        bank.removeClient(found);
        found = bank.clientById(created.getId());

        assertNull(found);
    }

}
