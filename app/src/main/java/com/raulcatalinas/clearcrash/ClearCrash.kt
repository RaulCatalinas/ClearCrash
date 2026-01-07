package com.raulcatalinas.clearcrash

import android.content.Context
import android.util.Log

/**
 * Main ClearCrash class that handles exception interception and analysis.
 *
 * Automatically initialized via ContentProvider - no manual setup required.
 */
object ClearCrash {
    private const val TAG = "ClearCrash"

    @Volatile
    private var isInitialized = false

    private var defaultHandler: Thread.UncaughtExceptionHandler? = null
    private var appContext: Context? = null

    /**
     * Installs ClearCrash exception handler.
     * Called automatically by ClearCrashInitializer.
     */
    internal fun install(context: Context) {
        if (isInitialized) {
            Log.w(TAG, "ClearCrash already initialized, skipping")
            return
        }

        try {
            appContext = context.applicationContext
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

            Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                handleException(thread, throwable)
            }

            isInitialized = true
            Log.d(TAG, "✓ ClearCrash installed successfully")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to install ClearCrash", e)
        }
    }

    private fun handleException(thread: Thread, throwable: Throwable) {
        try {
            val enhancedMessage = ExceptionAnalyzer.analyze(throwable)

            // Print with visual separators
            Log.e(TAG, "\n" + "=".repeat(60))
            Log.e(TAG, enhancedMessage)
            Log.e(TAG, "=".repeat(60) + "\n")

        } catch (e: Exception) {
            Log.e(
                TAG,
                "ClearCrash failed to analyze exception (Please report the bug at: https://github.com/RaulCatalinas/ClearCrash)",
                e
            )
        } finally {
            // CRITICAL: Always pass to original handler
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}
