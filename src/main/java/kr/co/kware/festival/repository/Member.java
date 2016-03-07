package kr.co.kware.festival.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TB_MEMBER")
public class Member {

    @Id
    private String email;

    private String name;

    @Column(name = "M_GROUP")
    private String group;
}
