# 🚀 ClearCrash

**Stop wasting time deciphering cryptic Android crashes. Get instant clear explanations, root
causes, and fixes.**

[![Maven Central](https://img.shields.io/maven-central/v/com.raulcatalinas.clearcrash/clearcrash.svg)](https://search.maven.org/search?q=g:%22com.clearcrash%22)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## ⚡ Quick Start (15 seconds)

### 1. Add dependency

```kotlin
dependencies {
    debugImplementation("com.raulcatalinas.clearcrash:clearcrash:1.0.0")
}
```

### 2. That's it! 🎉

**Zero code required.** ClearCrash auto-initializes and starts improving crashes immediately.

No `Application` class needed. No `install()` calls. Just add the dependency and it works.

---

## 📊 What You Get

### ❌ Standard Android

```
java.lang.NullPointerException: Attempt to invoke virtual method 
'int java.lang.String.length()' on a null object reference
    at com.example.MainActivity$onCreate$1.invoke(MainActivity.kt:47)
    at androidx.lifecycle.ViewModel.addCloseable(ViewModel.kt:432)
    ... 47 more lines of framework code ...
```

**Problem**: Confusing, unclear where YOUR code is, no solution

### ✅ With ClearCrash

```
🔴 NullPointerException - Attempted to use a null object

📋 WHAT HAPPENED:
You tried to call .length() on String 'userName' that is null

🔍 ROOT CAUSE:
• Variable 'userName' wasn't initialized
• getUserName() returned null

💡 HOW TO FIX:
1. Use safe call: userName?.length()
2. Provide default: userName?.length() ?: 0
3. Add null check: if (userName != null) { ... }

📍 IN YOUR CODE:
MainActivity.kt:47 in onCreate()
  val length = userName.length()  // ← userName is null
```

**Solution**: Crystal clear, actionable, shows YOUR code only

---

## ✨ Key Features

- ⚡ **Truly Zero Setup** - Auto-initializes via ContentProvider, no code needed
- 🎯 **Just Add Dependency** - Works instantly after Gradle sync
- 🧠 **Smart** - Recognizes 25+ Android error types with specific solutions
- 📍 **Focused** - Shows only YOUR code, filters out framework noise
- 💡 **Actionable** - Get real fixes, not generic advice
- 🪶 **Lightweight** - <50KB, zero dependencies, no performance impact
- 🔒 **Safe** - Works in debug, won't affect release builds

---

## 🎯 Supported Errors (25+)

| Error                          | What ClearCrash Tells You                      |
|--------------------------------|------------------------------------------------|
| `NullPointerException`         | Which variable is null + safe call suggestions |
| `ClassCastException`           | What types conflict + proper casting code      |
| `IndexOutOfBoundsException`    | Index vs actual size + bounds checking         |
| `IllegalStateException`        | What state is invalid + how to check it        |
| `ActivityNotFoundException`    | Which Intent failed + correct Intent code      |
| `NetworkOnMainThreadException` | Where network call is + coroutine examples     |
| `Resources.NotFoundException`  | Which resource is missing + how to verify      |
| `SQLiteException`              | SQL error + query debugging tips               |
| `OutOfMemoryError`             | What caused it + optimization strategies       |
| **And 16 more...**             | [See full list](#full-error-list)              |

---

## 🔧 How It Works

ClearCrash auto-initializes via ContentProvider and installs a global exception handler that:

1. Intercepts all unhandled exceptions
2. Analyzes the error type and context
3. Generates a clear, actionable explanation
4. Prints it to Logcat automatically

**No configuration needed. No manual logging. Just install and see better errors in Logcat.**

---

## 📋 More Examples

<details>
<summary><b>IndexOutOfBoundsException</b></summary>

**Before:**

```
java.lang.IndexOutOfBoundsException: Index: 5, Size: 3
at java.util.ArrayList.get(ArrayList.java:437)
... 38 more
```

**After:**

```
🔴 IndexOutOfBoundsException
Tried to access position 5 in list of size 3

🔍 ROOT CAUSE:
List has only 3 items, position 5 doesn't exist

💡 FIX:
1. Check size first: if (index < list.size) { ... }
2. Use getOrNull(5) for safe access
3. Verify loop bounds

📍 YOUR CODE:
UserAdapter.kt:24
  val user = userList.get(5)  // List only has 3 items
```

</details>

<details>
<summary><b>ClassCastException</b></summary>

**Before:**

```
java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Integer
```

**After:**

```
🔴 ClassCastException
Cannot convert String to Integer

🔍 ROOT CAUSE:
Trying to cast "123" (String) to Integer type

💡 FIX:
1. Use proper conversion: text.toIntOrNull()
2. Check type first: if (value is Integer) { ... }
3. Use when expression for safe casting

📍 YOUR CODE:
DataParser.kt:15
  val number = value as Integer  // value is String
```

</details>

<details>
<summary><b>NetworkOnMainThreadException</b></summary>

**Before:**

```
android.os.NetworkOnMainThreadException
at android.os.StrictMode$AndroidBlockGuardPolicy.onNetwork
```

**After:**

```
🔴 NetworkOnMainThreadException
Network call attempted on main/UI thread

🔍 ROOT CAUSE:
HTTP request in onCreate() blocks UI thread

💡 FIX:
1. Use coroutines:
   lifecycleScope.launch(Dispatchers.IO) {
       val data = api.fetchData()
   }

2. Use WorkManager for background work
3. Move to ViewModel with proper scope

📍 YOUR CODE:
MainActivity.kt:23
  val response = apiClient.get()  // Blocking call on main thread
```

</details>

---

## 🤔 FAQ

**Does this require any code changes?**  
No! Just add the Gradle dependency. ClearCrash auto-initializes and improved errors appear
automatically in Logcat.

**Does this impact performance?**  
No. Zero overhead during normal operation. Only activates when crashes occur.

**Can I use in production?**  
It's designed for debug builds. Use `debugImplementation` so it's only included during development.

**How does auto-initialization work?**  
ClearCrash uses Android's ContentProvider to initialize before your Application class runs. Same
technique as Firebase, WorkManager, etc.

**Works with Firebase Crashlytics?**  
Yes! ClearCrash only improves Logcat output. It doesn't interfere with crash reporting tools.

**Supports Jetpack Compose?**  
Yes! Works everywhere - Views, Compose, ViewModels, Repositories, Services, etc.

**What about obfuscated code?**  
Works with ProGuard/R8. Still provides helpful context even with shortened class names.

---

## 📦 Requirements

- Minimum SDK: 21 (Android 5.0)
- Kotlin 1.8+ or Java 8+
- Zero dependencies

---

## 🤝 Contributing

Contributions welcome! Ways to help:

- 🐛 [Report bugs](https://github.com/RaulCatalinas/ClearCrash/issues)
- 💡 Suggest new error types
- 🌍 Translate error messages
- 📖 Improve documentation
- ⭐ Star the repo

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

---

## 📄 License

MIT License - see [LICENSE](LICENSE) file for details.

---

## 💬 Support & Links

- 🐛 Issues: [GitHub Issues](https://github.com/RaulCatalinas/ClearCrash/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/RaulCatalinas/ClearCrash/discussions)

---

## Full Error List

ClearCrash provides enhanced messages for these errors:

1. `NullPointerException` - Null object access
2. `ClassCastException` - Invalid type casting
3. `IndexOutOfBoundsException` - Array/List index errors
4. `IllegalStateException` - Invalid state operations
5. `IllegalArgumentException` - Invalid method arguments
6. `ActivityNotFoundException` - Missing Activity for Intent
7. `Resources.NotFoundException` - Missing resource IDs
8. `NetworkOnMainThreadException` - Network on UI thread
9. `SQLiteException` - Database errors
10. `OutOfMemoryError` - Memory exhaustion
11. `NumberFormatException` - Invalid number parsing
12. `FileNotFoundException` - Missing files
13. `SecurityException` - Permission denied
14. `RuntimeException` - Generic runtime errors
15. `ArithmeticException` - Math errors (divide by zero)
16. `ConcurrentModificationException` - Collection modification during iteration
17. `InflateException` - XML layout inflation errors
18. `BadTokenException` - Invalid window token
19. `WindowManager.BadTokenException` - Dialog/Window errors
20. `JSONException` - JSON parsing errors
21. `ParseException` - Date/format parsing
22. `IOException` - Input/output errors
23. `SocketTimeoutException` - Network timeouts
24. `UnknownHostException` - DNS/connectivity
25. `SSLException` - Certificate/security errors

**And more being added!
** [Request a new error type →](https://github.com/RaulCatalinas/ClearCrash/issues)
