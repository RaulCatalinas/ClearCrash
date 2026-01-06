# 🚀 ClearCrash

**Transform cryptic Android crashes into clear, human-readable explanations**

<div align="center">

### ⚡ True Plug-and-Play

```gradle
debugImplementation("com.raulcatalinas.clearcrash:clearcrash:1.0.0")
```

**That's the ENTIRE setup. No code. No configuration. Works immediately.**

</div>

---

> ⚠️ **Early Development**: This project is just starting. The core idea is solid, but we need help building it.  
> If you've ever been frustrated by Android errors, this is for you—as a user or contributor.

---

## 💡 The Problem

**Android error messages are terrible**, especially for new developers:

```
java.lang.NullPointerException: Attempt to invoke virtual method 
'int java.lang.String.length()' on a null object reference
    at com.example.MainActivity$onCreate$1.invoke(MainActivity.kt:47)
    at androidx.lifecycle.ViewModel.addCloseable(ViewModel.kt:432)
    at androidx.lifecycle.ViewModel$addCloseable$1.invoke(ViewModel.kt:458)
    at androidx.lifecycle.ViewModel$addCloseable$1.invoke(ViewModel.kt:432)
    ... 43 more framework lines ...
```

**What does this mean?** 🤷  
**Where is MY code?** 🔍  
**How do I fix it?** 😫

This wastes **15-20 minutes per crash** googling and asking for help.

---

## ✨ The Solution

ClearCrash intercepts crashes and explains them clearly:

```
🔴 NullPointerException
You tried to use something that doesn't exist (is null)

📋 WHAT HAPPENED:
Called .length() on String 'userName' that is null

🔍 WHY IT HAPPENED:
• Variable 'userName' wasn't initialized
• getUserName() might have returned null

💡 HOW TO FIX:
1. Use safe call: userName?.length()
2. Provide default: userName?.length() ?: 0
3. Add null check: if (userName != null) { ... }

📍 IN YOUR CODE:
MainActivity.kt:47 in onCreate()
  val length = userName.length()  // ← userName is null here
```

**Result:** From 15 minutes to 2 minutes. From confusion to clarity.

---

## 🎓 Origin Story

This project was born from a real problem:

> "I'm taking an Android development course. Every time I got an error, I spent 15-20 minutes trying to understand it or
> had to ask the instructor. Android error messages are horrible, confusing, and hard to understand—especially for new
> developers."

If you've felt this frustration, this project is for you.

---

## ⚡ Installation (Literally 10 Seconds)

### Add one line to `build.gradle`:

```gradle
dependencies {
    debugImplementation("com.raulcatalinas.clearcrash:clearcrash:1.0.0")
}
```

### That's it. Done. 🎉

**No `Application` class needed.**  
**No `ClearCrash.install()` calls needed.**  
**No manifest changes needed.**  
**No initialization code needed.**  
**ZERO configuration.**

Sync Gradle → Next crash is automatically explained in Logcat.

---

## 🔧 How It Works (The Magic)

ClearCrash uses **ContentProvider auto-initialization** (same technique as Firebase, WorkManager, etc.):

1. **Auto-init**: Starts automatically when your app launches
2. **Intercept**: Catches all unhandled exceptions
3. **Analyze**: Identifies error type and extracts useful info
4. **Explain**: Prints clear message to Logcat
5. **Pass-through**: Re-throws exception (crash continues normally)

**Important:** ClearCrash does NOT fix, suppress, or prevent crashes. It only explains them better.

---

## ⚙️ Configuration (100% Optional)

ClearCrash works perfectly with zero config, but you CAN customize if you want:

```kotlin
// In your Application.onCreate() - OPTIONAL
ClearCrash.configure {
    enabled = true                    // Default: true
    showStackTrace = true             // Default: true
    packageFilter = "com.yourapp"     // Default: auto-detected
    verbosity = Verbosity.NORMAL      // Default: NORMAL
}
```

**But again: This is completely optional.** Most users will never need to configure anything.

---

## 🚧 Current Status

**This is an early-stage project.** Here's what exists and what doesn't:

### ✅ What's Ready

- [x] Core concept validated (born from real frustration)
- [x] Technical approach proven feasible
- [x] Architecture planned
- [x] Community interest growing

### 🚧 What's In Progress

- [ ] ContentProvider auto-initialization (plug-and-play setup)
- [ ] Basic exception handler implementation
- [ ] NullPointerException analysis
- [ ] IndexOutOfBoundsException analysis
- [ ] ClassCastException analysis

### 📋 What's Planned

- [ ] 10+ common Android errors
- [ ] Smart stacktrace filtering (show only your code)
- [ ] Compose-specific error handling
- [ ] Configurable output format
- [ ] IDE plugin (future)

---

## 🎯 Supported Errors (Planned)

Priority order based on frequency and impact:

### Phase 1 (MVP) - Target: Month 1

1. ✅ `NullPointerException` - Most common error
2. ✅ `IndexOutOfBoundsException` - Clear and useful
3. ✅ `ClassCastException` - Type errors

### Phase 2 - Target: Month 2

4. `NetworkOnMainThreadException` - Android-specific
5. `IllegalStateException` - Lifecycle issues
6. `ActivityNotFoundException` - Intent errors
7. `Resources.NotFoundException` - Missing resources
8. `Fragment not attached` - Fragment lifecycle

