import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application

	kotlin("jvm")
	kotlin("plugin.serialization")

	id("com.github.johnrengelman.shadow")
	id("io.gitlab.arturbosch.detekt")
}

group = "mirko"
version = "1.0-SNAPSHOT"

repositories {
	google()
	mavenCentral()

	maven {
		name = "Sonatype Snapshots (Legacy)"
		url = uri("https://oss.sonatype.org/content/repositories/snapshots")
	}

	maven {
		name = "Sonatype Snapshots"
		url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
	}
}

dependencies {
	detektPlugins(libs.detekt)

	implementation(libs.kord.extensions)
	implementation(libs.kotlin.stdlib)
	implementation(libs.kx.ser)

	// Logging dependencies
	implementation(libs.groovy)
	implementation(libs.jansi)
	implementation(libs.logback)
	implementation(libs.logback.groovy)
	implementation(libs.logging)

	// MongoDB driver dependency
	implementation("org.mongodb", "mongo-java-driver", "3.12.10")

	// YouTube Client API Library
	implementation("com.google.apis", "google-api-services-youtube", "v3-rev20210915-1.32.1")
}

application {
	mainClass.set("mirko.AppKt")
}

tasks.withType<KotlinCompile> {
	// Current LTS version of Java
	kotlinOptions.jvmTarget = "17"

	kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

tasks.jar {
	manifest {
		attributes(
			"Main-Class" to "mirko.AppKt"
		)
	}
}

java {
	// Current LTS version of Java
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

detekt {
	buildUponDefaultConfig = true

	config.from(rootProject.files("detekt.yml"))
}