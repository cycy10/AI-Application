package org.backend.promptservice.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtValidation {
    private String secretKey ="5C6fVZ6Ene5s7i2qm7bUA6NATRD00vc/u3pKUtob+EE=";
    private Key getKey() {
        byte[] data = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(data);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ======================================
    // Extract Username
    // ======================================
    public String extractUserName(String token) {

        return extractClaims(token).getSubject();
    }
    // ======================================
    // Check Token Expiration
    // ======================================
    private boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // ======================================
    // Validate Token
    // ======================================
    public boolean validateToken(String token) {

        try {

            String username = extractUserName(token);

            return username!=null?true:false;

        } catch (ExpiredJwtException e) {
            System.out.println("Token Expired");

        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT");

        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT");

        } catch (SignatureException e) {
            System.out.println("Invalid Signature");

        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty");

        }
        return false;
    }
}
