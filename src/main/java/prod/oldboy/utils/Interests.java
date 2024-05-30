package prod.oldboy.utils;

import java.math.BigDecimal;

public final class Interests {
    private static Interests INSTANCE;
    private final BigDecimal increaseBy = BigDecimal.valueOf(1.05);
    private final BigDecimal limitInterest = BigDecimal.valueOf(2.07);

    private Interests() {
    }

    public synchronized static Interests getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Interests();
        }
        return INSTANCE;
    }

    public BigDecimal getIncreaseBy() {
        return increaseBy;
    }

    public BigDecimal getLimitInterest() {
        return limitInterest;
    }
}