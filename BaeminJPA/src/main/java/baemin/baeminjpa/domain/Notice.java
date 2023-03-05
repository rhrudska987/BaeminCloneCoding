package baemin.baeminjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Notice extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "noticeId")

    private String noticeTitle;
    private String noticeContent;

    @Enumerated(EnumType.STRING)
    private NoticeStatus status;

    private LocalDateTime readAt;
    public void setReadAt(LocalDateTime modifiedDate) { readAt = modifiedDate; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
}
