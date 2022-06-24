package kg.megacom.mbank.model;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class Balance {

    private Long fromId;
    private Long toId;
    private BigDecimal amount;
}
