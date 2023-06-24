import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.8.20"
	id("io.github.shayf0x.spigotwarden") version "1.0"
	`maven-publish`
	java
}

group = "fr.pickaria"
version = "1.0.10-SNAPSHOT"

SpigotWarden {
	minecraftVersion.set("1.20.1-R0.1-SNAPSHOT")
	buildOutput.set(file("server/plugins"))
}

repositories {
	maven("https://jitpack.io")
	maven("https://oss.sonatype.org/content/groups/public/")
	maven("https://repo.papermc.io/repository/maven-public/") // Paper
	mavenCentral()
	mavenLocal()
}

dependencies {
	compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT:remapped-mojang")
	compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
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
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/Pickaria/Spawner")
			credentials {
				username = System.getenv("GITHUB_ACTOR")
				password = System.getenv("GITHUB_TOKEN")
			}
		}
	}

	publications {
		create<MavenPublication>("maven") {
			artifactId = "spawner"
			from(components["java"])
		}
	}
}
