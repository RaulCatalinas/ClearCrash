package com.raulcatalinas.clearcrash.utils

/**
 * Extension functions for working with stack traces.
 */

/**
 * Finds the first stack trace element that belongs to user code
 * (not Android framework or common libraries).
 */
fun Array<StackTraceElement>.findUserCode(): StackTraceElement? {
    return firstOrNull { it.isUserCode() }
}

/**
 * Gets all user code elements from the stack trace.
 */
fun Array<StackTraceElement>.findAllUserCode(): List<StackTraceElement> {
    return filter { it.isUserCode() }
}

/**
 * Checks if this stack trace element belongs to user code.
 */
private fun StackTraceElement.isUserCode(): Boolean {
    return !className.startsWith("android.") &&
        !className.startsWith("androidx.") &&
        !className.startsWith("java.") &&
        !className.startsWith("javax.") &&
        !className.startsWith("kotlin.") &&
        !className.startsWith("kotlinx.") &&
        !className.startsWith("dalvik.") &&
        !className.startsWith("com.android.") &&
        !className.startsWith("sun.") &&
        !className.startsWith("com.google.android.")
}

/**
 * Formats this stack trace element into a readable string.
 *
 * Example: "MainActivity.kt:42 in onCreate()"
 */
fun StackTraceElement.format(): String {
    return "$fileName:$lineNumber in $methodName()"
}
