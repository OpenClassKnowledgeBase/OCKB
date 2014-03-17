name := "play-ockb"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJpa,
  javaJdbc,
  javaEbean,
  cache,
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"
)     

play.Project.playJavaSettings
