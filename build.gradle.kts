import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.20"
	id("io.github.shayf0x.spigotwarden") version "1.0"
	`maven-publish`
}

group = "fr.pickaria"
version = "1.0-SNAPSHOT"

SpigotWarden {
	buildOutput.set(file("/home/erwan/Documents/GitHub/Pickaria/plugin-collection/server/plugins"))
}

repositories {
	maven("https://jitpack.io")
	mavenCentral()
	mavenLocal()
}

dependencies {
	implementation("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT:remapped-mojang")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = "fr.pickaria"
			artifactId = "shopapi"
			version = version

			from(components["java"])
		}
	}
}