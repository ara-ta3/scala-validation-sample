val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.12.5",
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "scala validation sample",
    libraryDependencies ++= serverDependencies
  )

val serverDependencies = Seq(
  "com.wix" %% "accord-core" % "0.7.2"

)



