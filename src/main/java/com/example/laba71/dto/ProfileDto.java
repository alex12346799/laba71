package com.example.laba71.dto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {
    private String readerName;
    private String libraryCardNumber;
    private List<LoanViewDto> loans;

    // для фильтра
    private LocalDate fromDate;
    private LocalDate toDate;
}
