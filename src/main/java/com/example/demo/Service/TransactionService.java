package com.example.demo.Service;

import com.example.demo.Model.Supervisor;
import com.example.demo.Model.TransactionHistory;
import com.example.demo.Model.Users;
import com.example.demo.dtoUser.TransactionHistoryDTO;
import com.example.demo.enums.Roles;
import com.example.demo.repo.TransactionHistoryRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private TransactionHistoryRepo transactionHistoryRepo;
    @Autowired
    private UserRepo userRepo;

    public List<TransactionHistoryDTO> getTransactionHistory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if(!(loggedUser.getRole()== Roles.SUPERVISOR)){
            throw new RuntimeException("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        List<TransactionHistory> list=transactionHistoryRepo.findBySupervisor_SupId(supervisor.getSupId());
        List<TransactionHistoryDTO> res=new ArrayList<>();
        for(TransactionHistory t:list){
            TransactionHistoryDTO transactionHistoryDTO=new TransactionHistoryDTO();
            transactionHistoryDTO.setCurPaid(t.getCurPaid());
            transactionHistoryDTO.setPaidAt(t.getPaidAt());
            transactionHistoryDTO.setPaymentMode(t.getPaymentMode());
            transactionHistoryDTO.setWorkerId(t.getWorker().getWorkerID());
            res.add(transactionHistoryDTO);
        }
        return res;
    }
}
