package br.pucpr.authserver.student

import br.pucpr.authserver.group.Group
import br.pucpr.authserver.plan.Plan
import br.pucpr.authserver.plan.PlanTypes
import br.pucpr.authserver.users.User
import jakarta.persistence.*

@Entity
@Table(name="tbStudent")
class Student (

    @Id @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "plan_id")
    var plan: Plan? = null,

    @OneToOne
    val user: User,

    @ManyToOne
    @JoinColumn(name = "group_id")
    val group: Group? = null

    )
