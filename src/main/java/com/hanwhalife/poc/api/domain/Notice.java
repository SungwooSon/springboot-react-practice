package com.hanwhalife.poc.api.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@ToString
public class Notice extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    //@Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    private LocalDateTime registrationDate;

    @Builder
    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoticeEditor.NoticeEditorBuilder toEditor() {
        return NoticeEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(NoticeEditor noticeEditor) {
        title = noticeEditor.getTitle();
        content = noticeEditor.getContent();
    }
}
