package kr.co.kware.festival.web;

import kr.co.kware.festival.KFestivalApplication;
import kr.co.kware.festival.JavaSourceGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KFestivalApplication.class)
public class ControllerTest {

    private static final int testQuestionId = 1;
    private static final String email = "sooyoung32@kware.co.kr";

    @Autowired
    private Controller controller;

    @Test
    public void testGetMembers() throws Exception {
        List<MemberVO> members = controller.getMembers();
        assertThat(members.size(), is(15));
    }

    @Test
    public void testRunAndSave() throws Exception {
        InputVO input = new InputVO();
        input.setQuestionId(testQuestionId);
        input.setEmail(email);
        input.setSource(JavaSourceGenerator.generateSource(testQuestionId));

        ResultVO resultVO = controller.runAndSave(input);

        assertThat(resultVO.isHasError(), is(false));
        assertThat(resultVO.getErrorOutput(), isEmptyOrNullString());
        assertThat(resultVO.getNormalOutput(), notNullValue());
    }

    @Test
    public void testRunErrorAndSave() throws Exception {
        InputVO input = new InputVO();
        input.setQuestionId(testQuestionId);
        input.setEmail(email);
        input.setSource(JavaSourceGenerator.generateErrorSource(testQuestionId));

        ResultVO resultVO = controller.runAndSave(input);

        assertThat(resultVO.isHasError(), is(true));
        assertThat(resultVO.getErrorOutput(), notNullValue());
        assertThat(resultVO.getNormalOutput(), isEmptyOrNullString());
    }
}
