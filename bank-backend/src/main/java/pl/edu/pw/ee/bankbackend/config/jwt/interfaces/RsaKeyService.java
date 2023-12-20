package pl.edu.pw.ee.bankbackend.config.jwt.interfaces;

import java.security.Key;

public interface RsaKeyService {
    Key buildSignInKey();

    Key buildVerifyKey();
}
