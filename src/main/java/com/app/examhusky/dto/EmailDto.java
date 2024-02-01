package com.app.examhusky.dto;

import java.util.List;

public class EmailDto {
    private String mailFrom;

    private String displayName;

    private String mailTo;

    private List<String> mailCc;

    private List<String> mailBcc;

    private String mailSubject;

    private String mailContent;

    private String contentType;

    private List<Object> attachments;

    public EmailDto() {
    }

    public EmailDto(String mailFrom, String displayName, String mailTo, List<String> mailCc, List<String> mailBcc,
                    String mailSubject, String mailContent, String contentType, List<Object> attachments) {
        this.mailFrom = mailFrom;
        this.displayName = displayName;
        this.mailTo = mailTo;
        this.mailCc = mailCc;
        this.mailBcc = mailBcc;
        this.mailSubject = mailSubject;
        this.mailContent = mailContent;
        this.contentType = contentType;
        this.attachments = attachments;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public List<String> getMailCc() {
        return mailCc;
    }

    public void setMailCc(List<String> mailCc) {
        this.mailCc = mailCc;
    }

    public List<String> getMailBcc() {
        return mailBcc;
    }

    public void setMailBcc(List<String> mailBcc) {
        this.mailBcc = mailBcc;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }
}