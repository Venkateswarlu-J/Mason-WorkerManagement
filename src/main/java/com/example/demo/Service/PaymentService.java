package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.dtoUser.AttendanceDTO;
import com.example.demo.dtoUser.MakePaymentRequest;
import com.example.demo.dtoUser.PaymentWorkerDetailsDTO;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.Roles;
import com.example.demo.repo.AttendanceRepo;
import com.example.demo.repo.TransactionHistoryRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AttendanceRepo attendanceRepo;
    @Autowired
    private WorkerRepo workerRepo;
    @Autowired
    private TransactionHistoryRepo transactionHistoryRepo;
    public List<PaymentWorkerDetailsDTO> getWorkersToPay(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if(!(loggedUser.getRole()== Roles.SUPERVISOR)){
            throw new RuntimeException("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();

        LocalDate today=LocalDate.now();
        LocalDate monday=today.with(DayOfWeek.MONDAY);
        LocalDateTime start = monday.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        Set<Long> seenWorker=new HashSet<>();
        List<WorkerAttendance> listOfWorkers=attendanceRepo.findWorkers(supervisor.getSupId(),monday,today);
        System.out.println(listOfWorkers.size());
        List<PaymentWorkerDetailsDTO> res=new ArrayList<>();
        for(WorkerAttendance workerAttendance:listOfWorkers){
            PaymentWorkerDetailsDTO paymentWorker=new PaymentWorkerDetailsDTO();
            Worker worker=workerAttendance.getWorker();
            if(!seenWorker.add(worker.getWorkerID())) continue;
            paymentWorker.setCategories(worker.getWorkerCat());
            paymentWorker.setPaymentPerDay(worker.getPayPerDay());
            paymentWorker.setWorkerName(worker.getWorkerName());
            long fullDays=attendanceRepo.findFullDays(supervisor,worker,monday,today);
            long halfDays=attendanceRepo.findHalfDays(supervisor,worker,monday,today);
            double totalDays=fullDays+(halfDays*0.5);
            double totalAmount=totalDays*worker.getPayPerDay();
            paymentWorker.setFullDays(fullDays);
            paymentWorker.setHalfDays(halfDays);
            long paid=transactionHistoryRepo.findPaid(supervisor,worker,start,end);
//            long paid=(paidCheck!=null)?paidCheck:0;
            paymentWorker.setTotalAmount(totalAmount);
            paymentWorker.setPaid(paid);
            paymentWorker.setToBePaid(totalAmount-paid);
            paymentWorker.setTotalDays(totalDays);
            PaymentStatus paymentStatus=(paid==totalAmount)?PaymentStatus.COMPLETED:(paid==0)?PaymentStatus.PENDING:PaymentStatus.PARTIAL;
            paymentWorker.setStatus(paymentStatus);

//            System.out.println("worker "+worker.getWorkerName()+" worked on ::"+workerAttendance.getAttendanceDate());
            res.add(paymentWorker);
        }
        return res;

    }

    public String makePayment(MakePaymentRequest makePaymentRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Users loggedUser = userRepo.findByEmail(email);
        if(!(loggedUser.getRole()==Roles.SUPERVISOR)){
            throw new RuntimeException("Access denied");
        }
        Supervisor supervisor = loggedUser.getSupervisor();
        Worker worker= workerRepo.findByWorkerIDAndIsActiveTrue(makePaymentRequest.getWorkerId());
        if(worker==null) return "User Not found";
//        System.out.println("worker "+worker+" "+makePaymentRequest.getAmount());
        TransactionHistory transactionHistory=new TransactionHistory();
        transactionHistory.setCurPaid(makePaymentRequest.getAmount());
        transactionHistory.setSupervisor(supervisor);
        transactionHistory.setPaymentMode(makePaymentRequest.getPaymentMode());
        transactionHistory.setWorker(worker);
        transactionHistory.setPaidAt(LocalDateTime.now());
        transactionHistoryRepo.save(transactionHistory);
        return "Thank you for making payment!!";
    }
}
