package sec.project.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @PostConstruct
    public void init() {
        signupRepository.save(new Signup("Bjorn Arnleifsson", "28-388"));
        signupRepository.save(new Signup("Torvald Algeirsson", "55-221"));
        signupRepository.save(new Signup("Dagbjort og FlokR", "99-111"));
        signupRepository.save(new Signup("Teppa Pekkanen", "49-10-221-3"));
        Signup signup = new Signup();
        signup.setName("Donald");
        signup.setPhone("Duck");
        signupRepository.save(signup);
    }

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

      // We are able to remove this "debug" mapping as "remove" access to this page at all;
     //   Otherwise just will add more protection-layers for possibility to get page here
    //     OR by changing settings under the SecurityConfiguration.java :
    @RequestMapping(value = "/hidden", method = RequestMethod.GET)
    public String loadHidden() {
        return "hidden";
    }

      // We are able to add protection for "pages-not-for-all" with more proper design.
     //   There main protection is just the "string" under the GET (as parameter);
    //      And "just string" which can be transferred from user's browser.
   //     As result - enough to be logged (but page is 'designed' to be visible not for all logged users);
    @RequestMapping(value = "/fylkr", method = RequestMethod.GET)
    public String loadFylkr(Model model, @RequestParam(required = false) String trick) {
        if (trick.equals("doTheTrick")) {
            model.addAttribute("listeners", signupRepository.findAll());
            return "fylkr";
        } else {
            return "redirect:/form";
        }
    }

    @RequestMapping(value = "/formsrc", method = RequestMethod.GET)
    public String loadSRC(@RequestParam String review) {
        if (review.equals("on")) {
            return "formsrc";
        } else {
            return "redirect:/form";
        }
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Model model, Authentication check) {
            int trickCount;   // temporary trick for handling admin-access;
            String who = check.getName();
        model.addAttribute("who", who);
        if (who.equals("admin")){
            trickCount = 1;
        model.addAttribute("admin", trickCount);
        }
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String phone) {
        signupRepository.save(new Signup(name, phone));
        return "done";
    }
    
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public String loadPreview() {return "preview";}
    
    @RequestMapping(value = "/csrfiner", method = RequestMethod.GET)
    public String loadCSRFiner() {return "CSRFiner";}
    
    // there only "GET" and with potential 'logout'-functionality - CSRF will be with more power!
   // also good to use pre-configured URLs only and do not use 'parameter' from GET-request where will be redirect to this input. 
    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String loadMap(@RequestParam String URL) {
        if (URL.equalsIgnoreCase("form")){URL = "http://127.0.0.1:8080/form";}
        if (URL.equalsIgnoreCase("login")){URL = "http://127.0.0.1:8080/login";}
        if (URL.equalsIgnoreCase("hidden")){URL = "http://127.0.0.1:8080/hidden";}
        if (URL.equalsIgnoreCase("preview")){URL = "http://127.0.0.1:8080/preview";}
        if (URL.equalsIgnoreCase("formsrc")){URL = "http://127.0.0.1:8080/formsrc?review=on";}
        if (URL.equalsIgnoreCase("fylkr")){URL = "http://127.0.0.1:8080/fylkr?trick=doTheTrick";}
        return "redirect:" + URL;
    }
}
