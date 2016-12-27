package com.s2o;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IcaImageGenSrvApplicationTests {

	@Autowired
	private MockMvc mockMvc;


  // For testing purpose to easy mock the json object to send in the request body
	private class reqBody{
	    String xPoints;
        String yPoints;

        public String getxPoints() {
            return xPoints;
        }

        public void setxPoints(String xPoints) {
            this.xPoints = xPoints;
        }

        public String getyPoints() {
            return yPoints;
        }

        public void setyPoints(String yPoints) {
            this.yPoints = yPoints;
        }

        public String getColors() {
            return colors;
        }

        public void setColors(String colors) {
            this.colors = colors;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRgbToken() {
            return rgbToken;
        }

        public void setRgbToken(String rgbToken) {
            this.rgbToken = rgbToken;
        }

        public int getImgWidth() {
            return imgWidth;
        }

        public void setImgWidth(int imgWidth) {
            this.imgWidth = imgWidth;
        }

        public int getImgHeight() {
            return imgHeight;
        }

        public void setImgHeight(int imgHeight) {
            this.imgHeight = imgHeight;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        String colors;
        String token;
        String rgbToken;
        int imgWidth;
        int imgHeight;
        String path;
    }

    // test suite
    @Test
	public void allParamAreInitalizedProperly() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130#81");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/ee.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("ACK"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



	}



    @Test
    public void emptyBody() throws Exception {



        try {
            String bodyJsonString ="";

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isBadRequest());
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }


    @Test
    public void wrongToken() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130#81");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("$");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : the x, y and color array are malformed, the tokens are not well defined or the size of the image is not defined"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void wrongRGBToken() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130#81");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken("$");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : the x, y and color array are malformed, the tokens are not well defined or the size of the image is not defined"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void NoPermissionsToWriteTheFile() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130#81");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("WE:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : no privileges to write file:"+testBody.getPath()));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void RGBArrayHasSeveralPositionsUncompleted() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130#81");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(200);
        testBody.setImgWidth(200);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : Invalid number of parameters in a position of the RGB array"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void differentNumerOfXandYPoints() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            String bodyJsonString =convertPojoToString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : the x, y and color array don't have the same length"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
	@Test
    public void differentNumerOfXYandColorPoints() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130#12");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            String bodyJsonString =convertPojoToString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : the x, y and color array don't have the same length"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }

    @Test
    public void noXData() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            String bodyJsonString =convertPojoToString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : the x, y and color array are malformed, the tokens are not well defined or the size of the image is not defined"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void RGBarrayCointainsNonNumbers() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130#81");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#b#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK :For input string: \"b\""));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void xArrayCointainsNonNumbers() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#b#130#81");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK :For input string: \"b\""));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void yArrayCointainsNonNumbers() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#4#130#81");
        testBody.setyPoints("94#b#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK :For input string: \"b\""));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void yArrayCointainsEmptyPossitions() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#4#130#81");
        testBody.setyPoints("94###81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK :empty String"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void xArrayCointainsEmptyPossitions() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94##130#2");
        testBody.setyPoints("94#4#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK :empty String"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }

    @Test
    public void colorArrayCointainsEmptyPossitions() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#4#130#81");
        testBody.setyPoints("94#4#130#81");
        testBody.setColors("255#255#0,,,20#255#255");
        testBody.setPath("c:/fharts/perdatfter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");
        testBody.setImgHeight(300);
        testBody.setImgWidth(300);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String bodyJsonString = mapper.writeValueAsString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : Invalid number of parameters in a position of the RGB array"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    @Test
    public void ImageSizeNotDefined() throws Exception {


        //... more
        reqBody  testBody = new reqBody();
        testBody.setxPoints("94#87#130#81");
        testBody.setyPoints("94#87#130#81");
        testBody.setColors("255#255#0,0#255#255,255#255#0,20#255#255");
        testBody.setPath("c:/fharts/perdatter300D.png");
        testBody.setToken("#");
        testBody.setRgbToken(",");


        try {
            String bodyJsonString =convertPojoToString(testBody);

            this.mockMvc.perform(post("/generateScatterGram")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJsonString))
                 
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("NACK : the x, y and color array are malformed, the tokens are not well defined or the size of the image is not defined"));
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }
    //reduce code duplications
    private String convertPojoToString(Object pojo){
        String bodyJsonString="";
        try {
            ObjectMapper mapper = new ObjectMapper();
             bodyJsonString = mapper.writeValueAsString(pojo);
        }  catch (Exception ex){
        ex.printStackTrace();
        }
        return bodyJsonString;
    }
}
