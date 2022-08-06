package com.mbtree.mbtree.domain.message;

import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;
    @After
    public void cleanup(){
        messageRepository.deleteAll();
    }

    @Test
    public void  sendMessage() {
        //int id = 2;
        /*User tree = "나무주인";
        User writer = "작성자";*/
        String content = "Test0524";

        messageRepository.save(Message.builder().content(content).build());

        List<Message> messageList = messageRepository.findAll();

        Message message = messageList.get(0);
        assertThat(message.getContent()).isEqualTo(content);
    }

}
