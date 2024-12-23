package br.pucpr.authserver.student

import br.pucpr.authserver.errors.BadRequestException
import br.pucpr.authserver.group.GroupRepository
import br.pucpr.authserver.plan.PlanTypes
import br.pucpr.authserver.users.User
import br.pucpr.authserver.users.UserRepository
import br.pucpr.authserver.users.UserService
import jakarta.validation.constraints.Null
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service
class StudentService(
    private val repository: StudentRepository,
    private val Userepository: UserRepository,
    private val groupRepository: GroupRepository
) {

    fun insert(userId: Long, nameGroup: String): Student? {
        log.info("Iniciando inserção de estudante com userId: $userId e nameGroup: $nameGroup")

        // Buscar se já existe um estudante com o mesmo userId
        var student = repository.findByUserId(userId)
        log.info("Resultado da busca por estudante existente: $student")
        if (student != null) {
            throw IllegalArgumentException("Student already enrolled!")
        } else {

            // Buscar usuário no banco de dados de usuários
            val usuario = Userepository.findById(userId)
            if (usuario.isEmpty) {
                throw BadRequestException("User not found!")
            }
            log.info("Usuário encontrado: $usuario")

            // Buscar o grupo no banco de dados de grupos
            val group = groupRepository.findByName(nameGroup) ?: run {
                log.error("Erro: Grupo não encontrado para nameGroup: $nameGroup")
                throw BadRequestException("Group not found!")
            }
            println("Grupo encontrado: $group")

            // Criar o estudante
            student = Student(user = usuario.get(), group = group)
            println("Estudante criado com sucesso: $student")

            // Salvar estudante no banco de dados
            val savedStudent = repository.save(student)
            log.info("Estudante salvo no banco de dados: $savedStudent")
            return savedStudent
        }
    }

    //quero buscar o plano do grupo
    fun addPlan(plan:String , id: Long): Boolean {
        //buscar o motorista
        val student = repository.findById(id).orElseThrow { BadRequestException("Studant not find!") }
        //buscar o plano
        // Get the student's group
        val group = student.group
            ?: throw IllegalArgumentException("Student does not belong to a group!")

        val plan = group.plans?.find { it.type == PlanTypes.valueOf(plan) }
            ?: throw IllegalArgumentException("Plan with name $plan not found in group!")


        student.plan = plan

        repository.save(student)
        return true
    }

    fun delete(id: Long): Student? {
        val student = repository.findByIdOrNull(id)
            ?: return null

        repository.delete(student)
        return student
    }

    fun findByGroup(groupName: String): List<Student>? {
        return repository.findByGroup(groupName)
    }

    fun update(id: Long): Student? {
        log.info("Inside service")
        var student = repository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Student not found for id: $id")
        student.group = null
        repository.save(student)
        log.info("Student $student exit group: ")
        return student
    }

    companion object {
        private val log = LoggerFactory.getLogger(StudentService::class.java)
    }

}