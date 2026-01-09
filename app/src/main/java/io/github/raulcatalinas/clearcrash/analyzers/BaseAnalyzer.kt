package io.github.raulcatalinas.clearcrash.analyzers

import io.github.raulcatalinas.clearcrash.utils.MessageFormatter
import io.github.raulcatalinas.clearcrash.utils.findUserCode

/**
 * Base class for exception analyzers.
 * Provides common structure and utilities.
 */
abstract class BaseAnalyzer<T : Throwable> {
    /**
     * Analyzes the exception and returns formatted message.
     */
    fun analyze(exception: T): String {
        val message = exception.message ?: ""
        val userCode = exception.stackTrace.findUserCode()

        return MessageFormatter.buildErrorMessage(
            title = getTitle(),
            subtitle = getSubtitle(),
            whatHappened = extractWhatHappened(message, userCode),
            whyItHappened = determineWhyItHappened(message, userCode),
            howToFix = determineHowToFix(message, userCode),
            location = userCode,
            additionalInfo = extractAdditionalInfo(message, userCode)
        )
    }

    /**
     * Returns the error title (e.g., "NullPointerException")
     */
    protected abstract fun getTitle(): String

    /**
     * Returns the error subtitle (short description)
     */
    protected abstract fun getSubtitle(): String

    /**
     * Extracts what happened from the error.
     */
    protected abstract fun extractWhatHappened(
        message: String,
        location: StackTraceElement?
    ): String

    /**
     * Determines why it happened.
     */
    protected abstract fun determineWhyItHappened(
        message: String,
        location: StackTraceElement?
    ): List<String>

    /**
     * Determines how to fix it.
     */
    protected abstract fun determineHowToFix(
        message: String,
        location: StackTraceElement?
    ): List<String>

    /**
     * Extracts additional contextual info (optional).
     */
    protected open fun extractAdditionalInfo(
        message: String,
        location: StackTraceElement?
    ): String? = null
}
