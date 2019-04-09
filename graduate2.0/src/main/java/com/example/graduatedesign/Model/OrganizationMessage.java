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
@Entity(name = "t_organization_message")
public class OrganizationMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long messageId;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="sender_id")
    private Organization sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    private int messageType;
    @Column(columnDefinition = "longtext")
    private long messageContent;
    private Calendar sendTime;
    private int status;
    private int deleted;
}
