package com.example.umbrellastars;

import com.example.umbrellastars.services.BioWarningService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UmbrellaStarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmbrellaStarsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run (BioWarningService bioWarningService) {
      return args -> {
          System.out.println("Starting App...");
          System.out.println("======All BOWs=====");
          bioWarningService.findAll().forEach(bow ->
                  System.out.println("[S.T.A.R.S-REPORT] Nombre: " + bow.getName() +" | Nivel de Peligro:" + bow.getRiskLevel() + " | Punto Débil: " + bow.getWeakPoint()));
          // Filtrados
          // por virus base
          System.out.println("=====BOWs by base Virus====");
          bioWarningService.findByBaseVirus("G-virus").forEach(bow ->
                  System.out.println("[S.T.A.R.S-REPORT] Name: " + bow.getName() +" | Risk Lvl:" + bow.getRiskLevel() + " | Weak point: " + bow.getWeakPoint()));
          // por estado actual
          System.out.println("=====BOWs by their current state====");
          bioWarningService.findByCurrentState("Eliminated").forEach(bow ->
                  System.out.println("[S.T.A.R.S-REPORT] Name: " + bow.getName() +" | Risk Lvl:" + bow.getRiskLevel() + " | Weak point: " + bow.getWeakPoint()));
          // los que son activos, sin repetir
          System.out.println("=====Free Distinct BOWs====");
          bioWarningService.findDistinctVirus().forEach(bow ->
                  System.out.println("[S.T.A.R.S-REPORT] Name: " + bow.getName() +" | Risk Lvl:" + bow.getRiskLevel() + " | Weak point: " + bow.getWeakPoint()));
      };
    };
}
