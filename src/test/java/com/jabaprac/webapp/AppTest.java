package com.jabaprac.webapp;

import java.sql.Date;
import java.util.*;

import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.Account_types;
import com.jabaprac.webapp.dbobjects.Accounts;
import com.jabaprac.webapp.dbobjects.Clients;
import com.jabaprac.webapp.pageconf.ClientType;
import com.jabaprac.webapp.pageconf.ClientView;
import com.jabaprac.webapp.pageconf.FindClientConfiguration;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(locations = {"classpath:applicationContextMVC.xml"})
public class AppTest {
    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void bankNotNull() {
        assertNotNull(bank);
    }

    @Test
    public void testTime() {
        Date d = bank.currentTime();
        assertNotNull(d);

        System.out.println("Local date is " + d);
    }

    static public Set<Clients> getAllClients() {
        HashSet<Clients> cls = new HashSet<>();
        cls.add(new Clients(1L, "Arhipova Svetlana Dmitrievna", "Boulevard Vesnin Brothers, 2G, Moscow", "arhipova@mail.ru", "79395247366", true));
        cls.add(new Clients(2L, "OJSC \"Skotoboynya\"", "Leningradsky prospect, 36с39, Moscow", "company@skotoboynya.ru", "79941605820", false));
        cls.add(new Clients(3L, "Agafonova Marija Alekseevna", "Pryluky street, 21-23A, Saint Petersburg", "agafonova_marija@mail.ru", "79366726517", true));
        cls.add(new Clients(4L, "Fedoseev Trifon Ermolaevich", "Izmailovsky Boulevard, 4k2, Saint Petersburg", "t-f@yandex.ru", "79027493396", true));
        cls.add(new Clients(5L, "Alekseev Agap Egorovich", "Belinsky street, 202, Novosibirsk", "a.a@yandex.ru", "79286058395", true));
        cls.add(new Clients(6L, "Seliverst Jakovlevich Potapov", "Krasina street, 28, Nizhny Novgorod", "j_seliverst@mail.ru", "79501672581", true));
        cls.add(new Clients(7L, "Viktor Gertrudovich Larionov", "Stepan Razin street, 25, Yekaterinburg, Sverdlovsk region", "gv@mail.ru", "79752686136", true));
        cls.add(new Clients(8L, "Frolova Fekla Stanislavovna", "Strelochnikov street, 6, Yekaterinburg, Sverdlovsk region", "feklaf@gmail.com", "79802544417", true));

        return cls;
    }

    static public Set<String> getAllCities() {
        HashSet<String> cities = new HashSet<>();

        cities.add("Moscow");
        cities.add("Saint Petersburg");
        cities.add("Novosibirsk");
        cities.add("Nizhniy Novgorod");
        cities.add("Yekaterinburg");

        return cities;
    }

    @Test
    public void testClientById() {
        Set<Clients> cls = getAllClients();

        for (Clients cl : cls) {
            System.out.println(cl.getName());

            Clients found = bank.clientById(cl.getId());
            assertEquals(found, cl);
        }
    }

    @Test
    public void testAccountsByClient() {
        Clients cl = bank.clientById(1L);
        assertNotNull(cl);

        List<Accounts> accs = bank.accountsByClient(cl);

        assertEquals(accs.size(), 1);

        Accounts acc = accs.get(0);

        assertNotNull(acc);

        assertEquals(acc.getId(), 1L);
        assertEquals(acc.getBranch().getId(), 1L);
        assertEquals(acc.getClient().getId(), 1L);
        assertEquals(acc.getType().getId(), 1L);
        assertEquals(acc.getBalance(), 10324);
        assertEquals(acc.getOpen_date(), new Date(2022 - 1900, 9, 17));
        assertNull(acc.getClose_date());
    }

    @Test
    public void testAccountTypes() {
        List<Account_types> types = bank.getAllAccountTypes();

        assertNotNull(types);

        HashSet<Account_types> targetTypes = new HashSet<>();

        targetTypes.add(new Account_types(1L, "Вклад-1", 0, false, false));
        targetTypes.add(new Account_types(2L, "Вклад-максимум", 0, true, true));
        targetTypes.add(new Account_types(3L, "Кредит-0", -10000, true, true));
        targetTypes.add(new Account_types(4L, "Кредит-1", -20000, true, true));
        targetTypes.add(new Account_types(5L, "Кредит-выгодный", -30000, true, true));

        assertEquals(targetTypes.size(), types.size());

        for (Account_types t : types) {
            System.out.println(t.getName());
            assertTrue(targetTypes.contains(t));
        }
    }

}
