package com.baizhi;

import com.baizhi.dao.FeedbackMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class YingxueBixApplicationTests {

    @Resource
    FeedbackMapper feedbackMapper;
    @Test
    void contextLoads() {
       List<Feedback> feedbacks = feedbackMapper.selectAll();

        for (Feedback feedback : feedbacks) {
            System.out.println(feedback);
        }

    }

}
