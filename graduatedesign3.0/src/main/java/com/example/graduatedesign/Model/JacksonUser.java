package com.example.graduatedesign.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("Jacksonuser")
public class JacksonUser {
    @JsonProperty
    private String id;
    @JsonProperty
    private  String userId;
    @JsonProperty
    private  String pwd;
}
