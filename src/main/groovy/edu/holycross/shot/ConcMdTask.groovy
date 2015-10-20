package edu.holycross.shot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.file.FileTree

import com.github.rjeschke.txtmark.*

class ConcMdTask extends DefaultTask {
    String from = "specs"
    String into = "build/specs"

    String htmlPreface = "<html xmlns:concordion='http://www.concordion.org/2007/concordion'>\n<body>\n"
    String htmlEnd = "</body>\n</html>\n"

    File getFrom() {
      project.file(from)
    }
    File getInto() {
      project.file(into)
    }

    @TaskAction
    def concmdAction() {
      def fromDir =  getFrom()
      def intoDir = getInto()
      println "Use source in " + fromDir
      println "Write output in " + intoDir

      FileTree tree = project.fileTree(fromDir) {
        include "**/*.md"
      }
      tree.visit { f ->
        if (f.relativePath.isFile()) {
          File inFile = new File("${fromDir}/${f.relativePath}")
          println "Converting " + inFile
          def segs = f.relativePath.getSegments()

          String treePath = intoDir.toString()
          Integer limit =  segs.size() - 1
          segs.eachWithIndex { s, i ->
            if (i < limit) {
              treePath = "${treePath}/${s}"
              File nxtDir = new File(intoDir, s)
              if (! nxtDir.exists()) {
                nxtDir.mkdir()
              }
            }
          }
          File outDir = new File(treePath)
          String htmlFileName = f.relativePath.getLastName().replaceFirst(/.md$/,".html")
          File htmlFile = new File(outDir, htmlFileName)
          println "Created ${htmlFile}"

          try {
            String body = Processor.process(inFile.getText("UTF-8"),Configuration.DEFAULT)
            htmlFile.setText("${htmlPreface}${body}${htmlEnd}", "UTF-8")
          } catch (Exception e) {
            System.err.println "Oops.  convertResources task unable to convert markdown source to HTML!"
            System.err.println e
          }
        }
      }
    }
}
