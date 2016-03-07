package kr.co.kware.festival.repository;

import kr.co.kware.festival.KFestivalApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KFestivalApplication.class)
public class ResultRepositoryTest {

    @Autowired
    private ResultRepository resultRepository;

    @Test
    public void testFindByMemberGroupOrderByTimeMillsAsc() throws Exception {
        List<Result> results = resultRepository.findAll();
        results.forEach(System.out::println);
//        for (Result result : results) {
//            if (StringUtils.hasText(result.getErrorCommand())) {
//                result.setHasError(true);
//
//                resultRepository.save(result);
//            }
//        }


        results = resultRepository.findDistinctResultByMemberGroupGroupByMemberOrderByTimeMillsAsc("A");
        System.out.println("==== GROUP A ====");
        results.forEach(System.out::println);

        results = resultRepository.findDistinctResultByMemberGroupGroupByMemberOrderByTimeMillsAsc("B");
        System.out.println("==== GROUP B ====");
        results.forEach(System.out::println);

    }
}
