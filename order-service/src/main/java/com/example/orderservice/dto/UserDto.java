package com.example.orderservice.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
