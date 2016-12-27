package com.s2o.scattergram.images.controller;


import com.s2o.scattergram.images.domain.Response;
import com.s2o.scattergram.images.domain.ScattergramParams;
import com.s2o.scattergram.images.service.ImageReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by blancof1 on 02/11/2016.
 */
@RestController
public class ScattergramController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    ImageReceiverService imageReceiverService;

    /**
     * rest endpoint
     *
     * @param scatterParams
     * @return
     */
    @RequestMapping("/generateScatterGram")
    public Response generateScatter(@RequestBody ScattergramParams scatterParams) {
        LOGGER.info("Scattergram request received to be generated in " + scatterParams.getPath());
        String generationResponse = imageReceiverService.generateScattergram(scatterParams);
        LOGGER.info("Scattergram request response " + generationResponse);
        return new Response(counter.incrementAndGet(), generationResponse);
    }
}
