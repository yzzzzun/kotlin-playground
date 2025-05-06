package collection

import org.junit.Assert.assertEquals
import org.junit.Test

data class StudentJson(
    val name: String?,
    val surname: String?,
    val result: Double,
    val pointsInSemester: Int
)

fun List<StudentJson>.getPassingSurnames(): List<String> =
    this.filter { it.result >= 50 && it.pointsInSemester >= 15}
        .mapNotNull { it.surname }

class PassingSurnamesTest {
    @Test
    fun `should return passing surnames`() {
        val students = listOf(
            StudentJson("John", "Smith", 60.0, 20),
            StudentJson("Jane", "Doe", 45.0, 20),
            StudentJson("Ivan", "Ivanov", 60.0, 10),
            StudentJson("John", "Doe", 30.0, 10),
            StudentJson("Jake", "Simonson", 80.0, 20),
        )
        assertEquals(
            listOf("Smith", "Simonson"),
            students.getPassingSurnames()
        )
    }
}