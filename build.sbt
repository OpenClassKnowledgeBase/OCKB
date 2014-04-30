name := "play-ockb"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.21",
  "com.typesafe.slick" %% "slick" % "1.0.0"
)     

play.Project.playJavaSettings
