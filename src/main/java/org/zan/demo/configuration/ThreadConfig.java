package org.zan.demo.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.zan.demo.entity.BillOfMonth;
import org.zan.demo.repository.BillOfMothRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ThreadConfig {

    private final WebClient webClient;
    private final BillOfMothRepository billOfMothRepository;

    @PostConstruct
    public void startThread() {
        Thread thread = new Thread(this::processPendingBills);
        thread.setDaemon(true);
        thread.start();
    }

    private void processPendingBills() {
        while (true) {
            try {
                List<BillOfMonth> pendingBills = billOfMothRepository.findAllByStatus("PENDING");

                for (BillOfMonth bill : pendingBills) {
                    try {
                        InquiryResponseDto response = webClient.get()
                                .uri("https://api.sandbox.midtrans.com/v2/" + bill.getBookingId() + "/status")
                                .header("Authorization", "Basic U0ItTWlkLXNlcnZlci1aVm5IZmV0eTZxM1FTRkZaWkoxYnhKcVM6")
                                .retrieve()
                                .bodyToMono(InquiryResponseDto.class)
                                .block(); // Blocking call (sync request)

                        if (response != null && "settlement".equalsIgnoreCase(response.getTransactionStatus())) {
                            bill.setStatus("SETTLEMENT");
                            billOfMothRepository.save(bill); // Simpan perubahan ke DB
                            log.info("Updated bill {} to SETTLEMENT", bill.getBookingId());
                        }
                    } catch (WebClientResponseException e) {
                        log.error("Error calling API for bill {}: {}", bill.getBookingId(), e.getResponseBodyAsString());
                    } catch (Exception e) {
                        log.error("Unexpected error processing bill {}: {}", bill.getBookingId(), e.getMessage(), e);
                    }
                }

                log.info("Thread is running...");
                Thread.sleep(5000); // Tunggu 5 detik sebelum iterasi berikutnya
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Thread interrupted!");
                break;
            } catch (Exception e) {
                log.error("Unexpected error in thread loop: {}", e.getMessage(), e);
            }
        }
    }

    @Setter
    @Getter
    @Builder
    public static class InquiryResponseDto {
        @JsonProperty("status_code")
        private String statusCode;

        @JsonProperty("transaction_status")
        private String transactionStatus;
    }
}
