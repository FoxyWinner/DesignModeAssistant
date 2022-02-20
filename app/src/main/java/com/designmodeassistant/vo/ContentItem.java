package com.designmodeassistant.vo;

/**
 * Created by 61002_000 on 2017/5/28.
 */

public  class ContentItem
{
    public int itemChapterId;
    public String itemChapterName;

    public ContentItem()
    {
    }

    public ContentItem(int itemChapterId, String itemChapterName)
    {
        this.itemChapterId = itemChapterId;
        this.itemChapterName = itemChapterName;
    }

    @Override
    public String toString()
    {
        return itemChapterId+" "+itemChapterName;
    }
}
