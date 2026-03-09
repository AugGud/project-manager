package io.github.auggud.projectmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    // String secretKey -> Bytes -> HMAC-SHA key
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Uses UserDetails to build the JWT token in string form with the Claims structure
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenValid(UserDetails userDetails, String token) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Takes in Claims object -> returns whatever particular Claim you pick
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Configure -> build -> use
        Claims claims = Jwts.parser()
                // when verifying use this key to verify the signature
                .verifyWith(getSigningKey())
                .build()
                // takes token and splits it into three main parts
                // then verifies the signature against secret key
                .parseSignedClaims(token)
                // We only want the payload, that's where sub, iat, exp, etc. is stored in
                .getPayload();
        return claimsResolver.apply(claims);
    }
}
