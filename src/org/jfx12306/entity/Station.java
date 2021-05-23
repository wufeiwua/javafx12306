package org.jfx12306.entity;

/**
 * 车站信息
 */
public class Station {

    private String at;
    private String name;
    private String code;
    private String fullPinYin;
    private String shortPinYin;

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullPinYin() {
        return fullPinYin;
    }

    public void setFullPinYin(String fullPinYin) {
        this.fullPinYin = fullPinYin;
    }

    public String getShortPinYin() {
        return shortPinYin;
    }

    public void setShortPinYin(String shortPinYin) {
        this.shortPinYin = shortPinYin;
    }
}
