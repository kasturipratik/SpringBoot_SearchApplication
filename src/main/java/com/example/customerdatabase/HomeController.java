package com.example.customerdatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("zipcodes",customerRepository.findAll());
        return "index";
    }

    @RequestMapping("/addCompany")
    public String addCompany(Model model){
        model.addAttribute("employer",new Employer());
        model.addAttribute("companyAdd","");
        return "addingCompany";
    }

    @PostMapping("/processCompany")
    public String processCompany(@ModelAttribute("employer") Employer employer, Model model){

        companyRepository.save(employer);

        model.addAttribute("companyAdd","You added "+ employer.getCompany() + " to the company table.");

        return "addingCompany";
    }

    @RequestMapping("/addCustomer")
    public String addCustoemr(Model model){
        model.addAttribute("customer", new Customer());

        model.addAttribute("companyRepo", companyRepository.findAll());

        model.addAttribute("customerAdd", "");

        return "addCustomer";
    }

    @PostMapping("/processCustomer")
    public String processCustomer(@ModelAttribute Customer customer, Model model){

        customerRepository.save(customer);

        model.addAttribute("companyRepo", companyRepository.findAll());

        model.addAttribute("customerAdd", "You added "+ customer.getFirstname() +" "+customer.getLastname() + " to the customer table");
        return "addCustomer";
    }

    @PostMapping("/lastname")
    public String processLastNameSearch(HttpServletRequest request, Model model){
        String searchLastName = request.getParameter("lastname");
        model.addAttribute("searchString", "You searched for " + searchLastName);
        model.addAttribute("customers", customerRepository.findByLastnameIgnoreCase(searchLastName));
        List<Customer> cust = customerRepository.findByLastnameIgnoreCase(searchLastName);
        model.addAttribute("cityCount", -1);
        model.addAttribute("zipCount", -1);
        model.addAttribute("count", cust.size());
        if(cust.size() >0)
        {
            model.addAttribute("customers", cust);
            return "index";
        }
        else {
            model.addAttribute("message", "No record Found");
            return "index";
        }
    }

    @PostMapping("/city")
    public String processByLocation(HttpServletRequest request, Model model){

        String searchCity = request.getParameter("city");
        model.addAttribute("count", -1);
        model.addAttribute("zipCount", -1);
        model.addAttribute("searchCity", "You searched for " + searchCity);
        List<Customer> cust =  customerRepository.findByCityIgnoreCase(searchCity);
        model.addAttribute("cityCount", cust.size());
        if(cust.size() >0)
        {
            model.addAttribute("location", cust);
            return "index";
        }
        else {
            model.addAttribute("notfound", "No record Found");
            return "index";
        }
    }

    @PostMapping("/searchZipcode")
    public String processZipcode(Model model, HttpServletRequest request){
        int zip = Integer.parseInt(request.getParameter("zipInput"));
        model.addAttribute("count", -1);
        model.addAttribute("cityCount", -1);
        model.addAttribute("searchZip", "You searched for " + zip);
        List<Customer> cust = customerRepository.findByZipcode(zip);
        model.addAttribute("zipCount", cust.size());
        if(cust.size() >0)
        {
            model.addAttribute("zipCode", cust);
            return "index";
        }
        else {
            model.addAttribute("norecord", "No record Found");
            return "index";
        }

    }
}
