package br.pucpr.authserver.students

import br.pucpr.authserver.student.Student
import br.pucpr.authserver.student.StudentRepository
import br.pucpr.authserver.student.StudentService
import br.pucpr.authserver.users.User
import io.mockk.checkUnnecessaryStub
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class StudentServiceTest {
    private val repositoryMock = mockk<StudentRepository>()
    private val service = StudentService(
        repository = repositoryMock
    )

    @AfterEach
    fun cleanUp() {
        checkUnnecessaryStub()
    }

    @Test
    fun `insert throws BadRequestException if the student exists`() {
        val userId = 1L
        val user = User(id = userId)
        val existingStudent = Student(id = 1L) // Simula um estudante já existente no banco

        when`(repository.findByUserId(userId)).thenReturn(existingStudent)

            // Act & Assert
            val exception = assertThrows<IllegalArgumentException> {
                studentService.insert(userId, "Group A")
            }

            // Verifica se a exceção tem a mensagem correta
            assertEquals("Student already enrolled!", exception.message)

            // Verifica se o método do repositório foi chamado com o userId correto
            verify(repository).findByUserId(userId)

            // Garante que nenhum outro repositório foi chamado
            verifyNoInteractions(userRepository)
            verifyNoInteractions(groupRepository)
        }    }
}