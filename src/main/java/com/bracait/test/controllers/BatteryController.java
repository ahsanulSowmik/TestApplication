package com.bracait.test.controllers;

import com.bracait.test.models.Battery;
import com.bracait.test.services.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/batteries")
public class BatteryController {

    @Autowired
    private BatteryService batteryService;

    @PostMapping
    public ResponseEntity<List<Battery>> addBatteries(@RequestBody List<Battery> batteries) {
        List<Battery> savedBatteries = batteryService.addBatteries(batteries);
        return ResponseEntity.ok(savedBatteries);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBatteriesInRange(
            @RequestParam String postcodeRange) {
        String[] range = postcodeRange.split("-");
        String startPostcode = range[0];
        String endPostcode = range[1];
        Map<String, Object> response = batteryService.getBatteriesInRange(startPostcode, endPostcode);
        return ResponseEntity.ok(response);
    }
}
