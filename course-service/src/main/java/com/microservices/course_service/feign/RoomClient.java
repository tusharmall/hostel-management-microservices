package com.microservices.course_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.microservices.course_service.response.RoomResponse;

@FeignClient(name = "ROOM-SERVICE")
public interface RoomClient {

    @GetMapping("/rooms")
    List<RoomResponse> getRooms();
}