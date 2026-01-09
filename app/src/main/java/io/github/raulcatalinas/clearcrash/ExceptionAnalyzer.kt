package io.github.raulcatalinas.clearcrash

import io.github.raulcatalinas.clearcrash.analyzers.NullPointerAnalyzer
import io.github.raulcatalinas.clearcrash.utils.MessageFormatter
import io.github.raulcatalinas.clearcrash.utils.findUserCode

object ExceptionAnalyzer {
    fun analyze(throwable: Throwable): String {
        return when (throwable) {
            is NullPointerException -> NullPointerAnalyzer.analyze(throwable)
            is IndexOutOfBoundsException -> analyzeIndexOutOfBounds(throwable)
            is ClassCastException -> analyzeClassCast(throwable)
            else -> analyzeGeneric(throwable)
        }
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
