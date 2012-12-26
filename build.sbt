scalaVersion := "2.10.0"

resolvers ++= Seq ( "Slick2D Maven Repo" at "http://slick.cokeandcode.com/mavenrepo",
      "b2srepo" at "http://b2s-repo.googlecode.com/svn/trunk/mvn-repo",
      "Freehep" at "http://java.freehep.org/maven2"
    )

libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl" % "2.8.5"

libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl-platform" % "2.8.5" classifier "natives-windows" classifier "natives-linux" classifier "natives-osx"

libraryDependencies += "slick" % "slick" % "274" exclude ("org.lwjgl","lwjgl") exclude ("phys2d","phys2d")

libraryDependencies ++= Seq(
     compilerPlugin("org.scala-lang.plugins" % "continuations" % "2.10.0"),
    "joda-time" % "joda-time" % "2.0" withSources(),
    "org.joda" % "joda-convert" % "1.2",
    "org.codehaus.jsr166-mirror" % "jsr166y" % "1.7.0",
    "org.scalatest" % "scalatest_2.10.0" % "2.0.M5",
    "junit" % "junit" % "4.10" % "test->default",
    "com.novocode" % "junit-interface" % "0.8" % "test->default"
)

autoCompilerPlugins := true

scalacOptions += "-P:continuations:enable"

parallelExecution := false
