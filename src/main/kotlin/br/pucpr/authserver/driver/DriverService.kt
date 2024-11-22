package br.pucpr.authserver.driver

import br.pucpr.authserver.errors.BadRequestException
import br.pucpr.authserver.group.GroupService
import br.pucpr.authserver.users.User
import br.pucpr.authserver.users.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DriverService(
    val repository: DriverRepository,
    val userRepository: UserRepository
) {

    fun insert(userId: Long): Driver ?{

        if (repository.findByUserId(userId) != null)
            throw IllegalArgumentException("Driver alright insert!")

        val user = userRepository.findById(userId).orElseThrow {
            BadRequestException("User not found!")
        }
        if(user.roles.any{ it.name.equals("ADMIN", ignoreCase = true) }) {
            val driver = repository.save(Driver(user = user))
            return driver
        }
        else {
            throw BadRequestException("User is not an admin!")
        }
    }
}