package kg.megacom.mbank.service;

import kg.megacom.mbank.model.Balance;
import kg.megacom.mbank.repository.BalanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BalanceService {
    private BalanceRepository balanceRepository;


    public BigDecimal getBalance(Long accountId) {
        return balanceRepository.getBalanceFromId(accountId);
    }

    public BigDecimal addBalance(Long toId, BigDecimal amount) {
        BigDecimal currentBalance = balanceRepository.getBalanceFromId(toId);
        if (currentBalance == null) {
            balanceRepository.save(toId, amount);
            return amount;
        } else {
            BigDecimal updatedBalance = currentBalance.add(amount);
            balanceRepository.save(toId, updatedBalance);
            return updatedBalance;
        }
    }

    public void makeTransfer(Balance balance) {
        BigDecimal fromBalance = balanceRepository.getBalanceFromId(balance.getFromId());
        BigDecimal toBalance = balanceRepository.getBalanceFromId(balance.getToId());
        if (fromBalance == null || toBalance == null) throw new IllegalArgumentException("no ids");
        if (balance.getAmount().compareTo(fromBalance) > 0) throw new IllegalArgumentException("money jok");

        BigDecimal updatedFromBalance = fromBalance.subtract(balance.getAmount());
        BigDecimal updatedToBalance = toBalance.add(balance.getAmount());
        balanceRepository.save(balance.getFromId(), updatedFromBalance);
        balanceRepository.save(balance.getToId(), updatedToBalance);
    }

    public BigDecimal subtractBalance(Long fromId, BigDecimal amount) {
        BigDecimal currentBalance = balanceRepository.getBalanceFromId(fromId);
        if (currentBalance.compareTo(amount) <= -1) {
            throw new IllegalArgumentException("AKCHA JOK");
        }else {
            BigDecimal updatedBalance = currentBalance.subtract(amount);
            balanceRepository.save(fromId, updatedBalance);
            return updatedBalance;
        }
    }
}
