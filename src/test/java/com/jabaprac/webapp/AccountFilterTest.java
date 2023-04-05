package com.jabaprac.webapp;

import com.google.common.collect.Lists;
import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Accounts;
import com.jabaprac.webapp.dbobjects.Clients;
import com.jabaprac.webapp.dbobjects.History;
import com.jabaprac.webapp.pageconf.AccountConfiguration;
import com.jabaprac.webapp.pageconf.ClientView;
import com.jabaprac.webapp.pageconf.FindAccountConfiguration;
import com.jabaprac.webapp.pageconf.FindClientConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(locations={"classpath:applicationContextMVC.xml"})
public class AccountFilterTest {
    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;

    @Test
    public void testAccountListDefault() {
        FindAccountConfiguration conf = new FindAccountConfiguration();

        List<Accounts> res = bank.getAccountList(conf);

        assertEquals(res.size(), 8);
        for(Accounts acc : res)
            assertNotNull(acc);
    }

    @Test
    public void testAccountListById() {
        List<Accounts> allAccounts = bank.getAccountList(new FindAccountConfiguration());

        for(Accounts currentAccount : allAccounts) {
            Long id = currentAccount.getId();

            FindAccountConfiguration conf = new FindAccountConfiguration();

            conf.setAllowAccountNo(true);
            conf.setAccountNo(id);

            List<Accounts> res = bank.getAccountList(conf);

            assertEquals(res.size(), 1);
            assertEquals(res.get(0), currentAccount);
        }
    }

    @Test
    public void testAccountListByClient() {
        List<Accounts> allAccounts = bank.getAccountList(new FindAccountConfiguration());
        List<ClientView> allClients = bank.getClientList(new FindClientConfiguration());


        for(ClientView clientView : allClients) {
            Clients client = clientView.getClient();

            HashSet<Accounts> accForClient = new HashSet<>();
            for(Accounts account : allAccounts)
                if(account.getClient().getId().equals(client.getId()))
                    accForClient.add(account);


            Long id = client.getId();

            FindAccountConfiguration conf = new FindAccountConfiguration();

            conf.setAllowClientNo(true);
            conf.setClientNo(id);

            List<Accounts> res = bank.getAccountList(conf);

            assertEquals(accForClient.size(), res.size());

            for(Accounts foundAccount : res) {
                assertTrue(accForClient.remove(foundAccount));
            }

            assertEquals(0, accForClient.size());
        }
    }
    @Test
    public void testAccountListByType() {
        LinkedList<TreeSet<Long>> types = new LinkedList<>();
        types.add( new TreeSet<>(Arrays.asList()));
        types.add( new TreeSet<>(Arrays.asList(1L, 2L)));
        types.add( new TreeSet<>(Arrays.asList(1L, 2L, 3L)));
        types.add( new TreeSet<>(Arrays.asList(1L, 3L)));
        types.add( new TreeSet<>(Arrays.asList(5L, 2L)));
        types.add( new TreeSet<>(Arrays.asList(1L, 2L, 3L, 4L, 5L)));

        for(TreeSet<Long> typeSet : types) {

            FindAccountConfiguration conf = new FindAccountConfiguration();

            conf.setAllowAccountTypesId(true);
            conf.setAccountTypesId(Lists.newArrayList(typeSet));

            List<Accounts> res = bank.getAccountList(conf);

            for(Accounts foundAccount : res) {
                assertTrue(typeSet.contains(foundAccount.getType().getId()));
            }
        }
    }

    @Test
    public void testAccountListByDepositRange() {
        LinkedList<java.util.Map.Entry<java.sql.Date, java.sql.Date>> pairsOfDate = new LinkedList<>();
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, null));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(new java.sql.Date(2022-1900, Calendar.OCTOBER, 1), null));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, new java.sql.Date(2022-1900, Calendar.OCTOBER, 1)));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(new java.sql.Date(2022-1900, Calendar.OCTOBER, 17), new java.sql.Date(2023-1900, Calendar.FEBRUARY, 20)));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, new Date(2023-1900, Calendar.OCTOBER, 1)));


        for(java.util.Map.Entry<Date, Date> current : pairsOfDate) {

            FindAccountConfiguration conf = new FindAccountConfiguration();

            conf.setAllowDepositDateRange(true);
            conf.setDepositStartDate(current.getKey());
            conf.setDepositEndDate(current.getValue());

            List<Accounts> res = bank.getAccountList(conf);

            for(Accounts foundAccount : res) {
                List<History> hst = bank.historyByAccount(foundAccount);

                boolean found = false;

                for(History currHst : hst) {
                    if(currHst.getSum() > 0) {
                        boolean left = current.getKey() == null || current.getKey().before(currHst.getDate());
                        boolean right = current.getValue() == null || current.getValue().after(currHst.getDate());

                        if (left && right) {
                            found = true;
                            break;
                        }
                    }
                }

                assertTrue(found);
            }
        }
    }

    @Test
    public void testAccountListByWithdrawRange() {
        LinkedList<java.util.Map.Entry<java.sql.Date, java.sql.Date>> pairsOfDate = new LinkedList<>();
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, null));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(new java.sql.Date(2022-1900, Calendar.OCTOBER, 1), null));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, new java.sql.Date(2022-1900, Calendar.OCTOBER, 1)));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(new java.sql.Date(2022-1900, Calendar.OCTOBER, 17), new java.sql.Date(2023-1900, Calendar.FEBRUARY, 20)));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, new Date(2023-1900, Calendar.OCTOBER, 1)));


        for(java.util.Map.Entry<Date, Date> current : pairsOfDate) {

            FindAccountConfiguration conf = new FindAccountConfiguration();

            conf.setAllowWithdrawDateRange(true);
            conf.setWithdrawStartDate(current.getKey());
            conf.setWithdrawEndDate(current.getValue());

            List<Accounts> res = bank.getAccountList(conf);

            for(Accounts foundAccount : res) {
                List<History> hst = bank.historyByAccount(foundAccount);

                boolean found = false;

                for(History currHst : hst) {
                    if(currHst.getSum() < 0) {
                        boolean left = current.getKey() == null || current.getKey().before(currHst.getDate());
                        boolean right = current.getValue() == null || current.getValue().after(currHst.getDate());

                        if (left && right) {
                            found = true;
                            break;
                        }
                    }
                }

                assertTrue(found);
            }
        }
    }

}
