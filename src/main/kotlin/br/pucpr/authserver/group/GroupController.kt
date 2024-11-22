package br.pucpr.authserver.group

import br.pucpr.authserver.errors.BadRequestException
import br.pucpr.authserver.group.response.GroupResponse
import br.pucpr.authserver.users.SortDir
import br.pucpr.authserver.users.requests.CreateUserRequest
import br.pucpr.authserver.users.responses.UserResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.persistence.Id
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController (
    val service: GroupService
){
    private val logger = LoggerFactory.getLogger(GroupController::class.java)
    @PostMapping
    @SecurityRequirement(name = "AuthServer")
    @PreAuthorize("hasRole('ADMIN')")
    fun insert(@RequestParam(required = false) name: String,
               @RequestParam(required = false)  adminId: Long?): ResponseEntity<GroupResponse> {
        logger.info("Received request to insert group with name: $name")

        return try {
            service.insert(name,adminId)
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

    @GetMapping
    fun list( @RequestParam(required = false) sortDir: String?,
              @RequestParam(required = false) adminId: Long?) =
        service.list(
            sortDir = SortDir.getByName(sortDir) ?:
            throw BadRequestException("Invalid sort dir!"),
            adminId = adminId
        )   ?.map { GroupResponse(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()


}