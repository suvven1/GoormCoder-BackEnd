package goormcoder.webide.domain;

import goormcoder.webide.domain.enumeration.BoardType;
import goormcoder.webide.dto.request.BoardCreateDto;
import goormcoder.webide.dto.request.BoardUpdateDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Table(name = "t_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_category", nullable = false)
    private BoardType boardType;

    @Column(name = "board_title", nullable = false)
    private String title;

    @Column(name = "board_content", nullable = false)
    private String content;

    @Column(name = "board_likeCount", nullable = false)
    private int likeCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    private Question question;

    public static Board of(final BoardType boardType, final String title, String content, final Member member, final Question question) {
        return Board.builder()
                .boardType(boardType)
                .title(title)
                .content(content)
                .member(member)
                .question(question)
                .likeCount(0)
                .build();
    }

    @Builder
    private Board(final BoardType boardType, final String title, final String content, final Member member, final Question question, final int likeCount) {
        this.boardType = boardType;
        this.title = title;
        this.content = content;
        this.member = member;
        this.question = question;
        this.likeCount = likeCount;
    }

    public void patch(String title, String content) {
        if (isModified(title, content)) {
            this.title = title;
            this.content = content;
        }
    }

    private boolean isModified(String title, String content) {
        return !Objects.equals(this.title, title) || !Objects.equals(this.content, content);
    }

    public void addLikeCount() {
        this.likeCount++;
    }

    public void removeLikeCount() {
        this.likeCount--;
    }
}
