package com.jabaprac.webapp;
import com.google.common.collect.Lists;
import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Accounts;
import com.jabaprac.webapp.dbobjects.Clients;
import com.jabaprac.webapp.pageconf.ClientType;
import com.jabaprac.webapp.pageconf.ClientView;
import com.jabaprac.webapp.pageconf.FindClientConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;
import java.util.Calendar.*;
import java.sql.Date;

import static com.jabaprac.webapp.AppTest.getAllClients;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(locations={"classpath:applicationContextMVC.xml"})
public class ClientFilterTest {
    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;

    @Test
    public void testGetClientListDefault() {
        FindClientConfiguration conf = new FindClientConfiguration();

        List<ClientView> cls = bank.getClientList(conf);

        Set<Clients> all = getAllClients();

        for(ClientView cl : cls) {
            System.out.println(cl.getClient().getName());
            assertTrue(all.contains(cl.getClient()));
        }
    }

    @Test
    public void testClientListByNo() {
        FindClientConfiguration conf = new FindClientConfiguration();

        Long test = 4L;

        conf.setAllowClientNo(true);
        conf.setClientNo(test);

        Clients cl = bank.clientById(test);

        assertNotNull(cl);

        List<ClientView> res = bank.getClientList(conf);

        assertEquals(res.size(), 1);

        assertEquals(res.get(0).getClient(), cl);
    }

    @Test
    public void testClientListByType() {
        FindClientConfiguration conf = new FindClientConfiguration();

        boolean is_phy = false;

        conf.setType(is_phy ? ClientType.ONLY_PHYS.getValue() : ClientType.ONLY_UR.getValue());

        Set<Clients> all = getAllClients();
        List<ClientView> res = bank.getClientList(conf);

        for(ClientView cl : res) {
            assertEquals(cl.getClient().isIs_phy(), is_phy);
            assertTrue(all.remove(cl.getClient()));
        }

        for(Clients cl : all) {
            assertEquals(cl.isIs_phy(), !is_phy);
        }
    }

    @Test
    public void testClientListBySubstring() {

        LinkedList<String> substrings = new LinkedList<>();
        substrings.add("none");
        substrings.add("va");
        substrings.add("Skotoboynya");
        substrings.add("\"");

        for(String substring : substrings) {
            FindClientConfiguration conf = new FindClientConfiguration();

            conf.setAllowNameSubstring(true);
            conf.setNameSubstring(substring);

            Set<Clients> all = getAllClients();
            List<ClientView> res = bank.getClientList(conf);

            for(ClientView cl : res) {
                System.out.println(cl.getClient().getName());
                assertTrue(cl.getClient().getName().contains(substring));
                assertTrue(all.remove(cl.getClient()));
            }

            for(Clients cl : all) {
                System.out.println(cl.getName());
                assertFalse(cl.getName().contains(substring));
            }
        }
    }

    @Test
    public void testClientListByAccountType() {
        LinkedList<TreeSet<Long>> types = new LinkedList<>();
        types.add( new TreeSet<>(Arrays.asList()));
        types.add( new TreeSet<>(Arrays.asList(1L, 2L)));
        types.add( new TreeSet<>(Arrays.asList(1L, 2L, 3L)));
        types.add( new TreeSet<>(Arrays.asList(1L, 3L)));
        types.add( new TreeSet<>(Arrays.asList(5L, 2L)));
        types.add( new TreeSet<>(Arrays.asList(1L, 2L, 3L, 4L, 5L)));


        for(TreeSet<Long> current : types) {
            FindClientConfiguration conf = new FindClientConfiguration();

            conf.setAllowAccountTypesId(true);
            conf.setAccountTypesId(Lists.newArrayList(current));

            Set<Clients> all = getAllClients();
            List<ClientView> res = bank.getClientList(conf);

            for(ClientView cl : res) {
                System.out.println(cl.getClient().getName());

                List<Accounts> accs = bank.accountsByClient(cl.getClient());

                boolean found = false;
                for(Accounts acc : accs) {
                    if(current.contains(acc.getType().getId())) {
                        found = true;
                        break;
                    }
                }


                assertTrue(found);
                assertTrue(all.remove(cl.getClient()));
            }

            for(Clients cl : all) {
                System.out.println(cl.getName());

                List<Accounts> accs = bank.accountsByClient(cl);

                boolean found = false;
                for(Accounts acc : accs) {
                    if(current.contains(acc.getType().getId())) {
                        found = true;
                        break;
                    }
                }
                assertFalse(found);
            }
        }
    }

    @Test
    public void testClientListByAccountRange() {
        LinkedList<java.util.Map.Entry<Date, Date>> pairsOfDate = new LinkedList<>();
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, null));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(new Date(2022-1900, Calendar.OCTOBER, 1), null));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, new Date(2022-1900, Calendar.OCTOBER, 1)));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(new Date(2022-1900, Calendar.OCTOBER, 17), new Date(2023-1900, Calendar.FEBRUARY, 20)));
        pairsOfDate.add(new java.util.AbstractMap.SimpleEntry<>(null, new Date(2023-1900, Calendar.OCTOBER, 1)));

        for(java.util.Map.Entry<Date, Date> current : pairsOfDate) {
            FindClientConfiguration conf = new FindClientConfiguration();

            Date open = current.getKey();
            Date close = current.getValue();

            if(open != null || close != null) {
                conf.setAllowDateRange(true);
                conf.setStartDate(open);
                conf.setEndDate(close);
            }

            Set<Clients> all = getAllClients();
            List<ClientView> res = bank.getClientList(conf);

            for(ClientView cl : res) {
                System.out.println(cl.getClient().getName());

                List<Accounts> accs = bank.accountsByClient(cl.getClient());

                boolean found = false;
                for(Accounts acc : accs) {
                    boolean left = open == null, right = close == null;

                    if(!left)
                        left = acc.getClose_date() == null || acc.getClose_date().after(open);

                    if(!right)
                        right = acc.getOpen_date() != null || acc.getOpen_date().before(close);

                    if(right && left) {
                        found = true;
                        break;
                    }
                }


                assertTrue(found);
                assertTrue(all.remove(cl.getClient()));
            }

            for(Clients cl : all) {
                System.out.println(cl.getName());

                List<Accounts> accs = bank.accountsByClient(cl);

                boolean found = false;
                for(Accounts acc : accs) {
                    boolean left = open == null, right = close == null;

                    if(!left)
                        left = acc.getClose_date() == null || acc.getClose_date().after(open);

                    if(!right)
                        right = acc.getOpen_date() != null || acc.getOpen_date().before(close);

                    if(right && left) {
                        found = true;
                        break;
                    }
                }


                assertFalse(found);
            }
        }
    }

}
