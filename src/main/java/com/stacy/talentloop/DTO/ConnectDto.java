package com.stacy.talentloop.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectDto {
    private String id;
    private String username;
    private String fullName;
    private String profileImageUrl;
}
