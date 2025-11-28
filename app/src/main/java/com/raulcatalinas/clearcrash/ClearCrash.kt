package com.raulcatalinas.clearcrash

import android.content.Context
import android.util.Log

/**
 * ClearCrash - Transform cryptic Android crashes into clear, actionable error messages.
 *
 * This library auto-initializes via ContentProvider. No manual setup required.
 */
object ClearCrash {

    private const val TAG = "ClearCrash"
    private const val VERSION = "1.0.0"
    private var isInitialized = false

    /**
     * Initializes ClearCrash. Called automatically by ClearCrashInitializer.
     * You don't need to call this manually.
     */
    fun install(context: Context) {
        if (isInitialized) {
            Log.w(TAG, "ClearCrash is already initialized")
            return
        }

        isInitialized = true

        // Print to console for visibility
        println("════════════════════════════════════════════════════════════")
        println("🚀 ClearCrash v$VERSION initialized successfully!")
        println("   Better crash messages enabled in Logcat")
        println("════════════════════════════════════════════════════════════")

        // Also log to Logcat
        Log.i(TAG, "ClearCrash v$VERSION initialized - Better crash messages enabled")
    }
}
