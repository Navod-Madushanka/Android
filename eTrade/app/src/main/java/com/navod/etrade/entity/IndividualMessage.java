package com.navod.etrade.entity;

import com.navod.etrade.model.Messages;

import java.util.Date;

public class IndividualMessage implements Messages {
    private String messageId;
    private String title;
    private String content;
    private Date addedDate;

    public IndividualMessage() {
    }

    public IndividualMessage(String messageId, String title, String content, Date addedDate) {
        this.messageId = messageId;
        this.title = title;
        this.content = content;
        this.addedDate = addedDate;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "IndividualMessage{" +
                "messageId='" + messageId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
