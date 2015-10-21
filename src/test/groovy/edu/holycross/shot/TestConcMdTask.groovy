package edu.holycross.shot

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class TestConcMdTask {
    @Test
    public void addTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('testTask', type: ConcMdTask)
        assertTrue(task instanceof ConcMdTask)
    }
}
