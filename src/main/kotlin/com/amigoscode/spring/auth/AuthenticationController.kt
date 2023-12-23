package com.amigoscode.spring.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(private val authenticationService: AuthenticationService) {
    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authenticationService.register(request))

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authenticationService.authenticate(request))
}