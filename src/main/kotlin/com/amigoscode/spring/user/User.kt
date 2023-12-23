package com.amigoscode.spring.user

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    var firstName: String,
    var lastName: String,
    @Column(unique = true)
    var email: String,
    private var password: String,
    @Enumerated(EnumType.STRING)
    var role: Role,
    @Id
    @GeneratedValue
    var id: Long = -1
) : UserDetails {
    override fun getAuthorities() = mutableListOf(SimpleGrantedAuthority(role.name))
    override fun getPassword() = password
    override fun getUsername() = email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}

