package io.github.raulcatalinas.clearcrash.analyzers

import io.github.raulcatalinas.clearcrash.utils.ContextAnalyzer
import io.github.raulcatalinas.clearcrash.utils.ExceptionMessageParser

/**
 * Analyzes NullPointerException to provide clear, actionable explanations.
 */
object NullPointerAnalyzer : BaseAnalyzer<NullPointerException>() {

    override fun getTitle() = "NullPointerException"

    override fun getSubtitle() = "You tried to use something that doesn't exist (is null)"

    override fun extractWhatHappened(
        message: String,
        location: StackTraceElement?
    ): String {
        return when {
            message.contains("invoke virtual method") -> {
                val methodName = ExceptionMessageParser.extractMethodName(message)

                if (methodName != null) {
                    "Called .$methodName() on something that is null"
                } else {
                    "Tried to call a method on something that is null"
                }
            }

            message.contains("read from field") -> {
                val fieldName = ExceptionMessageParser.extractFieldName(message)

                if (fieldName != null) {
                    "Tried to access property '$fieldName' on something that is null"
                } else {
                    "Tried to access a property on something that is null"
                }
            }

            message.contains("write to field") -> {
                val fieldName = ExceptionMessageParser.extractFieldName(message)

                if (fieldName != null) {
                    "Tried to set property '$fieldName' on something that is null"
                } else {
                    "Tried to set a property on something that is null"
                }
            }

            message.contains("length of null array") -> {
                "Tried to get the length of an array that is null"
            }

            message.isBlank() -> {
                "Tried to use a variable or object that is null"
            }

            else -> {
                "Tried to use something that is null\nDetails: $message"
            }
        }
    }

    override fun determineWhyItHappened(
        message: String,
        location: StackTraceElement?
    ): List<String> {
        val reasons = mutableListOf<String>()

        // Use ContextAnalyzer for lifecycle-specific reasons
        when (ContextAnalyzer.getLifecyclePhase(location)) {
            ContextAnalyzer.LifecyclePhase.ON_CREATE -> {
                reasons.add("Variable wasn't initialized before use in onCreate()")
                reasons.add("View might not exist yet (called before setContentView?)")
                reasons.add("Intent extra or Bundle value might be missing")
            }

            ContextAnalyzer.LifecyclePhase.FRAGMENT_ON_VIEW_CREATED -> {
                reasons.add("View reference might not be set up properly")
                reasons.add("Fragment's view might be null")
            }

            else -> {
                if (ContextAnalyzer.isInClickHandler(location)) {
                    reasons.add("The clicked view's associated data might be null")
                    reasons.add("A reference needed by the click handler wasn't initialized")
                }
            }
        }

        // Pattern-based reasons
        when {
            message.contains("invoke virtual method") -> {
                if (reasons.isEmpty()) {
                    reasons.add("The object wasn't initialized before calling the method")
                }
                reasons.add("A function might have returned null unexpectedly")
                reasons.add("An optional value wasn't checked before use")
            }

            message.contains("read from field") || message.contains("write to field") -> {
                reasons.add("The object containing this property is null")
                reasons.add("The property wasn't initialized")
            }
        }

        // Fallback generic reasons
        if (reasons.isEmpty()) {
            reasons.add("Variable wasn't initialized before use")
            reasons.add("A function returned null when you expected a value")
            reasons.add("An object reference became null unexpectedly")
        }

        return reasons
    }

    override fun determineHowToFix(
        message: String,
        location: StackTraceElement?
    ): List<String> {
        val fixes = mutableListOf<String>()

        when {
            message.contains("invoke virtual method") -> {
                val methodName = ExceptionMessageParser.extractMethodName(message)
                if (methodName != null) {
                    fixes.add("Use safe call: variable?.$methodName()")
                    fixes.add("Check for null first: if (variable != null) { variable.$methodName() }")
                } else {
                    fixes.add("Use safe call operator: variable?.method()")
                    fixes.add("Check for null: if (variable != null) { ... }")
                }
                fixes.add("Use Elvis operator for default: variable?.method() ?: defaultValue")
                fixes.add("Ensure the object is initialized before use")
            }

            message.contains("read from field") -> {
                val fieldName = ExceptionMessageParser.extractFieldName(message)
                if (fieldName != null) {
                    fixes.add("Use safe access: object?.$fieldName")
                    fixes.add("Provide default: object?.$fieldName ?: defaultValue")
                } else {
                    fixes.add("Use safe property access: object?.property")
                    fixes.add("Check if object is null before accessing properties")
                }
                fixes.add("Initialize the object before accessing its properties")
            }

            message.contains("write to field") -> {
                fixes.add("Make sure the object is initialized before setting properties")
                fixes.add("Check for null: if (object != null) { object.property = value }")
                fixes.add("Use apply or let: object?.apply { property = value }")
            }

            message.contains("length of null array") -> {
                fixes.add("Check if array is null: if (array != null) { array.size }")
                fixes.add("Use safe call: array?.size ?: 0")
                fixes.add("Initialize the array before use")
            }

            else -> {
                fixes.add("Use Kotlin's safe call operator: variable?.method()")
                fixes.add("Check for null explicitly: if (variable != null) { ... }")
                fixes.add("Provide a default value: variable ?: defaultValue")
                fixes.add("Make sure the variable is initialized before use")
            }
        }

        return fixes
    }

    override fun extractAdditionalInfo(
        message: String,
        location: StackTraceElement?
    ): String? {
        return when {
            message.contains("findViewById") || message.contains("View") -> {
                "💡 TIP: If working with Views, make sure you call findViewById() AFTER setContentView()"
            }

            message.contains("Intent") || message.contains("getIntent") -> {
                "💡 TIP: When getting Intent extras, always provide a default value or check for null"
            }

            message.contains("Fragment") -> {
                "💡 TIP: Fragment views can be null. Use viewLifecycleOwner and check lifecycle state"
            }

            message.contains("Activity") && message.contains("null") -> {
                "💡 TIP: Make sure you're not accessing Activity/Context after it's been destroyed"
            }

            else -> null
        }
    }
}
