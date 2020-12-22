package com.example.codeant.springbootsyncdemo.entity;

import lombok.*;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.12.22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    private String name;
    private String age;
    private String sex;
}
