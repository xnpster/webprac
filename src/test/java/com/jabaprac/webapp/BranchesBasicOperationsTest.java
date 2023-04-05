package com.jabaprac.webapp;

import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Accounts;
import com.jabaprac.webapp.dbobjects.Branches;
import com.jabaprac.webapp.dbobjects.History;
import com.jabaprac.webapp.pageconf.AccountConfiguration;
import com.jabaprac.webapp.pageconf.BranchConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;

import static com.jabaprac.webapp.AppTest.getAllCities;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(locations={"classpath:applicationContextMVC.xml"})
public class BranchesBasicOperationsTest {
    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;

    @Test
    public void testCities() {
        assertEquals(new HashSet<>(bank.departureCities()), getAllCities());
    }

    @Test
    public void saveReadRemove() {
        BranchConfiguration conf = new BranchConfiguration();

        Branches created = bank.persistBranch(conf);

        assertNotNull(created);

        Branches found = bank.branchById(created.getId());

        assertEquals(created, found);

        String newName = "another name";

        conf.setName(newName);
        bank.updateBranch(created.getId(), conf);

        found = bank.branchById(created.getId());

        assertEquals(newName, found.getName());

        bank.removeBranch(created);
        found = bank.branchById(created.getId());

        assertNull(found);
    }

}
