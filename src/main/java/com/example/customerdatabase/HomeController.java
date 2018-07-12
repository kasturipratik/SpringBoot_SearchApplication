package com.example.customerdatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/")
    public String index(Model model){

        return "index";
    }

    @PostMapping("/lastname")
    public String processLastNameSearch(HttpServletRequest request, Model model){
        String searchLastName = request.getParameter("lastname");
        model.addAttribute("customers", customerRepository.findByLastnameIgnoreCase(searchLastName));
        List<Customer> cust = customerRepository.findByLastnameIgnoreCase(searchLastName);
        model.addAttribute("cityCount", -1);
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
}
