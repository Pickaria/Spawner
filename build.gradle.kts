import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.20"
	id("io.github.shayf0x.spigotwarden") version "1.0"
	`maven-publish`
	java
}

group = "fr.pickaria"
version = "1.0.3-SNAPSHOT"

SpigotWarden {
	minecraftVersion.set("1.19.3-R0.1-SNAPSHOT")
	buildOutput.set(file("server/plugins"))
}

repositories {
	maven("https://jitpack.io")
	mavenCentral()
	mavenLocal()
}

dependencies {
	implementation("org.spigotmc:spigot:1.19.3-R0.1-SNAPSHOT:remapped-mojang")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

java {
	withSourcesJar()
	withJavadocJar()
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

publishing {
	repositories {
		maven {
			url = uri("https://maven.quozul.dev/snapshots")
			credentials(PasswordCredentials::class)
		}
	}

	publications {
		create<MavenPublication>("maven") {
			groupId = "fr.pickaria"
			artifactId = "spawner"
			version = "1.0.3-SNAPSHOT"

			from(components["java"])
		}
	}
}
