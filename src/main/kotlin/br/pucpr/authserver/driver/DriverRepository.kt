package br.pucpr.authserver.driver

import br.pucpr.authserver.group.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DriverRepository: JpaRepository<Driver, Long> {

    @Query("SELECT d FROM Driver d WHERE d.user.id = :userId")
    fun findByUserId(userId: Long): Driver?

    fun findDriverById(id: Long?): Driver?
}