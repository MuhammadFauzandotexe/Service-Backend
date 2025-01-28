package org.zan.demo.data.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder(toBuilder = true)
public class UserInfoRequestDto {
    private String username;
    private String email;
    private Boolean statusWaterGrid;
    private BigDecimal totalCurrentUsage;
    private BigDecimal currentUsageThisMonth;
    private String address;
}
