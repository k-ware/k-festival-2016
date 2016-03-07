package kr.co.kware.festival.runner;

import kr.co.kware.festival.repository.Member;
import kr.co.kware.festival.repository.Result;
import kr.co.kware.festival.web.ResultVO;
import lombok.Data;

import java.util.Date;

@Data
public class RunnerResult {
    private ProcessResult lastProcessResult;
    private boolean isAnswer = false;

    public static Result convertToEntity(RunnerResult runnerResult, Member member, int questionId, String source) {
        ProcessResult lastProcessResult = runnerResult.getLastProcessResult();

        Result entity = new Result();

        entity.setMember(member);

        entity.setQuestionId(questionId);
        entity.setSource(source);
        entity.setNormalOutput(lastProcessResult.getNormalOutput());

        if (lastProcessResult.hasError()) {
            entity.setHasError(lastProcessResult.hasError());
            entity.setErrorCommand(lastProcessResult.getType().toString());
        }
        entity.setAnswer(runnerResult.isAnswer());
        entity.setErrorOutput(lastProcessResult.getErrorOutput());
        entity.setTimeMillis(lastProcessResult.getExecuteTimeMillis());
        entity.setRegDt(new Date());

        return entity;
    }

    public static ResultVO convertToVO(RunnerResult runnerResult, Member member) {
        ProcessResult lastProcessResult = runnerResult.getLastProcessResult();

        ResultVO vo = new ResultVO();
        vo.setName(member.getName());
        vo.setGroup(member.getGroup());
        vo.setHasError(lastProcessResult.hasError());
        vo.setTimeMillis(lastProcessResult.getExecuteTimeMillis());
        vo.setNormalOutput(lastProcessResult.getNormalOutput());
        vo.setErrorOutput(lastProcessResult.getErrorOutput());
        vo.setAnswer(runnerResult.isAnswer());

        return vo;
    }
}
