package com.s2o.scattergram


import java.io._
import java.util.concurrent.atomic.AtomicInteger

import io.gatling.core.Predef.{global, _}
import io.gatling.http.Predef._

import scala.concurrent.duration._



class FullWorkLoadDynamicSimulation extends Simulation {

  val noOfUsers      = 50
  val rampUpTimeSecs = 5
  val testTimeSecs   = 60
  val minWaitMs      = 1000 milliseconds
  val maxWaitMs      = 3000 milliseconds

  val httpConf = http
    .baseURL("http://localhost:20666/")
    .acceptHeader("application/json")
    .doNotTrackHeader("1")

  val headers_10 = Map("Content-Type" -> "application/json")
  val incrementalId = new AtomicInteger(0)
  var imagesFolder="ICAScattergramSrv"
  var image= imagesFolder + "/scattergram"
  var extension=".png"
  var imagesFolderPath = new File(System.getProperty("user.dir")+ "/" + imagesFolder)
  deleteRecursively(imagesFolderPath)

  val scn = scenario("Generate Scattergram")
    .during(testTimeSecs) {
      exec(session => session.set("imageFile", image + incrementalId.getAndIncrement + extension))
        .exec(
          http("Generate Scattergram dynamic")
            .post("/generateScatterGram").headers(headers_10)
            .body(ElFileBody("create-img-dynamic.json")).asJSON
            .check(jsonPath("$.content").is("ACK"))
        )
        .pause(minWaitMs, maxWaitMs)
    }

  setUp(scn.inject(rampUsers(noOfUsers) over (rampUpTimeSecs)))
    .protocols(httpConf)
    .assertions(
      global.responseTime.max.lessThan(1000),
      global.successfulRequests.percent.greaterThan(98),
      global.allRequests.count.greaterThan(1000)
    )

  def deleteRecursively(file: File): Unit = {
    if (file.isDirectory)
      file.listFiles.foreach(deleteRecursively)
    if (file.exists && !file.delete)
      throw new Exception(s"Unable to delete ${file.getAbsolutePath}")
  }
}

