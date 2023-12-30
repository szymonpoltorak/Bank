package pl.edu.pw.ee.bankbackend.entities.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;

    private LocalDate date;

    @Positive
    private float amount;

    @Size(min = 4, max = 120)
    @Pattern(regexp = "^[a-zA-Z0-9 _-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$")
    private String title;

    @OneToOne
    private User from;

    @OneToOne
    private User to;
}
