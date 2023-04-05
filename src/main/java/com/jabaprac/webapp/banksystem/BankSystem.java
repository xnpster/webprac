package com.jabaprac.webapp.banksystem;

import com.jabaprac.webapp.dbobjects.*;

import com.jabaprac.webapp.pageconf.*;
import jakarta.persistence.EntityManager;
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
import java.util.TreeSet;

@Component("bankSystem")
public class BankSystem {
    private final EntityManager em;

    public Date currentTime() {
        long millis = System.currentTimeMillis();
        return new Date(millis);
    }

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

    public Branches persistBranch(BranchConfiguration conf) {
        Branches branch = new Branches();
        branch.setName(conf.getName());
        branch.setCity(conf.getCity());
        branch.setAddr(conf.getAddress());

        em.getTransaction().begin();
        em.persist(branch);
        em.getTransaction().commit();

        return branch;
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

    public void closeAccount(Accounts account) {
        account.setClose_date(currentTime());

        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }
    public void removeAccount(Accounts account) {
        em.getTransaction().begin();
        em.remove(account);
        em.getTransaction().commit();
    }

    public void removeHistory(History hist) {
        em.getTransaction().begin();
        em.remove(hist);
        em.getTransaction().commit();
    }

    public void removeBranch(Branches branch) {
        em.getTransaction().begin();
        em.remove(branch);
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

    public List<History> historyByClient(Clients client) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<History> q = cb.createQuery(History.class);
        Root<History> hist = q.from(History.class);

        q.select(hist);
        q.where(cb.equal(hist.get(History_.account).get(Accounts_.client), client.getId()));

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

        boolean checkDepositRange = conf.isAllowDepositDateRange();
        boolean checkWithdrawRange = conf.isAllowWithdrawDateRange();

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
                    depositCond = (conf.getDepositStartDate() == null || hist.getDate().after(conf.getDepositStartDate())) && (conf.getDepositEndDate() == null
                            || hist.getDate().before(conf.getDepositEndDate()));

                if (!withdrawCond && hist.getSum() < 0)
                    withdrawCond = (conf.getWithdrawStartDate() == null || hist.getDate().after(conf.getWithdrawStartDate())) && (conf.getWithdrawEndDate() == null
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

        account.setOpen_date(currentTime());
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

    public String performTransaction(Accounts account, Long sum) {
        if(account == null)
            return "неопределённый счёт";

        if(sum == null || sum == 0)
            return "неопределённая сумма";

        if(account.getClose_date() != null)
            return "счёт закрыт";

        Account_types type = account.getType();

        if(sum > 0) {
            if(!type.isAllow_refill())
                return "счёт закрыт для пополнений";
        } else {
            if(!type.isAllow_write_off())
                return "счёт закрыт для снятий";
        }

        if(type.getCredit_limit() > account.getBalance() + sum)
            return "недостаточно средств или выход за пределы кредитного лимита";

        em.getTransaction().begin();

        account.setBalance(account.getBalance() + sum);

        History hist = new History();
        hist.setAccount(account);
        hist.setDate(currentTime());
        hist.setSum(sum);

        em.persist(hist);
        em.persist(account);
        em.getTransaction().commit();

        return null;
    }

    public TreeSet<String> departureCities() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> q = cb.createQuery(String.class);

        Root<Branches> root = q.from(Branches.class);
        q.select(root.get(Branches_.CITY));

        return new TreeSet<>(em.createQuery(q).getResultList());
    }

    public List<Branches> getBranchesList(FindDepartureConfiguration conf) {
        boolean filled = false;
        TreeSet<Long> ids = new TreeSet<>();

        if(conf.isAllowNum() && conf.getNum() != null) {
            Branches dep = branchById(conf.getNum());

            if(dep != null)
                ids.add(dep.getId());

            filled = true;
        }

        if(conf.isAllowAccount() && conf.getAccount() != null) {
            if(!(filled && ids.size() == 0)) {
                Accounts acc = accountById(conf.getAccount());

                if(acc != null) {
                    Long branch = acc.getBranch().getId();

                    if(filled) {
                        TreeSet<Long> toIntersect = new TreeSet<>();
                        toIntersect.add(branch);
                        ids.retainAll(toIntersect);
                    } else {
                        ids.add(branch);
                    }
                } else {
                    ids.clear();
                }
            }

            filled = true;
        }

        if(conf.isAllowClient() && conf.getClient() != null) {
            if(!(filled && ids.size() == 0)) {
                Clients cli = clientById(conf.getClient());

                if(cli != null) {
                    List<History> hist= historyByClient(cli);

                    LinkedList<Long> good = new LinkedList<>();
                    for(History e : hist) {
                        good.add(e.getAccount().getBranch().getId());
                    }

                    if(filled) {
                        ids.retainAll(good);
                    } else {
                        ids.addAll(good);
                    }
                } else {
                    ids.clear();
                }
            }

            filled = true;
        }

        if(conf.isAllowCities() && conf.getCities() != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Branches> q = cb.createQuery(Branches.class);
            Root<Branches> branches = q.from(Branches.class);

            q.select(branches);
            q.where(branches.get(Branches_.city).in(conf.getCities()));

            LinkedList<Long> toIntersect = new LinkedList<>();

            for(Branches br : new LinkedList<>(em.createQuery(q).getResultList())) {
                toIntersect.add(br.getId());
            }
            if(filled) {
                ids.retainAll(toIntersect);
            } else {
                ids.addAll(toIntersect);
                filled = true;
            }
        }

        LinkedList<Branches> res;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Branches> q = cb.createQuery(Branches.class);
        Root<Branches> branches = q.from(Branches.class);

        if(filled)
            q.where(branches.get("id").in(ids));

        q.select(branches);
        res = new LinkedList<>(em.createQuery(q).getResultList());

        return res;
    }

    public String verifyBranchToClose(Branches branch) {
        return null;
    }

    public Branches updateBranch(Long id, BranchConfiguration conf) {
        Branches branch = branchById(id);
        conf.updateBranch(branch);

        em.getTransaction().begin();
        em.merge(branch);
        em.getTransaction().commit();

        return branch;
    }
}