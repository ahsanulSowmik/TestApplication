package com.bracait.test.services.serviceImpl;


import com.bracait.test.exceptions.BatteryNotFoundException;
import com.bracait.test.exceptions.InvalidBatteryException;
import com.bracait.test.models.Battery;
import com.bracait.test.repositories.BatteryRepository;
import com.bracait.test.services.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BatteryServiceImpl implements BatteryService {

    @Autowired
    private BatteryRepository batteryRepository;

    @Override
    public List<Battery> addBatteries(List<Battery> batteries) {
        validateBatteries(batteries);
        return batteryRepository.saveAll(batteries);
    }

    @Override
    public Map<String, Object> getBatteriesInRange(String startPostcode, String endPostcode) {
        List<Battery> batteries = batteryRepository.findByPostcodeBetweenOrderByNameAsc(startPostcode, endPostcode);
        if (batteries.isEmpty()) {
            throw new BatteryNotFoundException("No batteries found in the given postcode range");
        }

        List<String> batteryNames = batteries.stream()
                .map(Battery::getName)
                .collect(Collectors.toList());

        int totalWattCapacity = batteries.stream()
                .mapToInt(Battery::getWattCapacity)
                .sum();

        double averageWattCapacity = batteries.isEmpty() ? 0 : totalWattCapacity / (double) batteries.size();

        return Map.of(
                "batteryNames", batteryNames,
                "statistics", Map.of(
                        "totalWattCapacity", totalWattCapacity,
                        "averageWattCapacity", averageWattCapacity
                )
        );
    }

    private void validateBatteries(List<Battery> batteries) {
        for (Battery battery : batteries) {
            if (battery.getName() == null || battery.getName().isEmpty()) {
                throw new InvalidBatteryException("Battery name cannot be null or empty");
            }
            if (battery.getPostcode() == null || battery.getPostcode().isEmpty()) {
                throw new InvalidBatteryException("Battery postcode cannot be null or empty");
            }
            if (battery.getWattCapacity() <= 0) {
                throw new InvalidBatteryException("Battery watt capacity must be greater than zero");
            }
        }
    }
}