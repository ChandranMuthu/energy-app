package com.example.crudwithvaadin;

import com.example.crudwithvaadin.model.EnergyOutput;
import com.example.crudwithvaadin.model.Role;
import com.example.crudwithvaadin.model.User;
import com.example.crudwithvaadin.repository.EnergyOutputRepository;
import com.example.crudwithvaadin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class CrudWithVaadinApplication {

    private static final Logger log = LoggerFactory.getLogger(CrudWithVaadinApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrudWithVaadinApplication.class);
    }

    @Bean
    public CommandLineRunner loadData(EnergyOutputRepository repository, UserService userService) {
        return (args) -> {
            repository.deleteAll();
            EnergyOutput energyOutput = new EnergyOutput("1", "101", "1", LocalDateTime.now(), "ANC", "200", "12343", 50, "A", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());
            EnergyOutput energyOutput1 = new EnergyOutput("1", "102", "1", LocalDateTime.now(), "ANC", "200", "12343", 80, "B", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());
            EnergyOutput energyOutput11 = new EnergyOutput("1", "102", "1", LocalDateTime.now(), "ANC", "200", "12343", 85, "C", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());

            EnergyOutput energyOutput2 = new EnergyOutput("2", "103", "1", LocalDateTime.now().minusDays(1), "ANC", "200", "12343", 40, "A", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());
            EnergyOutput energyOutput3 = new EnergyOutput("2", "104", "1", LocalDateTime.now().minusDays(1), "ANC", "200", "12343", 65, "B", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());
            EnergyOutput energyOutput31 = new EnergyOutput("2", "104", "1", LocalDateTime.now().minusDays(1), "ANC", "200", "12343", 76, "C", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());

            EnergyOutput energyOutput4 = new EnergyOutput("3", "105", "1", LocalDateTime.now().minusDays(2), "ANC", "200", "12343", 30, "A", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());
            EnergyOutput energyOutput5 = new EnergyOutput("3", "106", "1", LocalDateTime.now().minusDays(2), "ANC", "200", "12343", 60, "B", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());
            EnergyOutput energyOutput51 = new EnergyOutput("3", "106", "1", LocalDateTime.now().minusDays(2), "ANC", "200", "12343", 50, "C", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());

            EnergyOutput energyOutput6 = new EnergyOutput("4", "107", "1", LocalDateTime.now().minusDays(3), "ANC", "200", "12343", 60, "A", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());
            EnergyOutput energyOutput7 = new EnergyOutput("4", "108", "1", LocalDateTime.now().minusDays(3), "ANC", "200", "12343", 90, "B", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());
            EnergyOutput energyOutput71 = new EnergyOutput("4", "108", "1", LocalDateTime.now().minusDays(3), "ANC", "200", "12343", 78, "C", 2, "1", LocalDateTime.now(), "Chandran", "Chandran", LocalDateTime.now());

            repository.save(energyOutput6);
            repository.save(energyOutput7);
            repository.save(energyOutput71);
            repository.save(energyOutput);
            repository.save(energyOutput1);
            repository.save(energyOutput11);
            repository.save(energyOutput2);
            repository.save(energyOutput3);
            repository.save(energyOutput31);
            repository.save(energyOutput4);
            repository.save(energyOutput5);
            repository.save(energyOutput51);
            log.info("Static energy data is loaded");
            //userRepository.deleteAll();
            User user = new User("Admin", "admin", "admin", "admin1234", "admin@gmail.com", Role.ADMIN);
            userService.saveUser(user);
        };
    }
}
