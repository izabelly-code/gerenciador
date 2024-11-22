package br.pucpr.authserver.driver.response

import br.pucpr.authserver.driver.Driver
import br.pucpr.authserver.users.User

data class DriverResponse (
    val id: Long,
    val user: User
){
    constructor(driver: Driver): this(
        id = driver.id!!,
        user = driver.user
    )
}