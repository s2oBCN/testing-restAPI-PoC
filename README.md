
The intention of this article is to provide a simple sample of testing a REST API Web Service. The code of this sample can be found at https://github.com/s2oBCN/testing-restAPI-PoC

In the sample we will use this technological stack:

* Gradle [https://gradle.org/]: it is a tool to automate the build process. There are a lot of comparatives between Maven and Gradle, in our case we choose Gradle because of its incremental compilation, smart testing (if a project has nothing changed then the tests will not be re-executed) and for its Groovy scripting, within you can do some cool things like:
* Spring boot 1.4  [http://codecentric.github.io/spring-boot-admin]: maybe the easiest way to bootstrapping a JEE application.
* Spock 1.0 [http://spockframework.org/]: it is a Groovy test framework that also can be used to test java applications. It is cool because of its expressive DSL, but it’s needed to use it to see all its power.
* Cucumber 1.2 [https://cucumber.io/]: because we will use BDD with Java, and we want our Bussines Analyst to review the Scenarios.
* Serenity 1.1 [http://www.thucydides.info/]: because of its IOC ready to use and its reports.
* Rest Assured 3.0 [http://rest-assured.io/]: because of its fluent way of testing a web service.
* Gatling 2.2 [http://gatling.io/]: incredibly easy to modify the configuration in order to test differents parameters of performance.

