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
        // Orden recomendado: primero los oficiales
        google()
        mavenCentral()
        // JitPack como fallback
        maven("https://jitpack.io")
    }
}

rootProject.name = "PPT_MuniC"
include(":app")
