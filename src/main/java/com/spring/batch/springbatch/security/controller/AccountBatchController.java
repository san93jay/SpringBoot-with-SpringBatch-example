package com.spring.batch.springbatch.security.controller;

import com.spring.batch.springbatch.security.dto.AccountDTO;
import com.spring.batch.springbatch.security.dto.AccountSearchDTO;
import com.spring.batch.springbatch.security.services.AccountService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
public class AccountBatchController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Autowired
    private AccountService accountService;

    @PostMapping("/save")
    public ResponseEntity<String> SaveDetails(){
        JobParameters jobParameter=new JobParametersBuilder().addLong("startAt",System.currentTimeMillis()).toJobParameters();
        try{
            jobLauncher.run(job, jobParameter);
            return  new ResponseEntity<>("save successfully", HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>("Bad Request'", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getById")
    public ResponseEntity<List<AccountDTO>> getAccountDetails(@RequestBody AccountSearchDTO searchDTO,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size
                                                              ){
        List<AccountDTO> accountDetails = null;
        if(searchDTO==null){
            return  new ResponseEntity<List<AccountDTO>>(accountDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            Pageable pageable= PageRequest.of(page, size);
            accountDetails=accountService.searchAccountDetails(searchDTO,pageable);
        }
        return  new ResponseEntity<List<AccountDTO>>(accountDetails, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<AccountDTO> updateAccountDetails(@RequestBody @Validated AccountDTO accountDTO){

        return  new ResponseEntity<AccountDTO>(accountService.updateAccountDetails(accountDTO), HttpStatus.OK);
    }

}
