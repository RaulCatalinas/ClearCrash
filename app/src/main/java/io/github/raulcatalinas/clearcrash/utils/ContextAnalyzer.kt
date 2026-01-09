package io.github.raulcatalinas.clearcrash.utils

/**
 * Analyzes stack trace context to provide better error explanations.
 */
object ContextAnalyzer {
    /**
     * Determines if error happened in Activity lifecycle.
     */
    fun isInActivityLifecycle(location: StackTraceElement?): Boolean {
        return location?.methodName?.let {
            it.contains("onCreate") ||
                it.contains("onStart") ||
                it.contains("onResume") ||
                it.contains("onPause") ||
                it.contains("onStop") ||
                it.contains("onDestroy")
        } ?: false
    }

    /**
     * Determines if error happened in Fragment lifecycle.
     */
    fun isInFragmentLifecycle(location: StackTraceElement?): Boolean {
        return location?.methodName?.let {
            it.contains("onCreateView") ||
                it.contains("onViewCreated") ||
                it.contains("onDestroyView")
        } ?: false
    }

    /**
     * Determines if error happened in a click handler.
     */
    fun isInClickHandler(location: StackTraceElement?): Boolean {
        return location?.methodName?.let {
            it.contains("onClick") ||
                it.contains("click") ||
                it.contains("onItemClick")
        } ?: false
    }

    /**
     * Gets the lifecycle phase where error occurred.
     */
    fun getLifecyclePhase(location: StackTraceElement?): LifecyclePhase? {
        return location?.methodName?.let { method ->
            when {
                method.contains("onCreate") -> LifecyclePhase.ON_CREATE
                method.contains("onStart") -> LifecyclePhase.ON_START
                method.contains("onResume") -> LifecyclePhase.ON_RESUME
                method.contains("onPause") -> LifecyclePhase.ON_PAUSE
                method.contains("onStop") -> LifecyclePhase.ON_STOP
                method.contains("onDestroy") -> LifecyclePhase.ON_DESTROY
                method.contains("onCreateView") -> LifecyclePhase.FRAGMENT_ON_CREATE_VIEW
                method.contains("onViewCreated") -> LifecyclePhase.FRAGMENT_ON_VIEW_CREATED
                method.contains("onDestroyView") -> LifecyclePhase.FRAGMENT_ON_DESTROY_VIEW
                else -> null
            }
        }
    }

    enum class LifecyclePhase {
        ON_CREATE,
        ON_START,
        ON_RESUME,
        ON_PAUSE,
        ON_STOP,
        ON_DESTROY,
        FRAGMENT_ON_CREATE_VIEW,
        FRAGMENT_ON_VIEW_CREATED,
        FRAGMENT_ON_DESTROY_VIEW
    }
}
