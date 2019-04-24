package com.example.graduatedesign.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_major")
@ToString(exclude = "users")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long majorId;
    private String majorName;
    @OneToMany(mappedBy = "major",cascade = CascadeType.ALL)
    private Set<User> users;
}
