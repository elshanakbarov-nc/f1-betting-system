package com.betservice.provider.ergast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.betservice.rest.dto.DriverDTO;
import com.betservice.rest.dto.EventDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenF1Provider implements F1EventProvider{

    private static final String SESSION_URL = "https://api.openf1.org/v1/sessions";
    private static final String DRIVER_URL = "https://api.openf1.org/v1/drivers";

    private final RestTemplate restTemplate;
    private final Random random = new Random();


    @Override
    public List<EventDTO> listEvents(Optional<String> sessionType, Optional<String> year, Optional<String> country) {
        StringBuilder urlBuilder = new StringBuilder(SESSION_URL).append("?");
        sessionType.ifPresent(st -> urlBuilder.append("session_type=").append(st).append("&"));
        year.ifPresent(y -> urlBuilder.append("year=").append(y).append("&"));
        country.ifPresent(c -> urlBuilder.append("country_name=").append(c).append("&"));

        String url = urlBuilder.toString();

        System.out.println("Fetching sessions from URL: " + url);

        SessionResponse[] sessions = restTemplate.getForObject(url, SessionResponse[].class);
        if (sessions == null) return List.of();

        List<EventDTO> eventDTOs = new ArrayList<>();
        for (SessionResponse session : sessions) {
            List<DriverDTO> drivers = fetchDriverMarketForSession(session.sessionKey());
            eventDTOs.add(new EventDTO(
                    session.sessionKey().toString(),
                    session.sessionName(),
                    session.dateStart(),
                    session.sessionType(),
                    session.countryName(),
                    drivers
            ));
        }
        return eventDTOs;
    }

    private List<DriverDTO> fetchDriverMarketForSession(Integer sessionKey) {
        String url = DRIVER_URL + "?session_key=" + sessionKey;
        DriverResponse[] driverResponses = restTemplate.getForObject(url, DriverResponse[].class);

        List<DriverDTO> driverDTOS = new ArrayList<>();
        if (driverResponses != null) {
            for (DriverResponse dr : driverResponses) {
                if (dr.driverNumber() != null) {
                    driverDTOS.add(
                            new DriverDTO(
                                    dr.driverNumber().toString(),
                                    dr.fullName(),
                                    randomOdds()
                            )
                    );
                }
            }
        }
        return driverDTOS;
    }

    private int randomOdds() {
        return List.of(2, 3, 4).get(random.nextInt(3));
    }
}
