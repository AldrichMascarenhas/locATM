# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\AndroidStudioSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepattributes Signature
-keepattributes *Annotation*


# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class com.nerdcutlet.atmfinder.model.** {
  *;
}

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-dontwarn okio.**

-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.** { *; }

-keep class android.location.** { *; }

-keepnames class com.google.android.maps.** {*;}
-keep public class com.google.android.maps.** {*;}

-dontwarn com.google.android.maps.GeoPoint
-dontwarn com.google.android.maps.MapActivity
-dontwarn com.google.android.maps.MapView
-dontwarn com.google.android.maps.MapController
-dontwarn com.google.android.maps.Overlay

-keep class android.support.v7.widget.LinearLayoutManager { *; }
-keep class com.nerdcutlet.atmfinder.viewholders.** { *; }

