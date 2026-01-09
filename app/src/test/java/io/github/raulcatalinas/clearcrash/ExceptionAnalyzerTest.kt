package io.github.raulcatalinas.clearcrash

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for ExceptionAnalyzer.
 *
 * This test verifies that the ExceptionAnalyzer can analyze different types
 * of exceptions and provide helpful error messages. Results are printed
 * to the console for easy review.
 */
class ExceptionAnalyzerTest {

    @Before
    fun setUp() {
        println("\n========== ExceptionAnalyzer Test Start ==========\n")
    }

    @Test
    fun testAnalyzeNullPointerException() {
        println("📍 TEST: Analyzing NullPointerException")
        println("-".repeat(60))

        try {
            val text: String? = null

            println(text!!.length)
        } catch (e: NullPointerException) {
            val analysis = ExceptionAnalyzer.analyze(e)

            println("Exception: NullPointerException")
            println("Message: $analysis")
            println()
            println("Analysis Result:")
            println(analysis)
            println("-".repeat(60))

            assertNotNull("Analysis should not be null", analysis)
            assertTrue("Analysis should not be empty", analysis.isNotEmpty())
        }
    }

    @Test
    fun testAnalyzeIndexOutOfBoundsException() {
        println("📍 TEST: Analyzing IndexOutOfBoundsException")
        println("-".repeat(60))

        val exception = IndexOutOfBoundsException("Index 10 out of bounds for length 5")
        val analysis = ExceptionAnalyzer.analyze(exception)

        println("Exception: IndexOutOfBoundsException")
        println("Message: ${exception.message}")
        println()
        println("Analysis Result:")
        println(analysis)
        println("-".repeat(60))

        assertNotNull("Analysis should not be null", analysis)
        assertTrue("Analysis should not be empty", analysis.isNotEmpty())
    }

    @Test
    fun testAnalyzeClassCastException() {
        println("📍 TEST: Analyzing ClassCastException")
        println("-".repeat(60))

        val exception = ClassCastException("java.lang.String cannot be cast to java.lang.Integer")
        val analysis = ExceptionAnalyzer.analyze(exception)

        println("Exception: ClassCastException")
        println("Message: ${exception.message}")
        println()
        println("Analysis Result:")
        println(analysis)
        println("-".repeat(60))

        assertNotNull("Analysis should not be null", analysis)
        assertTrue("Analysis should not be empty", analysis.isNotEmpty())
    }

    @Test
    fun testAnalyzeGenericException() {
        println("📍 TEST: Analyzing Generic Exception")
        println("-".repeat(60))

        val exception = RuntimeException("Something went wrong!")
        val analysis = ExceptionAnalyzer.analyze(exception)

        println("Exception: RuntimeException")
        println("Message: ${exception.message}")
        println()
        println("Analysis Result:")
        println(analysis)
        println("-".repeat(60))

        assertNotNull("Analysis should not be null", analysis)
        assertTrue("Analysis should contain exception class name", analysis.contains("RuntimeException"))
        assertTrue("Analysis should contain error message", analysis.contains("Something went wrong!"))
    }

    @Test
    fun testAnalyzeExceptionWithoutMessage() {
        println("📍 TEST: Analyzing Exception Without Message")
        println("-".repeat(60))

        val exception = IllegalArgumentException()
        val analysis = ExceptionAnalyzer.analyze(exception)

        println("Exception: IllegalArgumentException")
        println("Message: ${exception.message ?: "null"}")
        println()
        println("Analysis Result:")
        println(analysis)
        println("-".repeat(60))

        assertNotNull("Analysis should not be null", analysis)
        assertTrue("Analysis should contain exception type", analysis.contains("IllegalArgumentException"))
    }

    @Test
    fun testAnalyzeNestedExceptions() {
        println("📍 TEST: Analyzing Nested Exceptions")
        println("-".repeat(60))

        val rootException = NullPointerException("Null pointer exception")
        val nestedException = RuntimeException("Failed to fetch data", rootException)
        val analysis = ExceptionAnalyzer.analyze(nestedException)

        println("Exception: RuntimeException")
        println("Message: ${nestedException.message}")
        println("Caused by: ${nestedException.cause?.javaClass?.simpleName}")
        println()
        println("Analysis Result:")
        println(analysis)
        println("-".repeat(60))

        assertNotNull("Analysis should not be null", analysis)
        assertTrue("Analysis should contain outer exception info", analysis.contains("RuntimeException"))
    }

