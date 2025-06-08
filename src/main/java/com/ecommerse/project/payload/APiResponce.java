package com.ecommerse.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APiResponce {
    public  String message;
    public Boolean status;
}
