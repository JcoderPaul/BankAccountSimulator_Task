package prod.oldboy.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "phones")
public class Phone {

    public Phone(User user, String userPhone) {
        this.user = user;
        this.userPhone = userPhone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "user_phone")
    private String userPhone;

    public void setUser(User user) {
        this.user = user;
        this.user.getPhoneList().add(this);
    }
}