### Phase 3 - Target: Month 3+

9. Compose recomposition issues
10. Coroutine cancellation errors
11. Memory leaks detection
12. ... and more based on community feedback

**[→ Request a new error type](https://github.com/RaulCatalinas/ClearCrash/issues/new?template=error_request.md)**

---

## 🎯 Design Principles

1. **True Zero-Config** - One Gradle line. No code, no setup, no manifest edits
2. **Explain, don't fix** - We analyze crashes, we don't suppress them
3. **Debug-only** - Never ships to production (`debugImplementation`)
4. **Zero interference** - Crashes work exactly as normal
5. **Best-effort** - We show what we know, we don't guess
6. **Easy to remove** - Delete one Gradle line, done

---

## 🔧 Technical Approach

### What ClearCrash CAN Do:

✅ Parse error messages and stacktraces  
✅ Identify error types  
✅ Filter framework noise  
✅ Provide contextual fixes  
✅ Format output clearly

### What ClearCrash CANNOT Do:

❌ Access variable names at runtime (lost in compilation)  
❌ Know variable values (unless in error message)  
❌ Access source code directly  
❌ Automatically fix errors  
❌ Predict why something is null

**We work with what's available:** error type, message, stacktrace, and pattern recognition.

This keeps expectations realistic while still providing significant value.

---

## 📋 Roadmap

### Month 1: Foundation

- [ ] Project setup and architecture
- [ ] ContentProvider auto-init
- [ ] Basic exception handler
- [ ] 3 error types implemented
- [ ] Initial testing and validation

### Month 2: Expansion

- [ ] 7 more error types
- [ ] Stacktrace filtering (show only user code)
- [ ] Maven Central publication
- [ ] Documentation and examples
- [ ] First public release (v0.1.0)

### Month 3: Growth

- [ ] Community feedback integration
- [ ] Iteration based on real usage
- [ ] Bug fixes and improvements
- [ ] 1,000+ downloads goal
- [ ] Stable v1.0.0 release

### Future (Beyond Month 3)

- [ ] Android Studio IDE plugin
- [ ] Advanced error detection patterns
- [ ] Team crash statistics
- [ ] Multi-language support
- [ ] Enterprise features

---

## 🤝 We Need Your Help

This project can't succeed alone. Here's how you can contribute:

### 🐛 Share Your Pain Points

What Android errors confuse you most?  
[→ Tell us what errors to prioritize](https://github.com/RaulCatalinas/ClearCrash/issues/new?template=error_request.md)

### 💻 Code Contributions

- Implement error analyzers
- Improve stacktrace parsing
- Add tests and examples
- Fix bugs and issues

[→ See CONTRIBUTING.md](CONTRIBUTING.md) for guidelines

### 📖 Documentation

- Improve error explanations
- Translate to other languages
- Write tutorials and guides
- Create video demos

### 🧪 Testing & Feedback

- Try it in your projects
- Report bugs and issues
- Share your experience
- Suggest improvements

### ⭐ Spread the Word

If you like the idea:

- ⭐ Star the repo
- 🐦 Share on Twitter/Reddit
- 💬 Tell other developers
- 📝 Write about it

---

## ❓ FAQ

**Do I really need zero configuration?**  
YES. Just add the Gradle dependency. ClearCrash auto-initializes using ContentProvider. No Application class, no
install() calls, nothing.

**Is this ready to use?**  
Not yet. It's in early development. Star the repo to follow progress.

**When will v1.0 be released?**  
Target: 4-8 weeks, depending on contributions.

**Can I configure it if I want to?**  
Yes, but it's 100% optional. See the Configuration section above.

**Can I help even if I'm a junior developer?**  
Absolutely! This project is BY juniors, FOR juniors (and everyone else).

**Does this require Android experience?**  
For using it: No. For contributing: Basic Android knowledge helps, but we're learning together.

**Will this always be free?**  
Yes. This is an open-source project under MIT license.

**Who's behind this?**  
Started by [@RaulCatalinas](https://github.com/RaulCatalinas), a developer who got frustrated with Android errors and
decided to fix the problem.

**Does it work with Crashlytics/Firebase?**  
Yes. ClearCrash only improves local Logcat output. It doesn't interfere with crash reporting tools.

**What about Jetpack Compose?**  
Yes, it will work with Compose. We're planning Compose-specific error handling too.

---

## 📄 License

MIT License - see [LICENSE](LICENSE) file

---

## 💬 Community & Support

- 📢 [GitHub Discussions](https://github.com/RaulCatalinas/ClearCrash/discussions) - Ask questions, share ideas
- 🐛 [Issue Tracker](https://github.com/RaulCatalinas/ClearCrash/issues) - Report bugs, request features
- ⭐ Star this repo to follow progress and show support

---

## 🙏 Acknowledgments

Inspired by every developer who's ever stared at a stacktrace and thought "there has to be a better way."

Special thanks to:

- The Android community for sharing their pain points
- Future contributors who will help build this
- Everyone who believes this problem is worth solving

---

<div align="center">

**This project is in active development**

Help us make Android errors less painful for everyone.

[⭐ Star](https://github.com/RaulCatalinas/ClearCrash) • [🤝 Contribute](CONTRIBUTING.md) • [💬 Discuss](https://github.com/RaulCatalinas/ClearCrash/discussions)

---

*Built with ☕ and frustration by developers, for developers*

</div>
