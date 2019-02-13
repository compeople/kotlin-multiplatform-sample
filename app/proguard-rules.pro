-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class de.compeople.swn.**$$serializer { *; } # <-- change package surname to your app's
-keepclassmembers class de.compeople.swn.** { # <-- change package surname to your app's
    *** Companion;
}
-keepclasseswithmembers class de.compeople.swn.** { # <-- change package surname to your app's
    kotlinx.serialization.KSerializer serializer(...);
}