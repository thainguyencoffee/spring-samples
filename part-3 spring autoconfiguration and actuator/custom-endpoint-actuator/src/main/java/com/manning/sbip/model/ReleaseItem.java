package com.manning.sbip.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseItem {
    private String itemId;
    private String itemDescription;
}
