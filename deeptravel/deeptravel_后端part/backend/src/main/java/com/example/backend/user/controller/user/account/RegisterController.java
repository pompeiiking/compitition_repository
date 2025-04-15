package com.example.backend.user.controller.user.account;

import com.example.backend.user.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;


    @PostMapping("/user/account/register/")
    public Map<String,String> register(@RequestParam Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");
        String confirmedPassword = map.get("confirmedPassword");
        String gender = map.get("gender");
        String incomeLevel = map.get("incomeLevel");
        String travelModePreference = map.get("travelModePreference");
        String activityTypePreference = map.get("activityTypePreference");
        String travelRestriction = map.get("travelRestriction");
        String rests = map.get("rests");
        return registerService.register(username,password,confirmedPassword,gender,incomeLevel,travelModePreference,activityTypePreference,travelRestriction,rests);
    }

}
