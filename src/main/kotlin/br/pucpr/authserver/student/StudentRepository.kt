package br.pucpr.authserver.student

import br.pucpr.authserver.group.Group
import br.pucpr.authserver.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<Student, Long> {

    fun findByUserId(userId: Long): Student?

    @Query(
        "select s from Student s " +
        " join s.group g" +
        " where g.name = :group"

    )
    fun findByGroup(group: String): List<Student>
}