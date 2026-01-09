package io.github.raulcatalinas.clearcrash.utils

/**
 * Parses common patterns in Android exception messages.
 */
object ExceptionMessageParser {
    /**
     * Extracts method name from exception message.
     *
     * Example: "invoke virtual method 'int java.lang.String.length()'" -> "length"
     */
    fun extractMethodName(message: String): String? {
        val pattern = """'[^']*\.(\w+)\([^)]*\)'""".toRegex()

        return pattern.find(message)?.groupValues?.getOrNull(1)
    }

    /**
     * Extracts field/property name from exception message.
     *
     * Example: "read from field 'java.lang.String MainActivity.userName'" -> "userName"
     */
    fun extractFieldName(message: String): String? {
        val pattern = """field '[\w.]+ [\w.]+\.(\w+)'""".toRegex()

        return pattern.find(message)?.groupValues?.getOrNull(1)
    }

    /**
     * Extracts class name from exception message.
     *
     * Example: "java.lang.String cannot be cast to java.lang.Integer" -> ("String", "Integer")
     */
    fun extractClassCastTypes(message: String): Pair<String?, String?>? {
        val pattern = """([\w.]+)\s+cannot be cast to\s+([\w.]+)""".toRegex()
        val match = pattern.find(message)

        if (match == null || match.groupValues.size < 3) return null

        val fromType = match.groupValues[1].split(".").lastOrNull()
        val toType = match.groupValues[2].split(".").lastOrNull()

        return Pair(fromType, toType)
    }

    /**
     * Extracts index and size from IndexOutOfBoundsException message.
     *
     * Example: "Index: 5, Size: 3" -> (5, 3)
     */
    fun extractIndexAndSize(message: String): Pair<Int?, Int?>? {
        val pattern = """Index:\s*(\d+).*Size:\s*(\d+)""".toRegex()
        val match = pattern.find(message)

        if (match == null || match.groupValues.size < 3) return null

        val index = match.groupValues[1].toIntOrNull()
        val size = match.groupValues[2].toIntOrNull()

        return Pair(index, size)
    }

    /**
     * Simplifies fully qualified class name.
     *
     * Example: "java.lang.String" -> "String"
     */
    fun simplifyClassName(fullClassName: String): String {
        return fullClassName.split(".").lastOrNull() ?: fullClassName
    }
}
