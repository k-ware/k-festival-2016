package kr.co.kware.festival.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TB_RESULT")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMAIL")
    private Member member;

    private Integer questionId;
    private String source;
    private String normalOutput;

    private boolean hasError;
    private String errorCommand;
    private String errorOutput;

    @Column(name = "TIME_MILLS")
    private long timeMillis = 0;

    @Column(name = "RIGHT_ANSWER")
    private boolean isAnswer;

    private Date regDt;
}
