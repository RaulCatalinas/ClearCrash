package com.raulcatalinas.clearcrash.utils

/**
 * Formats error messages with consistent structure and styling.
 */
object MessageFormatter {
    /**
     * Builds a complete error analysis message.
     *
     * @param title Error type (e.g., "NullPointerException")
     * @param subtitle Short description of the error
     * @param whatHappened Detailed explanation of what went wrong
     * @param whyItHappened List of possible causes
     * @param howToFix List of suggested fixes
     * @param location Stack trace element pointing to user code
     * @param additionalInfo Optional extra information
     * @return Formatted error message
     */
    fun buildErrorMessage(
        title: String,
        subtitle: String,
        whatHappened: String,
        whyItHappened: List<String> = emptyList(),
        howToFix: List<String> = emptyList(),
        location: StackTraceElement? = null,
        additionalInfo: String? = null
    ): String {
        return buildString {
            // Header
            appendLine("🔴 $title")
            appendLine(subtitle)
            appendLine()

            // What happened section
            appendLine("📋 WHAT HAPPENED:")
            appendLine(whatHappened)
            appendLine()

            // Why it happened section
            if (whyItHappened.isNotEmpty()) {
                appendLine("🔍 WHY IT HAPPENED:")

                whyItHappened.forEach { reason ->
                    appendLine("• $reason")
                }

                appendLine()
            }

            // How to fix section
            if (howToFix.isNotEmpty()) {
                appendLine("💡 HOW TO FIX:")

                howToFix.forEachIndexed { index, fix ->
                    appendLine("${index + 1}. $fix")
                }

                appendLine()
            }

            // Additional info section (optional)
            if (additionalInfo != null) {
                appendLine("ℹ️  ADDITIONAL INFO:")
                appendLine(additionalInfo)
                appendLine()
            }

            // Location section
            if (location != null) {
                appendLine("📍 IN YOUR CODE:")
                appendLine(location.format())
            }
        }
    }

    /**
     * Builds a simple error message for unhandled error types.
     */
    fun buildGenericMessage(
        errorType: String,
        message: String?,
        location: StackTraceElement?
    ): String {
        return buildString {
            appendLine("🔴 $errorType")
            appendLine(message ?: "No error details available")
            appendLine()

            if (location != null) {
                appendLine("📍 IN YOUR CODE:")
                appendLine(location.format())
            }

            appendLine()
            appendLine("💡 This error type isn't specifically handled yet.")
            appendLine("Help us improve: https://github.com/RaulCatalinas/ClearCrash/issues")
        }
    }
}
