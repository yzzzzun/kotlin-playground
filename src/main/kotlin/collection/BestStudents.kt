package collection

import org.junit.Test
import kotlin.test.assertEquals

private val INTERSHIP = List(1) {5_000} + List(3){3_000} + List(6) {1_000}

fun List<Student2>.makeBestStudent2sList(): String = this.filter { it.pointsInSemester >=30 && it.result >=80 }
    .sortedByDescending { it.result }
    .zip(INTERSHIP)
    .sortedWith(compareBy(
        {it.first.surname},{it.first.name}
    ))
    .joinToString("\n"){"${it.first.name} ${it.first.surname}, $${it.second}"}


data class Student2(
    val name: String,
    val surname: String,
    val result: Double,
    val pointsInSemester: Int
)

class BestStudent2sListTest {
    val internshipStudent2 = Student2("Marc", "Smith", 87.0, 32)
    val Student2WithTooLowResultToInternship = Student2("Marcus", "Smith", 37.0, 32)
    val Student2WithNotEnoughPointsForInternship = Student2("Marcello", "Smith", 87.0, 12)
    val Student2NotPassingBecauseOfResult = Student2("Peter", "Jackson", 21.0, 24)
    val Student2NotPassingBecauseOfPoints = Student2("Michael", "Angelo", 71.0, 12)

    val allStudent2s = listOf(
        internshipStudent2,
        Student2WithTooLowResultToInternship,
        Student2WithNotEnoughPointsForInternship,
        Student2NotPassingBecauseOfResult,
        Student2("Noely", "Peterson", 91.0, 22),
        Student2NotPassingBecauseOfPoints,
        Student2("Noe", "Samson", 41.0, 18),
        Student2("Timothy", "Johnson", 51.0, 15),
        Student2("Noe", "Peterson", 91.0, 22),
        Student2("Ester", "Adams", 81.0, 30),
        Student2("Dior", "Angel", 88.5, 38),
        Student2("Naja", "Marcson", 100.0, 31),
        Student2("Oregon", "Dart", 85.5, 30),
        Student2("Ron", "Peters", 89.0, 31),
        Student2("Harry", "Potter", 80.0, 30),
        Student2("Sansa", "Stark", 49.5, 14),
        Student2("Jamme", "Lannister", 80.0, 30),
        Student2("Alex", "Nolan", 86.0, 33),
        Student2("Jon", "Johnson", 85.1, 31),
        Student2("James", "Johnson", 85.2, 31),
        Student2("Jack", "Johnson", 85.3, 31)
    )

    @Test
    fun `Single Student2 that matches criteria gets biggest internship`() {
        val text = listOf(internshipStudent2).makeBestStudent2sList()
        val expected = "Marc Smith, \$5000"
        assertEquals(expected, text)
    }

    @Test
    fun `Single Student2 with too low result doesn't get internship`() {
        val text = listOf(Student2WithTooLowResultToInternship).makeBestStudent2sList()
        assertEquals("", text)
    }

    @Test
    fun `Result 80 is acceptable`() {
        val Student2 = Student2("Noely", "Peterson", 80.0, 32)
        val text = listOf(Student2).makeBestStudent2sList()
        assertEquals("Noely Peterson, \$5000", text)
    }

    @Test
    fun `30 points is acceptable`() {
        val Student2 = Student2("Noely", "Peterson", 81.0, 30)
        val text = listOf(Student2).makeBestStudent2sList()
        assertEquals("Noely Peterson, \$5000", text)
    }

    @Test
    fun `Single Student2 with not enough doesn't get internship`() {
        val text = listOf(Student2WithNotEnoughPointsForInternship).makeBestStudent2sList()
        assertEquals("", text)
    }

    @Test
    fun `Complex test`() {
        val text = allStudent2s.makeBestStudent2sList()
        val expected = """
            Ester Adams, ${'$'}1000
            Dior Angel, ${'$'}3000
            Oregon Dart, ${'$'}1000
            Jack Johnson, ${'$'}1000
            James Johnson, ${'$'}1000
            Jon Johnson, ${'$'}1000
            Naja Marcson, ${'$'}5000
            Alex Nolan, ${'$'}1000
            Ron Peters, ${'$'}3000
            Marc Smith, ${'$'}3000
        """.trimIndent()
        assertEquals(expected, text)
    }
}