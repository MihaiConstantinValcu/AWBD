package com.awbd.cinemaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CinemaApiApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(CinemaApiApplication.class, args);

    }

}
