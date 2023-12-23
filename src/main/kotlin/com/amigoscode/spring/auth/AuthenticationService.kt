package com.amigoscode.spring.auth

import com.amigoscode.spring.exception.ResourceNotFoundException
import com.amigoscode.spring.jwt.JwtService
import com.amigoscode.spring.user.Role
import com.amigoscode.spring.user.User
import com.amigoscode.spring.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        val user = User(
            request.firstname, request.lastname,
            request.email,
            passwordEncoder.encode(request.password),
            Role.USER
        )
        if (!userRepository.existsUserByEmail(request.email)) userRepository.save(user)
        else throw ResourceNotFoundException("User not exist")


        val jwtToken = jwtService.generateToken(user)

        return AuthenticationResponse(jwtToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val user = userRepository.findUserByEmail(request.email)

        val jwtToken = jwtService.generateToken(user!!)

        return AuthenticationResponse(jwtToken)
    }

}