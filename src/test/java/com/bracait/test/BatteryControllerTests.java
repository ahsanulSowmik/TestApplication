package com.bracait.test;

import com.bracait.test.models.Battery;
import com.bracait.test.repositories.BatteryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BatteryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BatteryRepository batteryRepository;

    @Test
    public void testAddBatteries() throws Exception {
        String json = "[{\"name\": \"Battery1\", \"postcode\": \"12345\", \"wattCapacity\": 100}," +
                "{\"name\": \"Battery2\", \"postcode\": \"23456\", \"wattCapacity\": 200}]";

        mockMvc.perform(post("/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("Battery1"))
                .andExpect(jsonPath("$[1].name").value("Battery2"));
    }

    @Test
    public void testGetBatteriesInRange() throws Exception {
        batteryRepository.saveAll(List.of(
                new Battery(null, "Battery1", "12345", 100),
                new Battery(null, "Battery2", "23456", 200),
                new Battery(null, "Battery3", "34567", 150)
        ));

        mockMvc.perform(get("/batteries?postcodeRange=20000-30000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batteryNames").isArray())
                .andExpect(jsonPath("$.statistics.totalWattCapacity").value(350))
                .andExpect(jsonPath("$.statistics.averageWattCapacity").value(175.0));
    }
}
