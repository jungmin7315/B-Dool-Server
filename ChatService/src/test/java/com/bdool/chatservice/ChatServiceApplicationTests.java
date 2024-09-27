package com.bdool.chatservice;

import com.bdool.chatservice.model.Enum.SessionType;
import com.bdool.chatservice.model.domain.SessionModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.model.repository.MessageRepository;
import com.bdool.chatservice.service.MessageService;
import com.bdool.chatservice.service.SessionService;
import com.opencsv.CSVReader;
import org.apache.kafka.common.protocol.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ChatServiceApplicationTests {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void contextLoads() {

        List<MessageEntity> messages = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(""))) {
            String[] nextLine;
            reader.readNext(); // 헤더 스킵
            while ((nextLine = reader.readNext()) != null) {
                MessageEntity message = MessageEntity.builder()
                        .messageId(UUID.fromString(nextLine[0])) // messageId
                        .channelId(UUID.fromString(nextLine[1])) // channelId
                        .content(nextLine[2]) // content
                        .sendDate(LocalDateTime.parse(nextLine[3], DateTimeFormatter.ISO_DATE_TIME)) // sendDate
                        .isEdited(Boolean.parseBoolean(nextLine[4])) // isEdited
                        .isDeleted(Boolean.parseBoolean(nextLine[5])) // isDeleted
                        .parentMessageId(nextLine[6].isEmpty() ? null : UUID.fromString(nextLine[6])) // parentMessageId
                        .participantId(UUID.fromString(nextLine[7])) // participantId
                        .fileUrl(nextLine[8].isEmpty() ? null : nextLine[8]) // fileUrl
                        .build();

                messages.add(message);
            }
            // MongoDB에 데이터 저장
            messageRepository.saveAll(messages);
            System.out.println("CSV 데이터가 MongoDB에 성공적으로 삽입되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
