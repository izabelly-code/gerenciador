package br.pucpr.authserver.group

import br.pucpr.authserver.driver.Driver
import br.pucpr.authserver.plan.Plan
import br.pucpr.authserver.plan.PlanTypes
import br.pucpr.authserver.student.Student
import br.pucpr.authserver.users.User
import jakarta.persistence.*
import jakarta.validation.constraints.Size



@Entity
@Table(name = "students_group")
class Group (

    @Id @GeneratedValue
    var id: Long? = null,
    var name: String,

    @ManyToOne
    @JoinTable(
        name = "group_driver",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "student_id")]
    )
    var admin: Driver?=null,

//    var route: Route,

    @OneToMany
    @JoinTable(
    name = "group_plan_types",
    joinColumns = [JoinColumn(name = "group_id")],
    inverseJoinColumns = [JoinColumn(name = "plan_id")]
    )
    @Size( max = 3)
    var plans: MutableSet<Plan>? = mutableSetOf()
)


