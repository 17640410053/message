package com.banana.message.vo;

import lombok.Data;

@Data
public class MailVo {
    private String to;
    private String subject;
    private String text;
}
