package kr.co.kware.festival.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query("select r from Result r " +
            "where 1 = 1 " +
            "and r.id = (select max(r1.id) from Result r1 " +
            "             where r1.member.email = r.member.email" +
            "               and r1.timeMillis = (select min(r1.timeMillis) from Result r2 " +
            "                                    where r2.member.email = r1.member.email " +
            "                                      and r2.hasError = false and r2.isAnswer = true " +
            "                                      and r2.member.group = ?1)) " +
            "order by r.timeMillis asc")
    List<Result> findDistinctResultByMemberGroupGroupByMemberOrderByTimeMillsAsc(String group);
}
