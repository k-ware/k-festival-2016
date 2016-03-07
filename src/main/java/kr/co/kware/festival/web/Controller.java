package kr.co.kware.festival.web;

import kr.co.kware.festival.repository.*;
import kr.co.kware.festival.runner.JavaRunner;
import kr.co.kware.festival.runner.RunnerResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    private final JavaRunner javaRunner = new JavaRunner();

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ResultRepository resultRepository;

    @RequestMapping("/members")
    public List<MemberVO> getMembers() {
        List<MemberVO> memberVOList = new ArrayList<>();
        MemberVO vo;
        for (Member member : memberRepository.findAll(new Sort(Sort.Direction.ASC, "group", "name"))) {
            vo = new MemberVO();
            BeanUtils.copyProperties(member, vo);
            memberVOList.add(vo);
        }

        return memberVOList;
    }

    @RequestMapping(value = "/answers", method = RequestMethod.POST)
    public ResultVO runAndSave(InputVO input) throws ExecutionException, InterruptedException {
        Member member = memberRepository.findOne(input.getEmail());

        RunnerResult runnerResult = javaRunner.run(input.getSource(), QuestionRepository.getAnswer(input.getQuestionId()));

        Result result = RunnerResult.convertToEntity(runnerResult, member, input.getQuestionId(), input.getSource());

        resultRepository.save(result);

        return RunnerResult.convertToVO(runnerResult, member);
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public Map<String, List<ResultVO>> getResults() {
        Map<String, List<ResultVO>> result = new HashMap<>();

        for (String group : Arrays.asList("A", "B")) {
            result.put(group, convert(resultRepository.findDistinctResultByMemberGroupGroupByMemberOrderByTimeMillsAsc(group)));
        }

        return result;
    }

    private static List<ResultVO> convert(List<Result> results) {
        List<ResultVO> resultVOList = new ArrayList<>();

        int ranking = 1;
        ResultVO resultVO;
        for (Result result : results) {
            resultVO = new ResultVO();
            resultVO.setRanking(ranking++);
            resultVO.setName(result.getMember().getName());
            resultVO.setGroup(result.getMember().getGroup());
            resultVO.setTimeMillis(result.getTimeMillis());
            resultVO.setAnswer(result.isAnswer());

            resultVO.setSource(result.getSource());

            resultVOList.add(resultVO);
        }

        return resultVOList;

    }
}
