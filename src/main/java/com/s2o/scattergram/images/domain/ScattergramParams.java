package com.s2o.scattergram.images.domain;


import com.s2o.scattergram.images.ICAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.lang.invoke.MethodHandles;
import java.util.regex.Pattern;

public class ScattergramParams {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String xPoints;
    private String yPoints;
    private String colors;
    private String token;
    private String rgbToken;
    private Integer imgWidth;
    private Integer imgHeight;
    private String path;
    private String[] xStringArray;
    private String[] yStringArray;
    private String[] colorsArray;
    private int[] dotcolor;
    private double[][] axis;

    /**
     * is mandatory that the xPoints, yPoints and  Colors will have the same number of elements separated by the token char
     *
     * @throws Exception
     */
    public void fillArrays() throws ICAException {
        this.setxStringArray(this.getxPoints().split(Pattern.quote(this.getToken())));
        this.setyStringArray(this.getyPoints().split(Pattern.quote(this.getToken())));
        this.setColorsArray(this.getColors().split(Pattern.quote(this.getRgbToken())));
        try {
            axis = new double[2][xStringArray.length];
            dotcolor = new int[xStringArray.length];
            for (int i = 0; i < xStringArray.length; i++) {
                axis[0][i] = Double.parseDouble(xStringArray[i]);
                axis[1][i] = Double.parseDouble(yStringArray[i]);
                dotcolor[i] = getRGBColor(colorsArray[i], token);
            }

        } catch (Exception exception) {
            String msg = "Error generating the axis" + exception.getMessage();
            LOGGER.error(msg);
            ICAException throwEx = new ICAException(msg, exception);
            throw throwEx;
        }
    }

    public int[] getDotcolor() {
        return dotcolor;
    }

    public void setDotcolor(int[] dotcolor) {
        this.dotcolor = dotcolor;
    }

    public double[][] getAxis() {
        return axis;
    }

    public void setAxis(double[][] axis) {
        this.axis = axis;
    }


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

    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getxStringArray() {
        return xStringArray;
    }

    public void setxStringArray(String[] xStringArray) {
        this.xStringArray = xStringArray;
    }

    public String[] getyStringArray() {
        return yStringArray;
    }

    public void setyStringArray(String[] yStringArray) {
        this.yStringArray = yStringArray;
    }

    public String[] getColorsArray() {
        return colorsArray;
    }

    public void setColorsArray(String[] colorsArray) {
        this.colorsArray = colorsArray;
    }

    /**
     * transform a RGB string with the color in the format R|G|B being R, G and B numbers between 0 and 255
     *
     * @param rgbStringPatter
     * @return
     * @throws Exception
     */
    private int getRGBColor(String rgbStringPatter, String dftColorSeparatorChr) throws ICAException {

        String[] rgbStringArray = rgbStringPatter.split(dftColorSeparatorChr);
        if (rgbStringArray.length < 3) {
            String msg = "Invalid number of parameters in a position of the RGB array";
            LOGGER.error(msg);
            throw new ICAException(msg);

        }
        Color dotColor = new Color(Integer.parseInt(rgbStringArray[0]), Integer.parseInt(rgbStringArray[1]), Integer.parseInt(rgbStringArray[2]));
        return dotColor.getRGB();
    }
}
