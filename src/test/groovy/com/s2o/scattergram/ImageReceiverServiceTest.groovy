package com.s2o.scattergram

import com.s2o.scattergram.images.domain.ScattergramParams
import com.s2o.scattergram.images.service.ImageReceiverService
import com.s2o.scattergram.images.service.ScattergramService
import org.codehaus.groovy.runtime.InvokerHelper
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created on 09/12/2016.
 */
class ImageReceiverServiceTest extends Specification {


    @Unroll
    void 'params checker: #testDescription'() {
        given:
        def scattergramService = Mock(ScattergramService)
        def imageReceiverService = new ImageReceiverService(scattergramService)
        def defaultPathBase = new File(".").getCanonicalPath()
        imageReceiverService.imgBasePath = defaultPathBase
        when:
        def scatterParams = new ScattergramParams()
        use(InvokerHelper) {
            scatterParams.setProperties(givenParameters)
        }
        def result = imageReceiverService.generateScattergram(scatterParams)
        then:
         result == expectedResult
        where:
        testDescription     | givenParameters                                                  | expectedResult
        "By default Ok" | ["xPoints": "94#87#130#81", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "ACK"
        "Wrong Token"   | ["xPoints": "94#87#130#81", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "&", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK : the x, y and color array are malformed, the tokens are not well defined"
        "Wrong RGB Token" | ["xPoints": "94#87#130#81", "yPoints": "94#87#130#81",
                         "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                         "path"   : "build/tmp/fharts/asdfasdf.png",
                         "token"  : "#", "rgbToken": "&", imgHeight: 300, imgWidth: 300] | "NACK : the x, y and color array are malformed, the tokens are not well defined"
        "RGBArray Uncompleted" | ["xPoints": "94#87#130#81", "yPoints": "94#87#130#81",
                             "colors" : "255#255#0,0#255,255#255#0,20#255#255",
                             "path"   : "build/tmp/fharts/asdfasdf.png",
                             "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK :Error generating the axisInvalid number of parameters in a position of the RGB array"
        "different # of X/Y Points" | ["xPoints": "94#87#81", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK : the x, y and color array don't have the same length"
        "different # of X/Y Colors" | ["xPoints": "94#87#130#81", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK : the x, y and color array don't have the same length"
        "noXData" | ["xPoints":"", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK : the x, y and color array are malformed, the tokens are not well defined"
        "RGBarrayCointainsNonNumbers" | ["xPoints":"94#87#130#81", "yPoints": "94#87#130#81",
                     "colors" : "255#2a55#0,0#255#255,255#255#0,20#255#255",
                     "path"   : "build/tmp/fharts/asdfasdf.png",
                     "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK :Error generating the axisFor input string: \"2a55\""
        "xArrayCointainsNonNumbers" | ["xPoints": "94#87#1a#81", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK :Error generating the axisFor input string: \"1a\""
        "yArrayCointainsNonNumbers" | ["xPoints": "94#87#130#81", "yPoints": "94#b7#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK :Error generating the axisFor input string: \"b7\""
        "xArrayCointainsEmptyPossitions" | ["xPoints": "94##130#81", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK :Error generating the axisempty String"
        "yArrayCointainsEmptyPossitions" | ["xPoints": "94#87#130#81", "yPoints": "94##130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK :Error generating the axisempty String"
        "colorArrayCointainsEmptyPossitions" | ["xPoints": "94#87#130#81", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgHeight: 300, imgWidth: 300] | "NACK : the x, y and color array don't have the same length"
        "ImageSizeNotDefined" | ["xPoints": "94#87#130#81", "yPoints": "94#87#130#81",
                           "colors" : "255#255#0,0#255#255,255#255#0,20#255#255",
                           "path"   : "build/tmp/fharts/asdfasdf.png",
                           "token"  : "#", "rgbToken": ",", imgWidth: 300] | "NACK : the size of the image is not correctly  defined"
    }

}
