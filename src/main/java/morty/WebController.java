package morty;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class WebController {

	@GetMapping("/")
	public String index(Model model) {
		ArrayList<String> s = morty.getProspects();
		String ss = "";
		// Putting all the data into one string is (probably) not ideal, but works for now
		for (int i = s.size()-1; i >=0; i--) { 
			ss += morty.padding + "<br>";
			ss += s.get(i) + "<br>";
			ss += morty.padding + "<br>";
		}
		model.addAttribute("stringlist", ss);
		return "index";
	}
	@PostMapping("/")
	public String indexNew(@RequestParam String  name,@RequestParam double loanAmmount,@RequestParam double interesst,@RequestParam int years,Model model) {
		morty.newProspect(name, loanAmmount, interesst, years , true);
		ArrayList<String> s = morty.getProspects();
		String ss = "";
		for (int i = s.size()-1; i >=0; i--) { 
			ss += morty.padding + "<br>";
			ss += s.get(i) + "<br>";
			ss += morty.padding + "<br>";
		}
		model.addAttribute("stringlist", ss);
		return "index";
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public void handleMissingParams(MissingServletRequestParameterException ex) {
	    String name = ex.getParameterName();
	    System.out.println(name + " parameter is missing");
	    // Actual exception handling
	}
}
