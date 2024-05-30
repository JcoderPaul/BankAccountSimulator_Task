package prod.oldboy.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import prod.oldboy.utils.Interests;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "amounts")
public class Amount {

    public Amount(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
        this.stopLimit = amount.multiply(Interests.getInstance().getLimitInterest());
        this.startLimit = amount;
        this.countPeriod = 0;
        this.interest = BigDecimal.valueOf(0);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Account account;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "start_limit", nullable = false)
    private BigDecimal startLimit;
    @Column(name = "stop_limit", nullable = false)
    private BigDecimal stopLimit;

    @Column(name = "count_period")
    private Integer countPeriod;

    @Column(name = "interest")
    private BigDecimal interest;

    public void setAccount(Account account) {
        account.setAmount(this);
        this.account = account;
    }
}
