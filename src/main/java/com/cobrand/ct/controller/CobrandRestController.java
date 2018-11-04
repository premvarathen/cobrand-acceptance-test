package com.cobrand.ct.controller;

import static com.cobrand.citi.constant.CobrandConstants.SESSION_KEY_RETRY_PNR_COUNTER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cobrand.ct.action.CobrandAction;
import com.cobrand.ct.action.PassengerAction;
import com.cobrand.ct.service.CobrandService;
import com.cobrand.ct.utils.ActionUtils;


@Controller
@EnableAutoConfiguration
public class CobrandRestController {
	@Autowired
	ActionUtils actionUtils ;
	@Autowired
	PassengerAction passengerAction;
	@Autowired
	private CobrandAction action ;
	
	@Autowired
	private CobrandService cobrandService;
	
	@Autowired
	private VelocityEngine engine;
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}

	@GetMapping("/infoResult")
	public String displayResult() {
		return "infoResult.html";
	}

	@GetMapping("/form")
	public String displaysResult() {
		return "infoResult.html";
	}
	
	@GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "result";
    }
    
	@GetMapping("/createPNR")
    public String createPNRForm(Model model) {
        model.addAttribute("pnrData", new PnrDataForm());
        return "createPNR";
    }
	
	@PostMapping("/createPNR")
    public String createPNRSubmit(@RequestParam Map<String, String> formData, Model model) {
		  List<Map<String, String>> reservationRawData = new ArrayList<>();
		  reservationRawData.add(formData);
		 String recordLocator = actionUtils.invokeWithRetry(() ->cobrandService.createPNR(reservationRawData),
						SESSION_KEY_RETRY_PNR_COUNTER);
				
		  ResultWrapper result = new ResultWrapper();
		  result.setRecordLocator(recordLocator);
		  model.addAttribute("result", result);
         return "result";
    }

	
	@PostMapping("/form")
	public ModelAndView createPNR( @RequestParam Map<String, String> formData, ModelAndView mav,RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("msg", "Success");
		mav.setViewName("infoResult");
		mav.addObject("recordLocator", "pnrcreated123456767");
	    return mav;
	}
	
	
}
