package org.zan.demo.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(toBuilder = true)
public class Esp32ResponseDto {
    private String data;
}
