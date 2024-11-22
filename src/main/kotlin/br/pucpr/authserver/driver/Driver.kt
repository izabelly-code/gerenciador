package br.pucpr.authserver.driver

import br.pucpr.authserver.users.User
import jakarta.persistence.*

@Entity
@Table(name="driver")
class Driver (
    @Id
    @GeneratedValue
    var id: Long? = null,

    @OneToOne
    val user: User
)