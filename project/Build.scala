import sbt._
import sbt.Keys._

import java.io.File

object MyBuild extends Build {

  //def getNativeLibs(base: File) = base / "lib" / "native"
  def getNativeLibs(base: File) = base / "lib"

  def getLibraryPathOption(base: File) = {
    val prop = System.getProperty("java.library.path")
    val lib = getNativeLibs(base).getCanonicalPath
    "-Djava.library.path=" + prop + ":" + lib
  }

  override lazy val settings = super.settings ++ Seq(
    fork in run := true,
    javaOptions += getLibraryPathOption(new File("."))
  )

  lazy val root = Project("slack", file("."))
}
