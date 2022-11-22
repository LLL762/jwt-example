package com.delacasa.jwt.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.time.Instant.now;

@Component
public class JwtUtilImpl implements JwtUtil {

    @Getter
    private static final String CLAIM_ROLE = "role";
    private final int expirationInSeconds = 3600;
    private final String issuer = "Robert";
    private final OctetKeyPair privateKey = new OctetKeyPairGenerator(Curve.Ed25519)
            .keyID("123")
            .generate();

    private final JWSSigner signer = new Ed25519Signer(privateKey);

    @Getter
    private final OctetKeyPair publicKey = privateKey.toPublicJWK();

    public JwtUtilImpl() throws JOSEException {
    }


    @Override
    public String createToken(Authentication authResult) {

        try {
            final JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject((String) authResult.getPrincipal())
                    .issuer(issuer)
                    .issueTime(new Date())
                    .expirationTime(Date.from(now().plusSeconds(expirationInSeconds)))
                    .claim(CLAIM_ROLE, authResult.getAuthorities())
                    .build();
            final JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.EdDSA).keyID(privateKey.getKeyID()).build();
            final SignedJWT jws = new SignedJWT(header, claims);
            jws.sign(signer);
            return jws.serialize();
        } catch (JOSEException jose) {
            jose.printStackTrace();
            throw new RuntimeException();
        }
    }
}
