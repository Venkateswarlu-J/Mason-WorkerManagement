package com.example.demo.Controllers;

import com.example.demo.Model.TransactionHistory;
import com.example.demo.Service.PaymentService;
import com.example.demo.Service.TransactionService;
import com.example.demo.dtoUser.MakePaymentRequest;
import com.example.demo.dtoUser.PaymentWorkerDetailsDTO;
import com.example.demo.dtoUser.TransactionHistoryDTO;
import com.example.demo.repo.TransactionHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TransactionService transactionService;


    @GetMapping("/getWorkersToPay")
    public List<PaymentWorkerDetailsDTO> getWorkersToPay(){
        return paymentService.getWorkersToPay();
    }

    @PostMapping("/makePayment")
    public String makePayment(@RequestBody MakePaymentRequest makePaymentRequest){
//        System.out.println(makePaymentRequest);
        return paymentService.makePayment(makePaymentRequest);
    }

    @GetMapping("/getTransactionHistory")
    public List<TransactionHistoryDTO> getTransactionHistory(){
        return transactionService.getTransactionHistory();
    }
}
