package com.example.graduatedesign.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_major")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long majorId;
    private String majorName;
//    @OneToMany(mappedBy = "major",cascade = CascadeType.ALL)
//    private List<User> users;
}
