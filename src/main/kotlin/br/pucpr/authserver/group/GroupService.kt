package br.pucpr.authserver.group

import br.pucpr.authserver.driver.DriverRepository
import br.pucpr.authserver.users.SortDir
import br.pucpr.authserver.users.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GroupService(
    val repository: GroupRepository,
    val driverRepository: DriverRepository
){

    fun insert(name: String, adminId: Long?): Group {
        return try {
            if (repository.findByName(name) != null) {
                throw IllegalArgumentException("Group with name $name already exists") // Throw exception if group with name already exists
            }


            val driver = driverRepository.findDriverById(adminId) // Find driver by ID or throw exception if not found
            val group = repository.save(Group(name = name, admin = driver)) // Save group
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

    fun list(sortDir: SortDir, adminId: Long?): List<Group>? {
        if (adminId != null) {
            return repository.findByAdminId(adminId)
        } else {
            return when (sortDir) {
                SortDir.ASC -> repository.findAll()
                SortDir.DESC -> repository.findAll(Sort.by("id").reverse())
            }
        }
    }


}