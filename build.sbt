name := "play-ockb"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJpa,
  javaJdbc,
  cache
)     

play.Project.playJavaSettings
