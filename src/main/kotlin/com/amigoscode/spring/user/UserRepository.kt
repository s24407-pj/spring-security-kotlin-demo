package com.amigoscode.spring.user;

interface UserRepository: org.springframework.data.jpa.repository.JpaRepository<com.amigoscode.spring.user.User, kotlin.Long> {
}