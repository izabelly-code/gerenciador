package br.pucpr.authserver.group.response

import br.pucpr.authserver.group.Group
import br.pucpr.authserver.users.User

data class GroupResponse(
    val id: Long,
    val name: String
) {
    constructor(group: Group): this(
        id = group.id!!,
        name = group.name,
    )
}