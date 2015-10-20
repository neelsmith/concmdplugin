package edu.holycross.shot

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class TestConcMdPlugin {
    @Test
    public void addTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'edu.holycross.shot.concmd'

        assertTrue(project.tasks.hello instanceof ConcMdTask)
    }
}
