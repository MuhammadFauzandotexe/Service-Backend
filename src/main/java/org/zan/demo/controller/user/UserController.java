package org.zan.demo.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zan.demo.data.dto.request.UpdateRequestDto;
import org.zan.demo.entity.BillOfMonth;
import org.zan.demo.service.impl.CustomerServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-info")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final CustomerServiceImpl customerService;

    @GetMapping
    public ResponseEntity<?> getUserInfo(){
        return ResponseEntity.ok(customerService.getUserInfo());
    }

    @GetMapping("/bills")
    public ResponseEntity<?> getAllBills(){
        List<BillOfMonth> bills = customerService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/initalize/{id}")
    public ResponseEntity<?> payment(@PathVariable UUID id){
        return ResponseEntity.ok(customerService.initializePayment(id));
    }

    @PostMapping("/update")
    public ResponseEntity<?> requestUpdate(@RequestBody UpdateRequestDto updateRequestDto){
        customerService.update(updateRequestDto);
        return ResponseEntity.ok("success");
    }

}
