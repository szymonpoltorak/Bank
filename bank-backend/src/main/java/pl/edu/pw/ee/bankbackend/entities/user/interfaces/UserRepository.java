package pl.edu.pw.ee.bankbackend.entities.user.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u from User as u inner join JwtToken as t on u.userId = t.user.userId where t.token = :authToken")
    Optional<User> findUserByToken(String authToken);

    @Query("select u from User as u where concat(u.name, ' ', u.surname) like %:searchPattern%")
    List<User> findUsersByFullNameContaining(@Param("searchPattern") String searchPattern);
}
