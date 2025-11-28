# Keep public API
-keep public class com.raulcatalinas.clearcrash.ClearCrash {
    public *;
}

# Keep the ContentProvider for auto-initialization
-keep public class com.raulcatalinas.clearcrash.ClearCrashInitializer {
    public *;
}
