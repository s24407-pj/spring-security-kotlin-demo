package com.amigoscode.spring.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(val jwtService : JwtService) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String = request.getHeader("Authorization")
        val jwt: String
        val userEmail: String

        if (authHeader.isBlank() || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response)
        }
        jwt = authHeader.substring(7)
        userEmail = jwtService.extractUsername(jwt)
    }
}