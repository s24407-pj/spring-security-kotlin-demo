package com.amigoscode.spring.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findUserByEmail(email: String): User?
    fun existsUserByEmail(email: String): Boolean
}