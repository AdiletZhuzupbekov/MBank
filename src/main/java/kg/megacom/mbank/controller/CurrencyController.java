package kg.megacom.mbank.controller;

import kg.megacom.mbank.service.CurrencyConverterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CurrencyController {

    private final CurrencyConverterService currencyConverterService;




    @GetMapping
    public String mainPage(Model model){

        model.addAttribute("title", "Currency Converter");
        model.addAttribute("kgs",currencyConverterService.kgs);
        model.addAttribute("eur",currencyConverterService.eur);
        model.addAttribute("rub",currencyConverterService.rub);
        model.addAttribute("cad",currencyConverterService.cad);
        return "home";
    }

}