package br.pucpr.authserver.student

import br.pucpr.authserver.errors.BadRequestException
import br.pucpr.authserver.errors.ForbiddenException
import br.pucpr.authserver.security.UserToken
import br.pucpr.authserver.student.requests.StudentRequest
import br.pucpr.authserver.student.responses.StudentResponse
import br.pucpr.authserver.users.SortDir
import br.pucpr.authserver.users.UserService
import br.pucpr.authserver.users.requests.CreateUserRequest
import br.pucpr.authserver.users.requests.LoginRequest
import br.pucpr.authserver.users.requests.UpdateUserRequest
import br.pucpr.authserver.users.responses.UserResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

//
@RestController
@RequestMapping("/students")
class StudentController (
    val service: StudentService,
    ) {

        @PostMapping
        fun insert(@RequestBody @Valid studentRequest: StudentRequest) {
            val userId = studentRequest.userId
            val nameGroup = studentRequest.nameGroup
              service.insert(userId!!, nameGroup!!)
                 .let { StudentResponse(it) }
                 .let { ResponseEntity.status(CREATED).body(it) }

        }

        @PutMapping("/{id}/plans/{plan}")
        fun addPlan(@PathVariable plan:String , @PathVariable id: Long) : ResponseEntity<Void> =
            if (service.addPlan(plan.uppercase(),id))
                ResponseEntity.ok().build()
            else
                ResponseEntity.status(NO_CONTENT).build()

        @DeleteMapping("/{id}")
        @SecurityRequirement(name = "AuthServer")
        @PreAuthorize("hasRole('ADMIN')")
        fun delete(@PathVariable id: Long): ResponseEntity<Void> =
            service.delete(id)
                ?.let { ResponseEntity.ok().build() }
                ?: ResponseEntity.notFound().build()


        @GetMapping("/{id}")
        fun findByGroup(@PathVariable groupName:   String) =
            service.findByGroup(groupName)
                ?.map { StudentResponse(it) }
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()

//        @PutMapping("/{id}")
//        fun exitGroup(@RequestBody id:Long) =
//            service.exitGroup(id)
//                .let { ResponseEntity.ok().build() }
//                ?: ResponseEntity.notFound().build()

}