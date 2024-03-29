package com.gfa.pfp.controllers;

import com.gfa.pfp.exception.PfpException;
import com.gfa.pfp.exception.UnauthorizedException;
import com.gfa.pfp.exception.financeexceptions.FinanceNotFoundException;
import com.gfa.pfp.models.dto.finance.FinancesMonthlyDTO;
import com.gfa.pfp.models.dto.finance.GetAllIncomeDTO;
import com.gfa.pfp.models.dto.finance.GetIncomeDTO;
import com.gfa.pfp.services.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeApiController {

    public final IncomeService incomeService;

    @PostMapping("/add")
    ResponseEntity<GetIncomeDTO> createNewIncome(@RequestParam(value = "json") String income,
                                                 @RequestParam(required = false, value = "file") MultipartFile file,
                                                 @RequestHeader String authorization)
            throws FinanceNotFoundException, IOException {
        return ResponseEntity.ok().body(incomeService.createNewIncome(income, file, authorization));
    }

    @GetMapping("/getAll")
    ResponseEntity<GetAllIncomeDTO> readAllIncomes(@RequestHeader String authorization)
            throws FinanceNotFoundException {
        return ResponseEntity.ok().body(incomeService.getAllIncomes(authorization));
    }

    @PatchMapping("/update/{id}")
    ResponseEntity<GetIncomeDTO> updateIncome(@PathVariable Long id,
                                              @RequestParam(value = "json") String income,
                                              @RequestParam(required = false, value = "file") MultipartFile file,
                                              @RequestHeader String authorization)
            throws UnauthorizedException, FinanceNotFoundException, IOException {
        return ResponseEntity.ok().body(incomeService.updateIncome(id, income, file, authorization));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteIncome(@RequestHeader String authorization, @PathVariable Long id)
            throws UnauthorizedException, FinanceNotFoundException {
        incomeService.deleteIncome(id, authorization);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/each-month")
    ResponseEntity<List<FinancesMonthlyDTO>> monthlyIncome(@RequestHeader String authorization) throws
            PfpException {
        return ResponseEntity.ok().body(incomeService.monthlyIncomes(authorization));
    }

}