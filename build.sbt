resolvers ++= Seq ( "Slick2D Maven Repo" at "http://slick.cokeandcode.com/mavenrepo",
      "b2srepo" at "http://b2s-repo.googlecode.com/svn/trunk/mvn-repo",
      "Freehep" at "http://java.freehep.org/maven2"
    )

libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl" % "2.8.5"

libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl-platform" % "2.8.5" classifier "natives-windows" classifier "natives-linux" classifier "natives-osx"

libraryDependencies += "slick" % "slick" % "274" exclude ("org.lwjgl","lwjgl") exclude ("phys2d","phys2d")

