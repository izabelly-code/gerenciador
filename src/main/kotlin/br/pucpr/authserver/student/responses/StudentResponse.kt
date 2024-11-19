package br.pucpr.authserver.student.responses

import br.pucpr.authserver.plan.PlanTypes
import br.pucpr.authserver.student.Student
import br.pucpr.authserver.users.User

data class StudentResponse (
    val id: Long,
    val user: User,

) {
    constructor(student: Student): this(
        id = student.id!!,
        user = student.user
    )
}
