plugins {
id("epicfight-base")
alias(libs.plugins.moddevgradle)
alias(libs.plugins.mcSafeResources)
}
val neoforgeVersion: String = libs.versions.neoforge.get()
configureBaseArchive("neoforge")
neoForge {
version = neoforgeVersion
// 1.21.11 uses official Mojang mappings; do not reuse the 1.21.1 Parchment coordinate.
runs {
create("client") {
client()
systemProperty("neoforge.enabledGameTestNamespaces", modId)
devLogin = true
}
create("server") {
server()
systemProperty("neoforge.enabledGameTestNamespaces", modId)
}
create("gameTestServer") {
type = "gameTestServer"
systemProperty("neoforge.enabledGameTestNamespaces", modId)
}
create("data") {
data()
programArguments.addAll("--mod", modId, "--all", "--output", file("src/generated/resources/").absolutePath, "--existing", file("src/main/resources/").absolutePath)
}
configureEach {
systemProperty("forge.logging.markers", "REGISTRIES")
logLevel = org.slf4j.event.Level.DEBUG
}
}
mods {
register(modId) {
sourceSet(sourceSets.main.get())
}
}
}
mcSafeResources {
namespace.set(modId)
outputPackage.set("yesman.${modId}.generated")
}
java.sourceSets.main.get().java.srcDirs(tasks.generateLangKeys.map { it.outputs.files.singleFile }, tasks.generateSoundKeys.map { it.outputs.files.singleFile })
tasks.compileJava {
dependsOn(tasks.generateLangKeys, tasks.generateSoundKeys)
}
sourceSets.main {
resources.srcDir(layout.projectDirectory.dir("src/generated/resources"))
}
configurations {
val localRuntime by creating
named("runtimeClasspath") {
extendsFrom(localRuntime)
}
}
dependencies {
val localRuntime by configurations.getting
compileOnly(libs.jei.common.api)
compileOnly(libs.jei.neoforge.api)
localRuntime(libs.jei.neoforge)
compileOnly(libs.azurelib)
compileOnly(libs.azurelib.armor)
compileOnly(libs.geckolib)
compileOnly(libs.sodium)
compileOnly(libs.iris)
compileOnly(libs.werewolves)
compileOnly(libs.vampirism)
compileOnly(libs.curios)
compileOnly(libs.playerAnimator)
compileOnly(libs.kubejs)
compileOnly(libs.rhino)
implementation(libs.architectury)
implementation(libs.bundles.simply)
compileOnly("libs:TRansition:1.0.6")
compileOnly("libs:TRender:1.0.7")
compileOnly(libs.skinlayers)
compileOnly(libs.firstPersonModel)
compileOnly(libs.shoulderSurfing)
compileOnly(libs.controlify) {
isTransitive = false
}
compileOnly(libs.fgm)
implementation(libs.creativeCore)
implementation(libs.playerRevive)
}
tasks.processResources {
val replaceProperties = modPlatformMetadataReplaceProperties
inputs.properties(replaceProperties)
filesMatching("META-INF/" + 42.toChar() + ".mods.toml") {
expand(replaceProperties)
}
}
val apiPackage = "yesman/epicfight/api/" + 42.toChar() + 42.toChar()
val apiJarClassifier = "api"
val apiJar by tasks.registering(Jar::class) {
group = "build"
archiveClassifier.set(apiJarClassifier)
from(sourceSets.main.get().output) {
include(apiPackage)
}
}
val apiSourcesJar by tasks.registering(Jar::class) {
group = "build"
archiveClassifier.set("$apiJarClassifier-sources")
from(sourceSets.main.get().allSource) {
include(apiPackage)
}
}
artifacts {
archives(apiJar)
archives(apiSourcesJar)
}
configureModPublish(ModLoader.NeoForge) {
tasks.jar.get().archiveFile
}
