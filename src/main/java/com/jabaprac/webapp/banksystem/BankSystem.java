package com.jabaprac.webapp.banksystem;

import com.jabaprac.webapp.dbobjects.*;

import com.jabaprac.webapp.pageconf.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Component("bankSystem")
public class BankSystem {
    private final EntityManager em;

    @Autowired
    private BankSystem(@Qualifier("entityManager") EntityManager em) {
        this.em = em;
    }

    public List<Accounts> accountsByClient(Clients client) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Accounts> q = cb.createQuery(Accounts.class);
        Root<Accounts> accounts = q.from(Accounts.class);

        q.select(accounts);
        q.where(cb.equal(accounts.get(Accounts_.client), client.getId()));

        return em.createQuery(q).getResultList();
    }

    public List<Account_types> getAllAccountTypes() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Account_types> q = cb.createQuery(Account_types.class);
        Root<Account_types> types = q.from(Account_types.class);

        q.select(types);

        return em.createQuery(q).getResultList();
    }

    public List<ClientView> getClientList(FindClientConfiguration conf) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Clients> q = cb.createQuery(Clients.class);
        Root<Clients> clients = q.from(Clients.class);

        q.select(clients);
        ClientType clientType = ClientType.get(conf.getType());

        Predicate pred, resultPred = null;

        if(conf.isAllowClientNo() && conf.getClientNo() != null) {
            resultPred = cb.equal(clients.get(Clients_.id), conf.getClientNo());
        }

        if(clientType != ClientType.ALL)
        {
            boolean needIsPhy = clientType == ClientType.ONLY_PHYS;

            pred = cb.equal(clients.get(Clients_.is_phy), cb.literal(needIsPhy));

            resultPred = resultPred == null ? pred : cb.and(resultPred, pred);
        }

        if(resultPred != null)
            q.where(resultPred);
        LinkedList<Clients> res = new LinkedList<>(em.createQuery(q).getResultList());

        if(conf.isAllowNameSubstring() && conf.getNameSubstring() != null)
            res.removeIf(e -> !e.getName().contains(conf.getNameSubstring()));

       boolean checkTypes = conf.isAllowAccountTypesId() && conf.getAccountTypesId() != null;
       boolean checkRange = conf.isAllowDateRange() && conf.getStartDate() != null;

        LinkedList<ClientView> views = new LinkedList<>();

        for(Clients client : res) {
            List<Accounts> accs = accountsByClient(client);

            if(checkTypes || checkRange) {
                boolean typeCond = !checkTypes, rangeCond = !checkRange;
                for (Accounts acc : accs) {
                    if (!typeCond)
                        typeCond = conf.getAccountTypesId().contains(acc.getType().getId());

                    if (!rangeCond)
                        rangeCond = (acc.getClose_date() == null || !conf.getStartDate().after(acc.getClose_date())) &&
                                (conf.getEndDate() == null || !conf.getEndDate().before(acc.getOpen_date()));

                    if (typeCond && rangeCond)
                        break;
                }

                if(!typeCond || !rangeCond)
                    continue;
            }

            int total = accs.size(), active = 0;

            for(Accounts acc : accs)
                if(acc.getClose_date() == null)
                    active++;

            views.add(new ClientView(client, total, active));
        }


        return views;
    }

    public Clients persistClient(ClientConfiguration conf) {
        Clients client = new Clients();
        conf.updateClient(client);

        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();

        return client;
    }

    public Clients clientById(Long id) {
        return em.find(Clients.class, id);
    }

    public Branches branchById(Long id) {
        return em.find(Branches.class, id);
    }

    public Account_types accountTypeById(Long id) {
        return em.find(Account_types.class, id);
    }

    public Accounts accountById(Long id) {
        return em.find(Accounts.class, id);
    }

    public void removeClient(Clients client) {
        em.getTransaction().begin();
        em.remove(client);
        em.getTransaction().commit();
    }

    public void removeAccount(Accounts account) {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);

        account.setClose_date(today);

        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }

    public Clients updateClient(Long id, ClientConfiguration conf) {
        Clients client = clientById(id);
        conf.updateClient(client);

        em.getTransaction().begin();
        em.merge(client);
        em.getTransaction().commit();

        return client;
    }


    public List<History> historyByAccount(Accounts account) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<History> q = cb.createQuery(History.class);
        Root<History> hist = q.from(History.class);

        q.select(hist);
        q.where(cb.equal(hist.get(History_.account), account.getId()));

        return em.createQuery(q).getResultList();
    }
    public List<Accounts> getAccountList(FindAccountConfiguration conf) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Accounts> q = cb.createQuery(Accounts.class);
        Root<Accounts> accounts = q.from(Accounts.class);

        q.select(accounts);
        Predicate pred, resultPred = null;
        if(conf.isAllowAccountNo() && conf.getAccountNo() != null) {
            resultPred = cb.equal(accounts.get(Accounts_.id), conf.getAccountNo());
        }

        if(conf.isAllowClientNo() && conf.getClientNo() != null) {
            pred = cb.equal(accounts.get(Accounts_.client), conf.getClientNo());
            resultPred = resultPred == null ? pred : cb.and(resultPred, pred);
        }

        if(conf.isAllowAccountTypesId() && conf.getAccountTypesId() != null) {
            pred = accounts.get(Accounts_.type).in(conf.getAccountTypesId());
            resultPred = resultPred == null ? pred : cb.and(resultPred, pred);
        }

        boolean checkDepositRange = conf.isAllowDepositDateRange() && conf.getDepositStartDate() != null;
        boolean checkWithdrawRange = conf.isAllowWithdrawDateRange() && conf.getWithdrawStartDate() != null;

        if(resultPred != null)
            q.where(resultPred);
        LinkedList<Accounts> res = new LinkedList<>(em.createQuery(q).getResultList());

        if(!checkDepositRange && !checkWithdrawRange)
            return res;

        LinkedList<Accounts> toReturn = new LinkedList<>();
        for(Accounts account : res) {
            List<History> history = historyByAccount(account);

            boolean depositCond = !checkDepositRange, withdrawCond = !checkWithdrawRange;
            for (History hist : history) {
                if (!depositCond && hist.getSum() > 0)
                    depositCond = hist.getDate().after(conf.getDepositStartDate()) && (conf.getDepositEndDate() == null
                            || hist.getDate().before(conf.getDepositEndDate()));

                if (!withdrawCond && hist.getSum() < 0)
                    withdrawCond = hist.getDate().after(conf.getWithdrawStartDate()) && (conf.getWithdrawEndDate() == null
                            || hist.getDate().before(conf.getWithdrawEndDate()));
            }

            if(!depositCond || !withdrawCond)
                continue;

            toReturn.push(account);
        }

        return toReturn;
    }

    public Accounts persistAccount(AccountConfiguration conf) {
        Accounts account = new Accounts();
        account.setBalance(conf.getStartSum());
        account.setBranch(branchById(conf.getDepNo()));
        account.setClient(clientById(conf.getClientNo()));
        account.setType(accountTypeById(conf.getAccountType()));

        long millis = System.currentTimeMillis();
        Date today = new Date(millis);

        account.setOpen_date(today);
        account.setClose_date(null);

        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        return account;
    }

    public String verifyAccountToClose(Accounts account) {
        if(account == null)
            return "Неопределённый счёт";

        if(account.getClose_date() != null)
            return "Счёт уже закрыт";

        return null;
    }
}
