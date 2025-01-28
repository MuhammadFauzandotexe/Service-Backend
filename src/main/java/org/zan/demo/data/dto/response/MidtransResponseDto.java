package org.zan.demo.data.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MidtransResponseDto {
    private String token;

    @JsonProperty("redirect_url")
    private String redirectUrl;
}
