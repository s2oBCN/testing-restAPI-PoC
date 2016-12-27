package com.s2o.scattergram

import com.s2o.scattergram.images.domain.ScattergramParams
import com.s2o.scattergram.images.service.ImageReceiverService
import com.s2o.scattergram.images.service.ScattergramService
import org.codehaus.groovy.runtime.InvokerHelper
import spock.lang.Specification
/**
 * Created on 09/12/2016.
 */
class ImageReceiverServiceTest extends Specification {

    void 'params checker'() {
        given:
            def scattergramService = Mock(ScattergramService)
            def imageReceiverService = new ImageReceiverService(scattergramService)
            def defaultPathBase = new File( "." ).getCanonicalPath()
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
            givenParameters | expectedResult
        ["xPoints":"94#87#130#81","yPoints":"94#87#130#81",
         "colors":"255#255#0,0#255#255,255#255#0,20#255#255",
         "path":"build/tmp/fharts/asdfasdf.png",
         "token":"#","rgbToken":",", imgHeight:300, imgWidth:300]  | "ACK"
        ["xPoints":"94#87#130#81","yPoints":"94#87#130#81",
         "colors":"255#255#0,0#255#255,255#255#0,20#255#255",
         "path":"build/tmp/fharts/asdfasdf.png",
         "token":"&","rgbToken":",", imgHeight:300, imgWidth:300]  | "NACK : the x, y and color array are malformed, the tokens are not well defined"
    }

}
