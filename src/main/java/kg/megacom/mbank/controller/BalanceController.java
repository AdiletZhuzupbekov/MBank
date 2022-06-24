package kg.megacom.mbank.controller;

import kg.megacom.mbank.model.Balance;
import kg.megacom.mbank.service.BalanceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@AllArgsConstructor
public class BalanceController {

    private BalanceService balanceService;


    // check balance
    @GetMapping("/{accountId}")
    public BigDecimal getBalance(@PathVariable Long accountId){
        return balanceService.getBalance(accountId);
    }
    //add money
    @PostMapping("/add")
    public BigDecimal addBalance(@RequestBody Balance balance){
        return balanceService.addBalance(balance.getToId(),balance.getAmount());
    }
    //transfer money
    @PostMapping("/transfer")
    public void transferBalance(@RequestBody Balance balance){
         balanceService.makeTransfer(balance);
    }
    //get cash
    @PostMapping("/subtract")
    public BigDecimal subtractBalance(@RequestBody Balance balance){
        return balanceService.subtractBalance(balance.getFromId(),balance.getAmount());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handle(IllegalArgumentException e){
        log.error(e.getMessage());
        return "Akcha JOK";
    }
}
