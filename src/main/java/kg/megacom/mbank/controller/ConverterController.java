package kg.megacom.mbank.controller;

import kg.megacom.mbank.service.CurrencyConverterService;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class ConverterController {

    private final CurrencyConverterService currencyConverterService;


    @GetMapping("/converter")
    public String converter(Model model){
        model.addAttribute("title", "Converter");
        return "converter";
    }


    @PostMapping("/converter")
    public String currencyConvert(@RequestParam String fromCode,
                                  @RequestParam String toCode,
                                  @RequestParam double amount,
                                  Model model) throws IOException, JSONException {
        currencyConverterService.sendHttpGetRequest(fromCode, toCode, amount);
        model.addAttribute("result", currencyConverterService.conversion_result);
        model.addAttribute("currency", toCode);
        return "converter";
    }
}
