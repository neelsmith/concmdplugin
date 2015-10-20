package edu.holycross.shot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ConcMdTask extends DefaultTask {
    String greeting = 'Working on concmd...'

    @TaskAction
    def greet() {
        println greeting
    }
}
