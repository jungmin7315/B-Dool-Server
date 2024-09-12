package com.bdool.chatbotservice.chathistory.entity;

import com.bdool.chatbotservice.botresponse.entity.BotResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_histories")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long workspaceId;
    private Long profileId;
    private String question;

    @ManyToOne
    @JoinColumn(name = "bot_response_id")
    private BotResponse botResponse;

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}

