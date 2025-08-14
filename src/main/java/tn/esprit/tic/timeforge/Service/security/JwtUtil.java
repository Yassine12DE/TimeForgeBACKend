//package mak.coifbook.services;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil {
//
//    private final String SECRET_KEY = "secret"; // Remplacez par une clé sécurisée.
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
//    }
//

//
//    public String generateToken(String username) {
//        return generateToken(username, 1000 * 60 * 15); // 15 minutes
//    }
//
//    public String generateRefreshToken(String username) {
//        return generateToken(username, 1000 * 60 * 60 * 24 * 7); // 7 jours
//    }
//
//    private String generateToken(String username, long expirationTime) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    public Boolean validateToken(String token, String username) {
//        final String extractedUsername = extractUsername(token);
//        return (extractedUsername.equals(username) && !isTokenExpired(token));
//    }
//    package mak.coifbook.services;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Services;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
//    @Services
//    public class JwtUtil {
//
//        private final String SECRET_KEY = "your_secret_key"; // Replace with a stronger secret key
//        private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
//
//        // Generate JWT token
//        public String generateToken(String username) {
//            return Jwts.builder()
//                    .setSubject(username)
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                    .compact();
//        }
//
//        // Extract username from token
//        public String extractUsername(String token) {
//            return Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//        }
//
//        // Validate JWT token
//        public boolean validateToken(String token) {
//            try {
//                Jwts.parser()
//                        .setSigningKey(SECRET_KEY)
//                        .parseClaimsJws(token);
//                return true;
//            } catch (Exception e) {
//                return false;
//            }
//        }
//
//        // Extract token from the request's Authorization header
//        public String extractToken(HttpServletRequest request) {
//            String header = request.getHeader("Authorization");
//
//            if (header != null && header.startsWith("Bearer ")) {
//                return header.substring(7); // Extract token part from "Bearer <token>"
//            }
//            return null;
//        }
//
//        // Generate Refresh Token (if needed)
//        public String generateRefreshToken(String username) {
//            return Jwts.builder()
//                    .setSubject(username)
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 2)) // 2x expiration time
//                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                    .compact();
//        }
//    }
//
//}
package tn.esprit.tic.timeforge.Service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Secure 512-bit key
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    // Generate JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }



    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
        public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }









    // Extract token from the request's Authorization header
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Extract token part from "Bearer <token>"
        }
        return null;
    }

    // Generate Refresh Token (if needed)
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 2)) // 2x expiration time
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
