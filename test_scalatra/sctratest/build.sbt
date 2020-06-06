val ScalatraVersion = "2.7.0-RC1"

organization := "uk.co.danrh.scc"

name := "sctratest"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.10"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.json4s"   %% "json4s-jackson" % "3.6.6",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
//  "org.eclipse.jetty" % "jetty-webapp" % "9.4.19.v20190610" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.xerial" % "sqlite-jdbc" % "3.28.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.1",
  "org.eclipse.jetty" % "jetty-server" % "9.4.24.v20191120",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.24.v20191120",
  "org.eclipse.jetty" % "jetty-servlet" % "9.4.24.v20191120" % "container;compile",
  "org.scalatra" %% "scalatra-auth" % "2.7.0-RC1"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

enablePlugins(ScalatraPlugin)



PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value / "scalapb"
)

// (optional) If you need scalapb/scalapb.proto or anything from
// google/protobuf/*.proto
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"

libraryDependencies ++= Seq(
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
)