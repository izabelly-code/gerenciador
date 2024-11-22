package br.pucpr.authserver.group

import br.pucpr.authserver.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GroupRepository : JpaRepository<Group, Long> {
    fun findByName(name: String): Group?

    @Query("SELECT g FROM Group g WHERE g.admin.id = :adminId")
    fun findByAdminId(adminId: Long): List<Group>?

}