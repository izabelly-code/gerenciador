package br.pucpr.authserver.group

import br.pucpr.authserver.student.StudentService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GroupService (
    val repository: GroupRepository
){

    fun insert(name: String): Group {
        return try {
            val group = repository.save(Group(name = name))
            log.info("Group inserted successfully with ID: ${group.id}") // Log successful insertion
            group
        } catch (e: Exception) {
            log.error("Error occurred while inserting group with name: $name", e) // Log error if something goes wrong
            throw RuntimeException("Error inserting group", e) // Rethrow exception or handle as needed
        }
    }
    companion object {
        private val log = LoggerFactory.getLogger(GroupService::class.java)
    }

}