package com.jabaprac.webapp;

import com.jabaprac.webapp.banksystem.BankSystem;
import com.jabaprac.webapp.dbobjects.*;

import com.jabaprac.webapp.pageconf.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MainController {


    @Qualifier("bankSystem")
    @Autowired
    private BankSystem bank;
    private LinkedList<String> strings = new LinkedList<>();
    private String singleString;

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    @Qualifier("entityManager")
    @Autowired
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public TestBean getTb() {
        return tb;
    }

    @Qualifier("testBean")
    @Autowired
    public void setTb(TestBean tb) {
        this.tb = tb;
    }

    private TestBean tb;
    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(LinkedList<String> strings) {
        this.strings = strings;
    }

    public String getSingleString() {
        return singleString;
    }

    @Value("one single string")
    public void setSingleString(String singleString) {
        this.singleString = singleString;
    }

    @ModelAttribute("singleStr")
    public String sS() {
        return getSingleString();
    }

    @ModelAttribute("stringArr")
    public List<String> stringArr() {
        return strings;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // change the format according to your need.
        dateFormat.setLenient(false);

        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/testcss")
    public String testCSS() {
        return "page2";
    }

    @GetMapping("/testparam")
    public String testParam() {
        return "testparam";
    }

    @GetMapping("/testfactory")
    public String testSessionFactory() {


        return "testparam";
    }

    @GetMapping(value="/testlist")
    public ModelAndView testList(
            @RequestParam(value = "y", required = false) Integer year,
            @RequestParam(value = "d", required = false) Integer day,
            @RequestParam(value = "m", required = false) Integer month
            ) {

        if(year == null) {
            year = 1950;
        }

        if(month == null) {
            month = 0;
        }

        if(day == null) {
            day = 1;
        }

        ModelAndView mav = new ModelAndView("testlist");

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<History> q = cb.createQuery(History.class);
        Root<History> accs = q.from(History.class);

        q.select(accs);
        q.where(cb.greaterThan(accs.get(History_.date), cb.literal(new Date(year - 1900, month - 1, day))));

        List<History> ents = em.createQuery(q).getResultList();

        mav.addObject("dataArr", ents);

        return mav;
    }

    @GetMapping(value="/test_acc_types")
    public ModelAndView testAccTypes() {
        ModelAndView mav = new ModelAndView("testlist");

        mav.addObject("dataArr", bank.getAllAccountTypes());

        return mav;
    }

    @GetMapping("/form_example")
    public String showForm(Model mod) {
        mod.addAttribute("formExample1", new FormExample());
        mod.addAttribute("formExample2", new FormExample());

        return "form_example";
    }

    @PostMapping("/form_example")
    public String showForm(@ModelAttribute(name="formExample1") FormExample fe1,
                           @ModelAttribute(name="formExample2") FormExample fe2,
                           Model mod) {
        mod.addAttribute("formExample1", fe1);
        mod.addAttribute("formExample2", fe2);
//        mod.addAttribute("checkbox1", checkbox1);

        return "form_show";
    }

    @GetMapping("/show-clients")
    public String showClientsGet(Model mod) {

        FindClientConfiguration defaultClientConf = new FindClientConfiguration();
        List<ClientView> foundClients = bank.getClientList(defaultClientConf);

        mod.addAttribute("pageConf", new FindClientConfiguration());

        mod.addAttribute("clientList", foundClients);

        mod.addAttribute("clientTypeAll", ClientType.ALL);
        mod.addAttribute("clientTypePhys", ClientType.ONLY_PHYS);
        mod.addAttribute("clientTypeUr", ClientType.ONLY_UR);

        mod.addAttribute("checkboxOn", CheckboxValue.ON.getVal());
        mod.addAttribute("checkboxOff", CheckboxValue.OFF.getVal());

        mod.addAttribute("bank", bank);

        return "show-clients";
    }

    @PostMapping("/show-clients")
    public String showClientsPost(@ModelAttribute(name="pageConf") FindClientConfiguration conf, Model mod) {
        List<ClientView> foundClients = bank.getClientList(conf);

        mod.addAttribute("pageConf", conf);

        mod.addAttribute("clientList", foundClients);

        mod.addAttribute("clientTypeAll", ClientType.ALL);
        mod.addAttribute("clientTypePhys", ClientType.ONLY_PHYS);
        mod.addAttribute("clientTypeUr", ClientType.ONLY_UR);

        mod.addAttribute("checkboxOn", CheckboxValue.ON.getVal());
        mod.addAttribute("checkboxOff", CheckboxValue.OFF.getVal());

        mod.addAttribute("bank", bank);

//        return "print_configuration";
        return "show-clients";
    }

    @GetMapping("/add-client")
    public String addClientGet(Model mod) {
        ClientConfiguration defaultConf = new ClientConfiguration();
        mod.addAttribute("pageConf", defaultConf);

        mod.addAttribute("clientTypeAll", ClientType.ALL);
        mod.addAttribute("clientTypePhys", ClientType.ONLY_PHYS);
        mod.addAttribute("clientTypeUr", ClientType.ONLY_UR);

        return "add-client";
    }

    @PostMapping("/add-client")
    public String addClientPost(@ModelAttribute(name="pageConf") ClientConfiguration conf, Model mod) {
        mod.addAttribute("pageConf", conf);

        mod.addAttribute("clientTypeAll", ClientType.ALL);
        mod.addAttribute("clientTypePhys", ClientType.ONLY_PHYS);
        mod.addAttribute("clientTypeUr", ClientType.ONLY_UR);

        return "add-client";
    }

    @PostMapping("/try-add-client")
    public String tryAddClientPost(@ModelAttribute(name="pageConf") ClientConfiguration conf, Model mod) {
        mod.addAttribute("clientTypeAll", ClientType.ALL);
        mod.addAttribute("clientTypePhys", ClientType.ONLY_PHYS);
        mod.addAttribute("clientTypeUr", ClientType.ONLY_UR);

        boolean errorOccured = false;
        Clients added = null;

        String err = conf.verify();
        errorOccured = err != null;

        if(!errorOccured) {
            try {
                added = bank.persistClient(conf);
                if(added == null) {
                    errorOccured = true;
                    err = "Undefined error on saving client info";
                }
            } catch (Exception e) {
                errorOccured = true;
                err = e.getMessage();
            }
        }

        if(errorOccured) {
            mod.addAttribute("pageConf", conf);
            mod.addAttribute("ErrorMessage", err);

            return "add-client-fail";
        } else {
            mod.addAttribute("client", added);

            return "add-client-success";
        }
    }

    @GetMapping("/del-client")
    public String delClientGet(Long id, Model mod) {
        Clients client = bank.clientById(id);

        mod.addAttribute("client", client);
        return "del-client";
    }

    @PostMapping("/try-del-client")
    public String tryDelClientPost(Long id, Model mod) {
        Clients client = bank.clientById(id);
        mod.addAttribute("client", client);

        boolean errorOccurred = false;
        String errMsg = "";
        try {
            bank.removeClient(client);
        } catch (Exception e) {
            errorOccurred = true;
            errMsg = "Ошибка при удалении данных из базы";
        }

        if(errorOccurred) {
            mod.addAttribute("ErrorMessage", errMsg);
            return "del-client-fail";
        } else {
            return "del-client-success";
        }
    }

    @GetMapping("/alter-client")
    public String alterClientGet(Long id, Model mod) {
        Clients client = bank.clientById(id);

        ClientConfiguration conf = new ClientConfiguration(client);
        mod.addAttribute("pageConf", conf);
        mod.addAttribute("clientId", id);
        mod.addAttribute("clientTypePhys", ClientType.ONLY_PHYS);
        mod.addAttribute("clientTypeUr", ClientType.ONLY_UR);

        return "alter-client";
    }

    @PostMapping("/try-alter-client")
    public String tryAlterClient(@ModelAttribute ClientConfiguration conf, @RequestParam Long id, Model mod) {
        mod.addAttribute("clientTypePhys", ClientType.ONLY_PHYS);
        mod.addAttribute("clientTypeUr", ClientType.ONLY_UR);

        boolean errorOccured = false;
        Clients altered = null;

        String err = conf.verify();
        errorOccured = err != null;

        if(!errorOccured) {
            try {
                altered = bank.updateClient(id, conf);
                if(altered == null) {
                    errorOccured = true;
                    err = "Undefined error on altering client info";
                }
            } catch (Exception e) {
                errorOccured = true;
                err = e.getMessage();
            }
        }

        if(errorOccured) {
            mod.addAttribute("pageConf", conf);
            mod.addAttribute("ErrorMessage", err);
            mod.addAttribute("clientId", id);

            return "alter-client-fail";
        } else {
            mod.addAttribute("client", altered);

            return "alter-client-success";
        }
    }

    @GetMapping("/show-accounts")
    public String showAccountsGet(Model mod) {
        FindAccountConfiguration defaultConf = new FindAccountConfiguration();
        List<Accounts> foundAccounts = bank.getAccountList(defaultConf);

        mod.addAttribute("pageConf", defaultConf);

        mod.addAttribute("accountList", foundAccounts);

        mod.addAttribute("bank", bank);

        return "show-accounts";
    }

    @PostMapping("/show-accounts")
    public String showAccountsPost(@ModelAttribute(name="pageConf") FindAccountConfiguration conf, Model mod) {
        List<Accounts> foundAccounts = bank.getAccountList(conf);

        mod.addAttribute("pageConf", conf);

        mod.addAttribute("accountList", foundAccounts);

        mod.addAttribute("bank", bank);


        return "show-accounts";
//        return "page_configuration";
    }

    @GetMapping("/add-account")
    public String addAccountGet(Model mod,
                                @RequestParam(value = "client", required = false) Long clientNo,
                                @RequestParam(value = "branch", required = false) Long depNo) {
        AccountConfiguration defaultConf = new AccountConfiguration();

        if(clientNo != null)
            defaultConf.setClientNo(clientNo);

        if(depNo != null)
            defaultConf.setDepNo(depNo);

        mod.addAttribute("pageConf", defaultConf);
        mod.addAttribute("bank", bank);

        return "add-account";
    }

    @PostMapping("/add-account")
    public String addAccountPost(@ModelAttribute(name="pageConf") AccountConfiguration conf, Model mod) {
        mod.addAttribute("pageConf", conf);
        mod.addAttribute("bank", bank);

        return "add-account";
    }

    @PostMapping("/try-add-account")
    public String tryAddAccountPost(@ModelAttribute(name="pageConf") AccountConfiguration conf, Model mod) {
        boolean errorOccured = false;
        Accounts added = null;

        String err = conf.verify(bank);
        errorOccured = err != null;

        if(!errorOccured) {
            try {
                added = bank.persistAccount(conf);
                if(added == null) {
                    errorOccured = true;
                    err = "Undefined error on saving account info";
                }
            } catch (Exception e) {
                errorOccured = true;
                err = e.getMessage();
            }
        }

        if(errorOccured) {
            mod.addAttribute("pageConf", conf);
            mod.addAttribute("ErrorMessage", err);

            String accountTypeName = null;
            if(conf.getAccountType() != null) {
                Account_types accountType = bank.accountTypeById(conf.getAccountType());
                if(accountType != null)
                    accountTypeName = accountType.getName();
            }

            mod.addAttribute("accountTypeName", accountTypeName == null ? "Неопределённый тип" : accountTypeName);

            return "add-account-fail";
        } else {
            mod.addAttribute("account", added);

            return "add-account-success";
        }
    }

    @GetMapping("/del-account")
    public String delAccountGet(Long id, Model mod) {
        Accounts account = bank.accountById(id);

        mod.addAttribute("account", account);
        return "del-account";
    }

    @PostMapping("/try-del-account")
    public String tryDelAccountPost(Long id, Model mod) {
        Accounts account = bank.accountById(id);
        mod.addAttribute("account", account);

        String errMsg = bank.verifyAccountToClose(account);
        boolean errorOccurred = (errMsg != null);

        if(!errorOccurred) {
            try {
                bank.removeAccount(account);
            } catch (Exception e) {
                errorOccurred = true;
                errMsg = "Ошибка при удалении данных из базы";
            }
        }

        if(errorOccurred) {
            mod.addAttribute("errorMessage", errMsg);
            return "del-account-fail";
        } else {
            return "del-account-success";
        }
    }

    @GetMapping("/alter-account")
    public String alterAccountGet(Long id, Model mod) {
        Accounts account = bank.accountById(id);

        mod.addAttribute("account", account);
        mod.addAttribute("sum", null);
        return "alter-account";
    }

    @PostMapping("/alter-account")
    public String alterAccountPost(Long id, Long sum, Model mod) {
        Accounts account = bank.accountById(id);

        mod.addAttribute("account", account);
        mod.addAttribute("sum", sum);
        return "alter-account";
    }

    @PostMapping("/try-alter-account")
    public String tryAlterAccountPost(Long id, Long sum, Model mod) {
        Accounts account = bank.accountById(id);
        mod.addAttribute("account", account);

        String errMsg = bank.performTransaction(account, sum);
        boolean errorOccurred = (errMsg != null);

        if(errorOccurred) {
            mod.addAttribute("errorMessage", errMsg);
            mod.addAttribute("sum", sum);
            return "alter-account-fail";
        } else {
            return "alter-account-success";
        }
    }

    @GetMapping("/show-deps")
    public String showDeparturesGet(Model mod) {
        FindDepartureConfiguration defaultConf = new FindDepartureConfiguration();
        List<Branches> foundBranches = bank.getBranchesList(defaultConf);

        mod.addAttribute("pageConf", defaultConf);

        mod.addAttribute("branchesList", foundBranches);

        mod.addAttribute("bank", bank);

        return "show-deps";
    }

    @PostMapping("/show-deps")
    public String showDeparturesPost(@ModelAttribute(name="pageConf") FindDepartureConfiguration conf, Model mod) {
        List<Branches> foundBranches = bank.getBranchesList(conf);

        mod.addAttribute("pageConf", conf);

        mod.addAttribute("branchesList", foundBranches);

        mod.addAttribute("bank", bank);


        return "show-deps";
//        return "print_configuration";
    }

    @GetMapping("/add-dep")
    public String addBranchGet(Model mod) {
        BranchConfiguration defaultConf = new BranchConfiguration();

        mod.addAttribute("pageConf", defaultConf);
        return "add-dep";
    }

    @PostMapping("/add-dep")
    public String addBranchPost(@ModelAttribute(name="pageConf") BranchConfiguration conf, Model mod) {
        mod.addAttribute("pageConf", conf);

        return "add-dep";
    }

    @PostMapping("/try-add-dep")
    public String tryAddClientPost(@ModelAttribute(name="pageConf") BranchConfiguration conf, Model mod) {
        boolean errorOccured = false;
        Branches added = null;

        String err = conf.verify();
        errorOccured = err != null;

        if(!errorOccured) {
            try {
                added = bank.persistBranch(conf);
                if(added == null) {
                    errorOccured = true;
                    err = "Undefined error on saving client info";
                }
            } catch (Exception e) {
                errorOccured = true;
                err = e.getMessage();
            }
        }

        if(errorOccured) {
            mod.addAttribute("pageConf", conf);
            mod.addAttribute("ErrorMessage", err);

            return "add-dep-fail";
        } else {
            mod.addAttribute("branch", added);

            return "add-dep-success";
        }
    }

    @GetMapping("/del-dep")
    public String delBranchGet(Long id, Model mod) {
        Branches branch = bank.branchById(id);

        mod.addAttribute("branch", branch);
        return "del-dep";
    }

    @PostMapping("/try-del-dep")
    public String tryDelBranchPost(Long id, Model mod) {
        Branches branch = bank.branchById(id);
        mod.addAttribute("branch", branch);

        String errMsg = bank.verifyBranchToClose(branch);
        boolean errorOccurred = (errMsg != null);

        if(!errorOccurred) {
            try {
                bank.removeBranch(branch);
            } catch (Exception e) {
                errorOccurred = true;
                errMsg = e.getMessage();
            }
        }

        if(errorOccurred) {
            mod.addAttribute("errorMessage", errMsg);
            return "del-dep-fail";
        } else {
            return "del-dep-success";
        }
    }

    @GetMapping("/alter-dep")
    public String alterBranchGet(Long id, Model mod) {
        Branches branch = bank.branchById(id);
        BranchConfiguration conf = new BranchConfiguration(branch);

        mod.addAttribute("pageConf", conf);
        mod.addAttribute("id", id);

        return "alter-dep";
    }


    @PostMapping("/try-alter-dep")
    public String tryAlterDep(@ModelAttribute BranchConfiguration conf, @RequestParam Long id, Model mod) {
        boolean errorOccured = false;
        Branches altered = null;

        String err = conf.verify();
        errorOccured = err != null;

        if(!errorOccured) {
            try {
                altered = bank.updateBranch(id, conf);
                if(altered == null) {
                    errorOccured = true;
                    err = "Undefined error on altering branch info";
                }
            } catch (Exception e) {
                errorOccured = true;
                err = e.getMessage();
            }
        }

        if(errorOccured) {
            mod.addAttribute("pageConf", conf);
            mod.addAttribute("errorMessage", err);
            mod.addAttribute("id", id);

            return "alter-dep-fail";
        } else {
            mod.addAttribute("branch", altered);

            return "alter-dep-success";
        }
    }
}
