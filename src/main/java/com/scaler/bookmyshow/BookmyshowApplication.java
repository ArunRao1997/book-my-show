package com.scaler.bookmyshow;

import com.scaler.bookmyshow.models.BaseModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  BookmyshowApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmyshowApplication.class, args);
        BaseModel baseModel = new BaseModel();
//        baseModel.setId(1L);
//        System.out.println(baseModel);
    }

}
