package com.amigoscode.spring.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {


    private val SECRET_KEY = "F5A97F1D817D5EA1D7FB9483581A2FDSDGASDRLJKNGSDOIJBNGSIKEDJHRBGIKJSDRTSZEDRYHDRTHDRFTYHJDR"


    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun <T> extractClaim(token: String, claimExtractor: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimExtractor.invoke(claims)
    }

    fun generateToken(userDetails: UserDetails) = generateToken(HashMap(), userDetails)
    fun generateToken(
        extraClaims: Map<String, Any> = emptyMap(),
        userDetails: UserDetails
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .add(extraClaims)
            .and()
            .signWith(getSignInKey())
            .compact()

    fun extractAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }


    fun isTokenValid(token: String, userDetails: UserDetails) =
        extractUsername(token) == userDetails.username && !isTokenExpired(token)

    fun isTokenExpired(token: String) =
        extractClaim(token, Claims::getExpiration).before(Date())


    private fun getSignInKey() =
        Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())
}
