package prod.oldboy.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    public User(Account account, String lastname, String firstname, String patronymic, LocalDate birthDate) {
        this.account = account;
        this.lastname = lastname;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @ToString.Exclude
    private Account account;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String patronymic;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Phone> phoneList = new ArrayList<>();

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Email> emailList = new ArrayList<>();

    public void setAccount(Account account) {
        account.setUser(this);
        this.account = account;
    }
}
