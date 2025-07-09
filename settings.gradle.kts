pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "EconomyApp"

include(":app")
include(":core")
include(":core:base")
include(":core:domain")
include(":core:ui")
include(":core:navigation")
include(":core:data")
include(":features")
include(":features:account")
include(":features:articles")
include(":features:expenses")
include(":features:histories")
include(":features:income")
include(":features:settings")
