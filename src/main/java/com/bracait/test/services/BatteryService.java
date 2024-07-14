package com.bracait.test.services;

import com.bracait.test.models.Battery;

import java.util.List;
import java.util.Map;

public interface BatteryService {
    List<Battery> addBatteries(List<Battery> batteries);
    Map<String, Object> getBatteriesInRange(String startPostcode, String endPostcode);
}