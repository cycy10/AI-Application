package org.backend.contentgenerator.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private String secretKey ="";
    public JWTService(){
        KeyGenerator key = null;
        try {
            key = KeyGenerator.getInstance("HmacSHA256");
            SecretKey s = key.generateKey();
            secretKey = Base64.getEncoder().encodeToString(s.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

        public String generateToken(String username){
//        Map<String, Object> cl= new HashMap<>();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*30))
                .signWith(getKey())
                .compact();
    }

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
    public boolean validateToken(String token, UserDetails userDetails) {

        try {

            String username = extractUserName(token);

            return username.equals(userDetails.getUsername())
                    && !isTokenExpired(token);

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
