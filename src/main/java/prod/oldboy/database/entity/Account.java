package prod.oldboy.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {

    public Account(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String pass;

    @ToString.Exclude
    @OneToOne(mappedBy = "account")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private User user;

    @ToString.Exclude
    @OneToOne(mappedBy = "account")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Amount amount;
}
