package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long attachmentId;
    private String title;
    @Column(columnDefinition = "longtext")
    private String attachmentDesc;
    @Column(columnDefinition = "text")
    private String attachmentAddr;
    private int downloadCount;
    @ManyToOne
    @JoinColumn(name="activity_id")
    private Activity activity;
    @ManyToOne
    @JoinColumn(name="organization_id")
    private Organization organization;

}
