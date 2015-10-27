---
layout: page
title: The concmd gradle plugin
---

Write testable [concordion](http://concordion.org/) specifications in markdown.


## Prerequisites

- Java version >= 7


## Configure your gradle build file


**1**. Apply the plugin:

    apply plugin: 'edu.holycross.shot.concmd'

**2**. Define a dependency on the "fat" plugin like this:

    buildscript {
      repositories {
        maven {
      	  url "http://beta.hpcc.uh.edu/nexus/content/groups/public"
      	}
      }
      dependencies {
        classpath group: "edu.holycross.shot", name: "concmd", version: "1.0.3", classifier: "all"
      }
    }

**3**. Then use the plugin in a task like this:

    task prepareConc(type: edu.holycross.shot.ConcMdTask) {
      description "Converts markdown source to HTML for concordion"
      from = "specs"
      into = "output"
    }


## Writing testable prose

Testable prose is not just for program specifications!  See a [gradle project using this
plugin](http://neelsmith.github.io/concmd/) to help you write machine-testable prose in markdown.


## Release notes

See [details](releases).
