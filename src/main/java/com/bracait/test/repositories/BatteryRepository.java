package com.bracait.test.repositories;


import com.bracait.test.models.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
    List<Battery> findByPostcodeBetweenOrderByNameAsc(String startPostcode, String endPostcode);
}
