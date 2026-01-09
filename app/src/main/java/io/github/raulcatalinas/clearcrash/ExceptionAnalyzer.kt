package io.github.raulcatalinas.clearcrash

import io.github.raulcatalinas.clearcrash.utils.MessageFormatter
import io.github.raulcatalinas.clearcrash.utils.findUserCode

object ExceptionAnalyzer {

    fun analyze(throwable: Throwable): String {
        return when (throwable) {
            is NullPointerException -> analyzeNullPointer(throwable)
            is IndexOutOfBoundsException -> analyzeIndexOutOfBounds(throwable)
            is ClassCastException -> analyzeClassCast(throwable)
            else -> analyzeGeneric(throwable)
        }
    }

    private fun analyzeNullPointer(exception: NullPointerException): String {
        val message = exception.message ?: "No details available"
        val userCode = exception.stackTrace.findUserCode()
        val methodInfo = extractMethodFromNPEMessage(message)

        val whatHappened = if (methodInfo != null) {
            "Called method '${methodInfo.methodName}' on a ${methodInfo.typeName} that is null"
        } else {
            message
        }

        val whyItHappened = listOf(
            "Variable wasn't initialized",
            "Method returned null",
            "The object was set to null somewhere"
        )

        val howToFix = if (methodInfo != null) {
            listOf(
                "Use safe call: variable?.${methodInfo.methodName}",
                "Provide default: variable?.${methodInfo.methodName} ?: defaultValue",
                "Add null check: if (variable != null) { ... }",
                "Make the variable non-nullable: var name: String (without ?)"
            )
        } else {
            listOf(
                "Use safe call: variable?.method()",
                "Provide default: variable?.method() ?: defaultValue",
                "Add null check: if (variable != null) { ... }",
                "Make the variable non-nullable: var name: String (without ?)"
            )
        }

        return MessageFormatter.buildErrorMessage(
            title = "NullPointerException",
            subtitle = "You tried to use something that doesn't exist (is null)",
            whatHappened = whatHappened,
            whyItHappened = whyItHappened,
            howToFix = howToFix,
            location = userCode
        )
    }

    private fun extractMethodFromNPEMessage(message: String): MethodInfo? {
        val methodPattern = Regex("'[^']*\\s+([^.]+)\\.([^(]+)\\([^)]*\\)'")
        val match = methodPattern.find(message)

        return if (match != null && match.groupValues.size >= 3) {
            val className = match.groupValues[1]
            val methodName = match.groupValues[2]
            val simpleClassName = className.split(".").lastOrNull() ?: className

            MethodInfo(
                methodName = "$methodName()",
                typeName = simpleClassName
            )
        } else {
            null
        }
    }

    private data class MethodInfo(
        val methodName: String,
        val typeName: String
    )

    private fun analyzeIndexOutOfBounds(exception: IndexOutOfBoundsException): String {
        // TODO: Implementar
        return MessageFormatter.buildGenericMessage(
            errorType = "IndexOutOfBoundsException",
            message = exception.message,
            location = exception.stackTrace.findUserCode()
        )
    }

    private fun analyzeClassCast(exception: ClassCastException): String {
        // TODO: Implementar
        return MessageFormatter.buildGenericMessage(
            errorType = "ClassCastException",
            message = exception.message,
            location = exception.stackTrace.findUserCode()
        )
    }

    private fun analyzeGeneric(throwable: Throwable): String {
        return MessageFormatter.buildGenericMessage(
            errorType = throwable.javaClass.simpleName,
            message = throwable.message,
            location = throwable.stackTrace.findUserCode()
        )
    }
}
