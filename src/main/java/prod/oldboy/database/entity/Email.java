package prod.oldboy.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "emails")
public class Email {

    public Email(User user, String userEmail) {
        this.user = user;
        this.userEmail = userEmail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "user_email")
    private String userEmail;

    public void setUser(User user) {
        this.user = user;
        this.user.getEmailList().add(this);
    }
}
