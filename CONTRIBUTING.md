# Contributing to ClearCrash

First off, thanks for taking the time to contribute! 🎉

The following is a set of guidelines for contributing to ClearCrash. These are mostly guidelines,
not rules. Use your best judgment, and feel free to propose changes to this document in a pull
request.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
    - [Reporting Bugs](#reporting-bugs)
    - [Suggesting Enhancements](#suggesting-enhancements)
    - [Adding New Error Types](#adding-new-error-types)
    - [Pull Requests](#pull-requests)
- [Development Setup](#development-setup)
- [Style Guidelines](#style-guidelines)
- [Commit Messages](#commit-messages)

## Code of Conduct

This project and everyone participating in it is governed by a Code of Conduct. By participating,
you are expected to uphold this code. Please report unacceptable behavior
to [support@clearcrash.dev](mailto:support@clearcrash.dev).

**In short:** Be respectful, be kind, be professional.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues as you might find that you don't need
to create one. When you are creating a bug report, please include as many details as possible:

**Required information:**

- **Description**: Clear description of the bug
- **Steps to reproduce**: Step-by-step instructions
- **Expected behavior**: What you expected to happen
- **Actual behavior**: What actually happened
- **ClearCrash version**: Which version are you using?
- **Android version**: Device/emulator Android version
- **Logcat output**: Include relevant logs if possible

**Bug Report Template:**

```markdown
### Description

A clear description of the bug.

### Steps to Reproduce

1. Install ClearCrash
2. Trigger error X
3. See problem

### Expected Behavior

Error should be displayed as...

### Actual Behavior

Error is displayed as...

### Environment

- ClearCrash version: 1.0.0
- Android version: 13
- Device: Pixel 5
- Logcat output: (paste here)
```

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion,
please include:

- **Clear title**: Use a descriptive title
- **Detailed description**: Explain the enhancement and why it's useful
- **Examples**: Show how it would work with code examples
- **Alternatives**: Any alternative solutions you've considered

### Adding New Error Types

Want to add support for a new error type? Great! Here's how:

1. **Check if it's not already supported**: Look at
   the [list of supported errors](README.md#full-error-list)

2. **Open an issue first**: Discuss the error type you want to add

3. **Follow this structure** when implementing:
   ```kotlin
   // In ErrorAnalyzer.kt
   is YourExceptionType -> {
       errorType = "YourExceptionType"
       explanation = "Clear explanation of what happened"
       probableCause = """
           • Reason 1 why this happens
           • Reason 2 why this happens
       """.trimIndent()
       suggestedFixes = """
           1. First solution with code example
           2. Second solution with code example
           3. Third solution (if applicable)
       """.trimIndent()
   }
   ```

4. **Add tests**: Include test cases for the new error type

5. **Update documentation**: Add the error to README.md

### Pull Requests

1. **Fork the repo** and create your branch from `main`
2. **Make your changes**
3. **Add tests** if applicable
4. **Update documentation** if needed
5. **Ensure code style** follows guidelines
6. **Test thoroughly** on multiple Android versions
7. **Submit PR** with a clear description

**PR Title Format:**

- `feat: Add support for SQLiteException`
- `fix: Correct NPE detection for Kotlin`
- `docs: Update README installation steps`
- `refactor: Simplify error formatting`

## Development Setup

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 17
- Android SDK with API 21-34

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/RaulCatalinas/ClearCrash.git
   cd clearcrash
   ```

2. **Open in Android Studio**
    - File → Open → Select the `clearcrash` folder
    - Wait for Gradle sync

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run tests**
   ```bash
   ./gradlew test
   ```

### Project Structure

```
clearcrash/
├── clearcrash/              # Library module
│   ├── src/main/
│   │   ├── java/com/clearcrash/
│   │   │   ├── ClearCrash.kt           # Main entry point
│   │   │   ├── ClearCrashInitializer.kt # ContentProvider
│   │   │   ├── ErrorAnalyzer.kt        # Error analysis logic
│   │   │   └── ErrorFormatter.kt       # Format output
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── app/                     # Sample app for testing
└── README.md
```

## Style Guidelines

### Kotlin Code Style

We follow
the [official Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html):

**Key points:**

- Use 4 spaces for indentation
- Maximum line length: 120 characters
- Use meaningful variable names
- Add KDoc for public APIs
- Keep functions small and focused

**Example:**

```kotlin
/**
 * Analyzes an exception and generates an improved error message.
 *
 * @param throwable The exception to analyze
 * @return A formatted error message with explanation and fixes
 */
fun analyzeException(throwable: Throwable): String {
    // Implementation
}
```

### Documentation

- **Public APIs**: Must have KDoc comments
- **Complex logic**: Add inline comments explaining why, not what
- **README**: Keep examples simple and working
- **Comments**: Write clear, concise comments

### Error Messages

When writing error explanations:

- **Be clear**: Use simple language, avoid jargon
- **Be specific**: "Variable 'userName' is null" not "A variable is null"
- **Be helpful**: Provide actual solutions, not generic advice
- **Be concise**: Get to the point quickly

**Good:**

```
🔴 NullPointerException
Variable 'userName' is null when calling .length()

💡 FIX:
Use userName?.length() ?: 0
```

**Bad:**

```
Error: Something went wrong with your code.
Try fixing the null reference.
```

## Commit Messages

Use clear, descriptive commit messages:

### Format

```
<type>: <subject>

<body>

<footer>
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, no logic change)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

### Examples

**Good:**

```
feat: Add support for SQLiteException

- Detect common SQL errors
- Provide query debugging suggestions
- Add test cases

Closes #42
```

**Bad:**

```
update stuff
```

## Testing

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests "ErrorAnalyzerTest.testNullPointerException"
```

### Writing Tests

- Test edge cases
- Test on different Android versions
- Include both positive and negative test cases
- Use descriptive test names

**Example:**

```kotlin
@Test
fun shouldDetectNullPointerExceptionOnNullString() {
    val exception =
        NullPointerException("Attempt to invoke virtual method 'int java.lang.String.length()' on a null object reference")
    val result = ErrorAnalyzer.analyze(exception)

    assertTrue(result.contains("Variable") && result.contains("null"))
    assertTrue(result.contains("?."))
}
```

## Need Help?

- 💬 [GitHub Discussions](https://github.com/RaulCatalinas/ClearCrash/discussions)
- 🐛 [GitHub Issues](https://github.com/RaulCatalinas/ClearCrash/issues)
- 📧 Email: support@clearcrash.dev

## Recognition

Contributors will be:

- Listed in the README
- Credited in release notes
- Appreciated forever! 🎉

---

**Thank you for contributing to ClearCrash!** 🚀
