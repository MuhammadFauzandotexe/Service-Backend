package org.zan.demo.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class GeneralResponse <T>{
    private String message;
    private T data;
}
