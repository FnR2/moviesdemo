plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
var retrofit = "2.9.0"
var interceptor = "4.9.3"
var gson = "2.10.1"
var coroutine = "1.7.3"
dependencies{
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine")
    api ("com.squareup.retrofit2:retrofit:$retrofit")
    api ("com.google.code.gson:gson:$gson")
    api ("com.squareup.retrofit2:converter-gson:$retrofit")
    api ("com.squareup.okhttp3:logging-interceptor:$interceptor")
    // Mockito + Coroutines için gerekli bağımlılıklar
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")

}