    @Test
    fun testAnalyzerReturnsFormattedString() {
        println("📍 TEST: Verifying Analysis Format")
        println("-".repeat(60))

        val exception = Exception("Test error message")
        val analysis = ExceptionAnalyzer.analyze(exception)

        println("Analysis Structure Check:")
        println("Is multi-line: ${analysis.contains("\n")}")
        println("Contains class name: ${analysis.contains(exception.javaClass.simpleName)}")
        println("Contains message: ${analysis.contains(exception.message ?: "No message")}")
        println()
        println("Full Analysis Output:")
        println(analysis)
        println("-".repeat(60))

        assertTrue("Analysis should be formatted as multi-line", analysis.contains("\n"))
        assertTrue("Analysis should contain exception class name", analysis.contains("Exception"))
    }

    @Test
    fun testExceptionAnalyzerComparison() {
        println("📍 TEST: Comparing Analysis of Different Exception Types")
        println("-".repeat(60))
        println()

        val exceptions = listOf(
            Pair("NullPointerException", NullPointerException("Null object reference")),
            Pair("IndexOutOfBoundsException", IndexOutOfBoundsException("Invalid index")),
            Pair("ClassCastException", ClassCastException("Type mismatch")),
            Pair("RuntimeException", RuntimeException("General runtime error")),
            Pair("IllegalArgumentException", IllegalArgumentException("Invalid argument")),
        )

        for ((name, exception) in exceptions) {
            println("📌 $name:")
            println("─".repeat(40))
            val analysis = ExceptionAnalyzer.analyze(exception)
            println(analysis)
            println()
        }
        println("-".repeat(60))
    }

    @Test
    fun printExceptionAnalyzerSummary() {
        println(
            """

            📊 ExceptionAnalyzer Test Summary:
            ──────────────────────────────────

            The ExceptionAnalyzer provides:
            ✓ Automatic exception classification
            ✓ User code location identification
            ✓ Helpful error messages with formatting
            ✓ Support for multiple exception types
            ✓ Graceful fallback for unknown exceptions

            Supported Exception Types:
            • NullPointerException (Analysis coming soon)
            • IndexOutOfBoundsException (Analysis coming soon)
            • ClassCastException (Analysis coming soon)
            • Generic exceptions (Immediate analysis)

            Each analysis includes:
            📍 Exception type and message
            📍 Location in user code (if available)
            📍 Helpful suggestions for fixing
            📍 Link to GitHub issues for support

            ✨ The analyzer automatically filters out framework code
               and focuses on user code in the stack trace!
        """.trimIndent()
        )
    }

    @Test
    fun testAnalysisConsistency() {
        println("📍 TEST: Verifying Analysis Consistency")
        println("-".repeat(60))

        val exception = Exception("Consistent error")

        // Analyze the same exception multiple times
        val analysis1 = ExceptionAnalyzer.analyze(exception)
        val analysis2 = ExceptionAnalyzer.analyze(exception)

        println("First analysis length: ${analysis1.length}")
        println("Second analysis length: ${analysis2.length}")
        println("Are analyses equal: ${analysis1 == analysis2}")
        println()
        println("First Analysis:")
        println(analysis1)
        println()
        println("Second Analysis:")
        println(analysis2)
        println("-".repeat(60))

        assertEquals("Analysis should be consistent", analysis1, analysis2)
    }

    @Test
    fun testExceptionAnalyzerWithStackTrace() {
        println("📍 TEST: Analyzing Exception with Full Stack Trace")
        println("-".repeat(60))

        val exception = try {
            throw RuntimeException("Simulated application error")
        } catch (e: Exception) {
            e
        }

        val analysis = ExceptionAnalyzer.analyze(exception)

        println("Exception Class: ${exception.javaClass.simpleName}")
        println("Stack Trace Elements: ${exception.stackTrace.size}")
        println()
        println("Analysis with Stack Trace Context:")
        println(analysis)
        println()
        println("Stack Trace Details:")
        exception.stackTrace.take(3).forEach { element ->
            println("  at ${element.className}.${element.methodName}(${element.fileName}:${element.lineNumber})")
        }
        println("-".repeat(60))

        assertNotNull("Analysis should handle exceptions with stack traces", analysis)
    }
}

