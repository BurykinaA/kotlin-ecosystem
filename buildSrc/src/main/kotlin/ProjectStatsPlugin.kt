import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File
import org.gradle.api.DefaultTask
import javax.inject.Inject
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory


class ProjectStatsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("generateProjectStats", GenerateProjectStatsTask::class.java)
    }
}

abstract class GenerateProjectStatsTask : DefaultTask() {
    @InputDirectory
    val rootDirProperty = project.layout.projectDirectory

    @OutputDirectory
    val buildDirProperty = project.layout.buildDirectory.dir("reports")

    init {
        group = "statistics"
        description = "Generates statistics about the project"
    }

    @TaskAction
    fun generateStats() {
        val stats = collectProjectStats()
        generateReport(stats)
    }

    private fun collectProjectStats(): ProjectStats {
        val sourceFiles = project.fileTree(rootDirProperty.asFile).matching {
            include("**/*.kt", "**/*.java", "**/*.kts")
            exclude("**/build/**")
        }

        val classFiles = project.fileTree(rootDirProperty.asFile).matching {
            include("**/build/**/*.class")
        }

        val gitInfo = try {
            getGitInfo()
        } catch (e: Exception) {
            GitInfo("N/A", "N/A")
        }

        return ProjectStats(
            sourceFilesCount = sourceFiles.count(),
            classFilesCount = classFiles.count(),
            lastCommit = gitInfo.lastCommit,
            lastCommitAuthor = gitInfo.author
        )
    }

    private fun getGitInfo(): GitInfo {
        FileRepositoryBuilder().setGitDir(File(rootDirProperty.asFile, ".git")).build().use { repository ->
            val git = Git(repository)
            val logs = git.log().setMaxCount(1).call()
            val lastCommit = logs.iterator().next()

            return GitInfo(
                lastCommit = lastCommit.name.substring(0, 7),
                author = lastCommit.authorIdent.name
            )
        }
    }

    private fun generateReport(stats: ProjectStats) {
        val reportFile = buildDirProperty.get().asFile.resolve("project-stats.txt")
        reportFile.writeText("""
            Project Statistics
            =================
            Source Files: ${stats.sourceFilesCount}
            Class Files: ${stats.classFilesCount}
            Last Commit: ${stats.lastCommit}
            Author: ${stats.lastCommitAuthor}
        """.trimIndent())

        println("Report generated at: ${reportFile.absolutePath}")
    }

    data class ProjectStats(
        val sourceFilesCount: Int,
        val classFilesCount: Int,
        val lastCommit: String,
        val lastCommitAuthor: String
    )

    data class GitInfo(
        val lastCommit: String,
        val author: String
    )
}