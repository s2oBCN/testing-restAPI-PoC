package com.s2o.scattergram.images.service;


import com.s2o.scattergram.images.domain.ScattergramParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.invoke.MethodHandles;

/**
 * Created by blancof1 on 26/10/2016.
 */
@Service
public class ImageReceiverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${files.upload.baseDir}")
    private String imgBasePath;

    private ScattergramService scattergramService;

    @Autowired
    public ImageReceiverService(ScattergramService scattergramService) {
        this.scattergramService = scattergramService;
    }

    /**
     * receives the pojo with the parameters to create a scattergram. Perform the validations and queue it i
     *
     * @param scatterParams
     * @return ACK msg to notify if the scattergram is going to be created or a NACK and the message explaining why cant be created
     */
    public String generateScattergram(final ScattergramParams scatterParams) {
        String ret = "ACK";
        try {

            scatterParams.setPath(imgBasePath+"/"+scatterParams.getPath());
            MsgValidation validation = validateInputs(scatterParams);
            if (validation.passed) {
                // push scatterdata to the generation queue
                scatterParams.fillArrays();
                scattergramService.generateFile(scatterParams);
            } else {
                ret = validation.msg;
            }

        } catch (Exception exception) {
            ret = "NACK :" + exception.getMessage();
            LOGGER.error("Error generating Scattergram " + ret, exception);
        }
        return ret;
    }

    /**
     * launch validations over the Scattergram parameters received
     *
     * @param scattergramParams
     * @return a plain object with 2 parameters
     * - validated who will be true if the parameters pass all the validations
     * - msg who will contain the explanation when the parametters dont pass the validations
     */
    private MsgValidation validateInputs(final ScattergramParams scattergramParams) {
        MsgValidation validation;
        validation = checkSizes(scattergramParams);
        if (validation.passed) {
            validation = checkLenghtTokens(scattergramParams);
            if (validation.passed) {
                validation = checkFileAndDirPermissions(scattergramParams);
            }
        }
        return validation;
    }

    /**
     * this method check if the file can be written checking the user permissions and if the directory in which the file has to be created exists
     *
     * @param scatterParams
     * @return true if everything is ok
     */
    private MsgValidation checkFileAndDirPermissions(final ScattergramParams scatterParams) {
        MsgValidation validation = new MsgValidation();
        if (checkDirExist(scatterParams.getPath()) && checkFileCanBeWritten(scatterParams.getPath())) {
            validation.passed = true;
        } else {
            validation.passed = false;
            validation.msg = "NACK : no privileges to write file:" + scatterParams.getPath();
        }
        return validation;
    }

    /**
     * checks if the three arrays have the same number of parameters
     *
     * @param scatterParams
     * @return
     */
    private MsgValidation checkSameNumberOfPositions(ScattergramParams scatterParams) {
        MsgValidation validation = new MsgValidation();
        int numTokenInX = StringUtils.countMatches(scatterParams.getxPoints(), scatterParams.getToken());
        int numTokenInY = StringUtils.countMatches(scatterParams.getyPoints(), scatterParams.getToken());
        int numTokenInCol = StringUtils.countMatches(scatterParams.getColors(), scatterParams.getRgbToken());
        if (numTokenInCol == numTokenInX && numTokenInY == numTokenInX) {
            validation.passed = true;
        } else {
            validation.passed = false;
            validation.msg = "NACK : the x, y and color array don't have the same length";
        }
        return validation;
    }

    /**
     * check if a dir exists
     *
     * @param path
     * @return
     */
    private boolean checkDirExist(String path) {
        boolean chkDir = true;
        String dirpath = path.substring(0, path.lastIndexOf("/"));
        try {
            File directory = new File(String.valueOf(dirpath));
            if (!directory.exists()) {
                chkDir=directory.mkdirs();
            }
        } catch (Exception ex) {
            LOGGER.info("checkDirExist" + path + " doesent exists " + ex.getMessage());
            chkDir= false;
        }
        return chkDir;
    }

    /**
     * check if a file can be written
     *
     * @param imgPath
     * @return
     */
    private boolean checkFileCanBeWritten(String imgPath) {
        File file = new File(imgPath);
        if (file.exists()) {
            return file.canWrite();
        } else {
            try {
                file.createNewFile();
                file.delete();
                return true;
            } catch (Exception exception) {
                LOGGER.error("checkFileCanBeWritten" + imgPath + " no permissions to write file " + exception.getMessage(), exception);
                return false;
            }
        }
    }

    /**
     * check the sizes of the scattegram params
     *
     * @param scatterParams
     * @return
     */
    private MsgValidation checkSizes(ScattergramParams scatterParams) {
        MsgValidation validation = new MsgValidation();
        if (scatterParams.getImgHeight() != null && scatterParams.getImgWidth() != null && scatterParams.getImgHeight() > 0 && scatterParams.getImgWidth() > 0) {
            validation.passed = true;
        } else {
            validation.passed = false;
            validation.msg = "NACK : the size of the image is not correctly  defined";
        }
        return validation;
    }

    /**
     * check if the arrays have the specified tokens
     *
     * @param scatterParams
     * @return
     */
    private MsgValidation checkLenghtTokens(ScattergramParams scatterParams) {
        MsgValidation validation = new MsgValidation();
        if (checkPoints(scatterParams.getxPoints(), scatterParams.getToken())
                && checkPoints(scatterParams.getyPoints(), scatterParams.getToken())
                && (checkPoints(scatterParams.getColors(), scatterParams.getToken()) && scatterParams.getColors().contains(scatterParams.getRgbToken()))) {
            validation.passed = true;
            validation = checkSameNumberOfPositions(scatterParams);
        } else {
            validation.passed = false;
            validation.msg = "NACK : the x, y and color array are malformed, the tokens are not well defined";
        }
        return validation;
    }

    /**
     * auxiliar function created in order to make more readable the checkLenghtTokens if
     *
     * @param points
     * @param token
     * @return
     */
    private boolean checkPoints(String points, String token) {
        Boolean precond = false;
        if (points.length() > 0 && points.contains(token)) {
            precond = true;
        }
        return precond;
    }

    private class MsgValidation {
        boolean passed;
        String msg;
    }

}
