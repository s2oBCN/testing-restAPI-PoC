package com.s2o.scattergram.images.features.definitions;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.s2o.scattergram.images.IcaImageGenSrvApplication;
import com.s2o.scattergram.images.domain.ScattergramParams;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.rest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = IcaImageGenSrvApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class IcaImageStepDefinitions {

    @LocalServerPort
    private int port;

    private ScattergramParams scattergramParam;
    private Response reqAnswer;

    @Before
    public void configurePorts() {
        RestAssured.port = port;
    }

    @Given("the following image has just come out:")
    public void requestParamenters(List<ScattergramParams> scattergramParams) {
        scattergramParam = scattergramParams.get(0);
    }

    @When("I send the request to generateScatterGram")
    public void sendRequest() {
        if (scattergramParam != null) {
            reqAnswer = rest().given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(scattergramParam)
                    .post("/generateScatterGram");
        } else {
            reqAnswer = rest().given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("")
                    .post("/generateScatterGram");
        }
    }

    @Then("I should be able to find the content OK")
    public void shouldBeAbleToFindThatAllIsOk() {
        String content = reqAnswer.then().statusCode(HttpStatus.OK.value())
                .and().extract().jsonPath().getString("content");
        assertThat(content).isEqualTo("ACK");
    }

    @Given("^I send an empty body$")
    public void iSendAnEmptyBody() throws Exception {
        scattergramParam = null;
    }

    @Then("^I should be able to find the content is not OK$")
    public void iShouldBeAbleToFindTheContentIsNotOK() throws Exception {
        reqAnswer.then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Then("^I should be able to find an error of '(.*)'$")
    public void rGBArrayHasSeveralPositionsUncompleted(String expectedError) throws Exception {
        String content = reqAnswer.then().statusCode(HttpStatus.OK.value())
                .and().extract().jsonPath().getString("content");
        assertThat(content).startsWith(expectedError);
    }
}
