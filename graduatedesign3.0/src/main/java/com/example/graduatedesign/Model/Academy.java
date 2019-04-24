package com.example.graduatedesign.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_academy")
@ToString(exclude = "users")
public class Academy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long academyId;
    private String academyName;
    @OneToMany(mappedBy = "academy",cascade = CascadeType.ALL)
    private Set<User> users;
}
