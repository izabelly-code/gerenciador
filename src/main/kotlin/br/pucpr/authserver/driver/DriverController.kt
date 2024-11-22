package br.pucpr.authserver.driver

import br.pucpr.authserver.driver.response.DriverResponse
import br.pucpr.authserver.group.GroupController
import br.pucpr.authserver.group.response.GroupResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/drivers")
class DriverController(
    val service: DriverService
) {
    private val logger = LoggerFactory.getLogger(GroupController::class.java)

    @PostMapping
    fun insert(@RequestBody userID : Long): ResponseEntity<DriverResponse> {
        logger.info("Received request to insert Driver with user Id: $userID")

        return try {
            service.insert(userID)
                ?.let {
                    logger.info("dRIVER successfully inserted with ID: ${it.id}")
                    DriverResponse(it)
                }
                .let { ResponseEntity.status(CREATED).body(it) }
        } catch (e: Exception) {
            logger.error("Error occurred while inserting Driver: ${e.message}", e)
            ResponseEntity.status(500).build()
        }
    }
}