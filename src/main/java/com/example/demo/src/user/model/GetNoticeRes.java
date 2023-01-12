package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Text;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetNoticeRes {
    private String noticeTitle;
    private String noticeContent;
    private String createAt;
}
