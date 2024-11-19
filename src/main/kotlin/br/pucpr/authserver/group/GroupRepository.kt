package br.pucpr.authserver.group

import br.pucpr.authserver.users.User
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Long> {
    fun findByName(name: String): Group?

}