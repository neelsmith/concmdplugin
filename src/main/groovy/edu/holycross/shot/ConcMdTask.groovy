package edu.holycross.shot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.file.FileTree

import com.github.rjeschke.txtmark.*

class ConcMdTask extends DefaultTask {
  Integer debug = 0
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

  /** Finds output file corresponding to input file,
   * and creates new directory if necessary.
   * @param treePath Relative path within file tree.
   * @returns New File object.
   */
  File outForIn(org.gradle.api.file.RelativePath treePath) {
    File inFile = new File("${getFrom()}/${treePath}")
    File outFile = new File("${getInto()}/${treePath}")
    if (inFile.isDirectory()) {
      if   (! outFile.exists()) {
	outFile.mkdir()
      }
      
    } else {
      String htmlFileName = treePath.toString().replaceFirst(/.md$/,".html")
      outFile = new File(getInto(), htmlFileName)
      outFile.setText("<html><head><title>Empty HTML file</title></head><body/></html>")
    }

    return outFile
  }

  @TaskAction
  def concmdAction() {
    File fromDir =  getFrom()
    File intoDir = getInto()
    println "ConcMdTask: Use source in " + fromDir 
    println "ConcMdTask: Write output to " + intoDir
    
    
    String currentSubDir = intoDir.toString()
    File outDir
    try {
      outDir = new File(currentSubDir)
    } catch (Exception e) {
      System.err.println "ConcMdTask: unable able to create subdirectory " + currentSubDir
      throw e
    }

    // Won't create whole containing hierarchy, but if
    // the named directory doesn't yet exist, make it.
    if (! outDir.exists()) {
      outDir.mkdir()
    }
    
    FileTree tree = project.fileTree(fromDir) {
      include "**/*.md"
    }
    tree.visit { f ->
      File inFile = project.file("${fromDir}/${f.relativePath}")
      File outFile = outForIn(f.relativePath)
      if (outFile.isDirectory()) {
	// skip
      } else {
	try {
	  String body = Processor.process(inFile.getText("UTF-8"),Configuration.DEFAULT)
	  outFile.setText("${htmlPreface}${body}${htmlEnd}", "UTF-8")
	} catch (Exception e) {
	  System.err.println "ConcMdTask: unable to convert markdown source ${inFile} to HTML ${outFile}."
	  System.err.println e
	}
      }
    }
  }
}
