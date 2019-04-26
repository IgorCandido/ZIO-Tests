name := "ZIO_Test"

version := "0.1"

scalaVersion := "2.12.8"

val scalaz = "org.scalaz" %% "scalaz-zio" % "1.0-RC4"
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test
val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.1.8"
val akka = "com.typesafe.akka" %% "akka-actor" % "2.5.22"
val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.22"

libraryDependencies ++= Seq(scalaz, scalaTest, scalaCheck, akkaHttp, akkaStream, akka)