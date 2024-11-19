package br.pucpr.authserver.plan

import jakarta.persistence.*

@Entity
@Table(name = "plan")
data class Plan (

    @Id @GeneratedValue
    var id : Long? = null,
    var type : PlanTypes,
    var priece : Double

)