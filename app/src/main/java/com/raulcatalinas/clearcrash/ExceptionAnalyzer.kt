package com.raulcatalinas.clearcrash

import com.raulcatalinas.clearcrash.utils.findUserCode
import com.raulcatalinas.clearcrash.utils.format

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
        return "TODO: NullPointerException analysis coming soon..."
    }

    private fun analyzeIndexOutOfBounds(exception: IndexOutOfBoundsException): String {
        return "TODO: IndexOutOfBoundsException analysis coming soon..."
    }

    private fun analyzeClassCast(exception: ClassCastException): String {
        return "TODO: ClassCastException analysis coming soon..."
    }

    private fun analyzeGeneric(throwable: Throwable): String {
        val userCode = throwable.stackTrace.findUserCode()

        return buildString {
            appendLine("🔴 ${throwable.javaClass.simpleName}")
            appendLine(throwable.message ?: "No error details available")
            appendLine()

            if (userCode != null) {
                appendLine("📍 IN YOUR CODE:")
                appendLine(userCode.format())
            }

            appendLine()
            appendLine("💡 This error type isn't specifically handled yet.")
            appendLine("Help us improve: https://github.com/RaulCatalinas/ClearCrash/issues")
        }
    }
}
