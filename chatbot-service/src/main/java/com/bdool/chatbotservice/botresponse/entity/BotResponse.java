package com.bdool.chatbotservice.botresponse.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bot_responses")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BotResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String responseText;
    private String modelUsed;
}
