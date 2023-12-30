package pl.edu.pw.ee.bankbackend.entities.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.bankbackend.entities.user.User;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;

    @Size(min = 26, max = 26)
    @Pattern(regexp = "\\d+$")
    @Column(unique = true)
    private String billNumber;

    private String accountName;

    @PositiveOrZero
    private float balance;

    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    @OneToOne
    private User user;

    public final void addAccountBalance(float balance) {
        this.balance += balance;
    }

    public final void subtractAccountBalance(float amount) {
        this.addAccountBalance(-amount);
    }
}
