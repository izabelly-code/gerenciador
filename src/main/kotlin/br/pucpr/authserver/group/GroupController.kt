package br.pucpr.authserver.group

import br.pucpr.authserver.group.response.GroupResponse
import br.pucpr.authserver.users.requests.CreateUserRequest
import br.pucpr.authserver.users.responses.UserResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
class GroupController (
    val service: GroupService
){
    private val logger = LoggerFactory.getLogger(GroupController::class.java)
    @PostMapping
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN')")
    fun insert(@RequestBody name: String): ResponseEntity<GroupResponse> {
        logger.info("Received request to insert group with name: $name")

        return try {
            service.insert(name)
                .let {
                    logger.info("Group successfully inserted with ID: ${it.id}")
                    GroupResponse(it)
                }
                .let { ResponseEntity.status(CREATED).body(it) }
        } catch (e: Exception) {
            logger.error("Error occurred while inserting group: ${e.message}", e)
            ResponseEntity.status(500).build()
        }
    }

}