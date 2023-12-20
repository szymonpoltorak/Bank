package pl.edu.pw.ee.bankbackend.entities.user.interfaces;

import org.springframework.security.core.userdetails.UserDetails;


public interface ServiceUser extends UserDetails {
    long getUserId();

    String getFullName();
}
