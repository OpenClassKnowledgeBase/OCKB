name := "play-ockb"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.21",
  "com.typesafe.slick" %% "slick" % "1.0.0",
  "org.mockito" % "mockito-all" % "1.9.0" % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-support" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-iphone-driver" % "2.39.0",
  "org.seleniumhq.selenium" % "selenium-ie-driver" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-chrome-driver" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-android-driver" % "2.39.0",
  "org.seleniumhq.selenium" % "selenium-remote-driver" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-api" % "2.41.0",
  "org.apache.poi" % "poi" % "3.8",
  "org.apache.poi" % "poi-ooxml" % "3.9"
)     

play.Project.playJavaSettings
