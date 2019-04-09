package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_private_message")
public class PrivateMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long messageId;
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="sender_id")
    private User sender;
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
    private int messageType;
    @Column(columnDefinition = "longtext")
    private long messageContent;
    private Calendar sendTime;
    private int status;
    private int deleted;
}
