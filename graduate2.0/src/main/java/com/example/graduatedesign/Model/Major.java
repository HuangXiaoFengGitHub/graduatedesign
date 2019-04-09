package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
