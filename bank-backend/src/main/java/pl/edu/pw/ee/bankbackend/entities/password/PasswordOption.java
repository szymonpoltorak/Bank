package pl.edu.pw.ee.bankbackend.entities.password;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pw.ee.bankbackend.entities.user.User;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PasswordOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long passwordOptionId;

    @NotNull
    private String passwordCombinationId;

    @NotNull
    private String combinationHash;

    @ManyToOne
    private User user;
}
