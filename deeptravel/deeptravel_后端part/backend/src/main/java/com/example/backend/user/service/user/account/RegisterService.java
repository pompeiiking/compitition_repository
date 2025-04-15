package com.example.backend.user.service.user.account;

import java.util.Map;

public interface RegisterService {
    Map<String,String> register(
            String username,
            String password,
            String confirmedPassword,
            String gender,           // 新增
            String incomeLevel,      // 新增
            String travelModePreference,
            String activityTypePreference,
            String travelRestriction,
            String rests
    );
}
