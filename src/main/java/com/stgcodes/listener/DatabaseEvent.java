package com.stgcodes.listener;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DatabaseEvent  {
    private String step;
    private String message;
}
