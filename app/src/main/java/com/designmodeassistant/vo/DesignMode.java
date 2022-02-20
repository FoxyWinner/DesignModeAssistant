package com.designmodeassistant.vo;

/**
 * Created by 61002_000 on 2017/5/27.
 */

public class DesignMode
{
    private int chapterId;
    private String chapterName;
    private int hasGener;
    private int hasUml;
    private int hasCode;
    private String generalize;
    private String umlImageUrl;
    private String code;

    public DesignMode()
    {
    }

    public DesignMode(int chapterId, String chapterName, int hasGener, int hasUml, int hasCode, String generalize, String umlImageUrl, String code)
    {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.hasGener = hasGener;
        this.hasUml = hasUml;
        this.hasCode = hasCode;
        this.generalize = generalize;
        this.umlImageUrl = umlImageUrl;
        this.code = code;
    }

    public int getChapterId()
    {
        return chapterId;
    }

    public void setChapterId(int chapterId)
    {
        this.chapterId = chapterId;
    }

    public String getChapterName()
    {
        return chapterName;
    }

    public void setChapterName(String chapterName)
    {
        this.chapterName = chapterName;
    }

    public int getHasGener()
    {
        return hasGener;
    }

    public void setHasGener(int hasGener)
    {
        this.hasGener = hasGener;
    }

    public int getHasUml()
    {
        return hasUml;
    }

    public void setHasUml(int hasUml)
    {
        this.hasUml = hasUml;
    }

    public int getHasCode()
    {
        return hasCode;
    }

    public void setHasCode(int hasCode)
    {
        this.hasCode = hasCode;
    }

    public String getGeneralize()
    {
        return generalize;
    }

    public void setGeneralize(String generalize)
    {
        this.generalize = generalize;
    }

    public String getUmlImageUrl()
    {
        return umlImageUrl;
    }

    public void setUmlImageUrl(String umlImageUrl)
    {
        this.umlImageUrl = umlImageUrl;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
