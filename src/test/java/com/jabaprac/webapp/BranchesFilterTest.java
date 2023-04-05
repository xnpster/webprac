package com.jabaprac.webapp;

import com.google.common.collect.Lists;
import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Accounts;
import com.jabaprac.webapp.dbobjects.Branches;
import com.jabaprac.webapp.dbobjects.Clients;
import com.jabaprac.webapp.dbobjects.History;
import com.jabaprac.webapp.pageconf.ClientView;
import com.jabaprac.webapp.pageconf.FindAccountConfiguration;
import com.jabaprac.webapp.pageconf.FindClientConfiguration;
import com.jabaprac.webapp.pageconf.FindDepartureConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(locations={"classpath:applicationContextMVC.xml"})
public class BranchesFilterTest {
    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;

    @Test
    public void testBranchesListDefault() {
        FindDepartureConfiguration conf = new FindDepartureConfiguration();

        List<Branches> res = bank.getBranchesList(conf);

        assertEquals(res.size(), 7);
        for(Branches branch : res)
            assertNotNull(branch);
    }

    @Test
    public void testBranchesListById() {
        List<Branches> all = bank.getBranchesList(new FindDepartureConfiguration());

        for(Branches branch : all) {
            FindDepartureConfiguration conf = new FindDepartureConfiguration();
            conf.setAllowNum(true);
            conf.setNum(branch.getId());

            List<Branches> found = bank.getBranchesList(conf);
            assertEquals(1, found.size());
            assertEquals(branch, found.stream().findFirst().get());
        }
    }

    @Test
    public void testBranchesListByAccount() {
        List<Accounts> accounts = bank.getAccountList(new FindAccountConfiguration());

        for(Accounts account : accounts) {
            FindDepartureConfiguration conf = new FindDepartureConfiguration();
            conf.setAllowAccount(true);
            conf.setAccount(account.getId());

            List<Branches> found = bank.getBranchesList(conf);

            assertEquals(1, found.size());
            Branches foundBranch = found.stream().findFirst().get();
            assertEquals(account.getBranch(), foundBranch);

            conf.setAllowClient(true);
            conf.setClient(account.getClient().getId());

            found = bank.getBranchesList(conf);

            assertEquals(1, found.size());
            assertEquals(foundBranch, found.stream().findFirst().get());

            conf.setAllowNum(true);
            conf.setNum(account.getBranch().getId());

            found = bank.getBranchesList(conf);

            assertEquals(1, found.size());
            assertEquals(foundBranch, found.stream().findFirst().get());
        }

        FindDepartureConfiguration conf = new FindDepartureConfiguration();
        conf.setAllowAccount(true);
        conf.setAccount(-10000L);

        List<Branches> found = bank.getBranchesList(conf);
        assertEquals(0, found.size());
    }

    @Test
    public void testBranchesListByClient() {
        List<ClientView> clients = bank.getClientList(new FindClientConfiguration());

        for(ClientView client : clients) {
            FindAccountConfiguration accountConf = new FindAccountConfiguration();
            accountConf.setAllowClientNo(true);
            accountConf.setClientNo(client.getClient().getId());

            HashSet<Branches> target = new HashSet<>();

            for(Accounts acc : bank.getAccountList(accountConf)) {
                target.add(acc.getBranch());
            }

            FindDepartureConfiguration conf = new FindDepartureConfiguration();

            conf.setAllowClient(true);
            conf.setClient(client.getClient().getId());

            HashSet<Branches> res = new HashSet<>(bank.getBranchesList(conf));
            assertEquals(target, res);
        }

        FindDepartureConfiguration conf = new FindDepartureConfiguration();
        conf.setAllowClient(true);
        conf.setClient(-1L);

        assertEquals(0, bank.getBranchesList(conf).size());
    }

    @Test
    public void testBranchesListByCities() {
        LinkedList<String> allCities = new LinkedList<>(bank.departureCities());

        LinkedList<Set<String>> testCases = new LinkedList<>();

        Set<String> test;

        test = new HashSet<>();
        test.add(allCities.get(0));
        testCases.add(test);

        test = new HashSet<>();
        test.add(allCities.get(2));
        testCases.add(test);

        test = new HashSet<>();
        test.add(allCities.get(0));
        test.add(allCities.get(1));
        testCases.add(test);

        test = new HashSet<>();
        test.add(allCities.get(0));
        test.add(allCities.get(1));
        test.add(allCities.get(2));
        testCases.add(test);

        test = new HashSet<>();
        test.add(allCities.get(2));
        test.add(allCities.get(4));
        testCases.add(test);

        Set<Branches> allBranches = new HashSet<>(bank.getBranchesList(new FindDepartureConfiguration()));

        for(Set<String> currSet : testCases) {
            FindDepartureConfiguration conf = new FindDepartureConfiguration();

            conf.setAllowCities(true);
            conf.setCities(new TreeSet<>(currSet));

            HashSet<Branches> res = new HashSet<>(bank.getBranchesList(conf));

            HashSet<Branches> target = new HashSet<>(allBranches);
            target.removeIf(e -> !currSet.contains(e.getCity()));

            assertEquals(target, res);
        }
    }

    @Test
    public void verifyTest() {
        assertNull(bank.verifyBranchToClose(null));
    }
}
