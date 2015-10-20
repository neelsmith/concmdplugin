package edu.holycross.shot

import org.gradle.api.Project
import org.gradle.api.Plugin

class ConcMdPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.task('cpConcMd', type: ConcMdTask)
    }
}
