package org.zan.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.zan.demo.data.dto.request.MidtransRequestDto;
import org.zan.demo.data.dto.request.UpdateRequestDto;
import org.zan.demo.data.dto.request.UserInfoRequestDto;
import org.zan.demo.data.dto.response.MidtransResponseDto;
import org.zan.demo.entity.BillOfMonth;
import org.zan.demo.entity.Customer;
import org.zan.demo.entity.User;
import org.zan.demo.entity.WaterGrid;
import org.zan.demo.repository.BillOfMothRepository;
import org.zan.demo.repository.CustomerRepository;
import org.zan.demo.repository.UserRepository;
import org.zan.demo.repository.WaterGridRepository;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl {
    private final UserRepository userRepository;

    private final WaterGridRepository waterGridRepository;

    private final CustomerRepository customerRepository;

    private final BillOfMothRepository billOfMothRepository;

    private final WebClient webClient;

    public UserInfoRequestDto getUserInfo(){

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> {
                    try {
                        throw new AuthenticationException();
                    } catch (AuthenticationException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        WaterGrid waterGrid = waterGridRepository.findByUser(user).orElseThrow(
                () -> {
                    try {
                        throw new AuthenticationException();
                    } catch (AuthenticationException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Customer customer = customerRepository.findByUser(user).orElseThrow(                () -> {
            try {
                throw new AuthenticationException();
            } catch (AuthenticationException e) {
                throw new RuntimeException(e);
            }
        });


        UserInfoRequestDto build = UserInfoRequestDto.builder()
                .statusWaterGrid(waterGrid.getStatus())
                .address(customer.getAddress())
                .username(customer.getUser().getUsername())
                .email(customer.getEmail())
                .totalCurrentUsage(waterGrid.getTotalCurrentUsage())
                .currentUsageThisMonth(waterGrid.getCurrentUsageThisMount())
                .build();

        return build;
    }

    public List<BillOfMonth> getAllBills(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> {
                    try {
                        throw new AuthenticationException();
                    } catch (AuthenticationException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        Customer customer = customerRepository.findByUser(user).orElseThrow(                () -> {
            try {
                throw new AuthenticationException();
            } catch (AuthenticationException e) {
                throw new RuntimeException(e);
            }
        });

        List<BillOfMonth> allByCustomer = billOfMothRepository.findAllByCustomer(customer);
        return allByCustomer;
    }

    public MidtransResponseDto initializePayment(UUID uuid){
        Optional<BillOfMonth> billOfMonth = billOfMothRepository.findById(uuid);
        if(billOfMonth.isPresent()){
            String bookingId = UUID.randomUUID().toString();
            MidtransRequestDto build = MidtransRequestDto.builder()
                    .transactionDetails(MidtransRequestDto.TransactionDetails.builder()
                            .grossAmount(billOfMonth.get().getAmount())
                            .orderId(bookingId)
                            .build()).build();
            MidtransResponseDto midtransResponseDtoMono = webClient.post().uri("/snap/v1/transactions")
                    .header("Authorization", "Basic U0ItTWlkLXNlcnZlci1aVm5IZmV0eTZxM1FTRkZaWkoxYnhKcVM6")
                    .bodyValue(build)
                    .retrieve()
                    .bodyToMono(MidtransResponseDto.class).block();

            billOfMonth.get().setBookingId(bookingId);
            billOfMonth.get().setSnapUrl(midtransResponseDtoMono.getRedirectUrl());
            billOfMonth.get().setStatus("PENDING");
            billOfMothRepository.save(billOfMonth.get());
            return midtransResponseDtoMono;
        }
        return null;
    };

    public void update(UpdateRequestDto updateRequestDto){
        WaterGrid waterGrid = waterGridRepository.findById(updateRequestDto.getId()).get();
        waterGrid.setTotalCurrentUsage(waterGrid.getTotalCurrentUsage().add(updateRequestDto.getQuantity()));
        waterGrid.setCurrentUsageThisMount(waterGrid.getCurrentUsageThisMount().add(updateRequestDto.getQuantity()));
        waterGridRepository.save(waterGrid);
    }

}
