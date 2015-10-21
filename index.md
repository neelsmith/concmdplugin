---
layout: page
title: The concmd gradle plugin
---

[Concordion](http://concordion.org/) is wonderful, but who needs all the HTML pointy brackets?  It's 2015:  write your prose in markdown already.

## Use the plugin in 3 easy steps

In your gradle build file:

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
        classpath group: "edu.holycross.shot", name: "concmd", version: "1.0.0", classifier: "all"
      }
    }

**3**. Then use the plugin in a task like this:

    task prepareConc(type: edu.holycross.shot.ConcMdTask) {
      description "Converts markdown source to HTML for concordion"
      from = "specs"
      into = "output"
    }
