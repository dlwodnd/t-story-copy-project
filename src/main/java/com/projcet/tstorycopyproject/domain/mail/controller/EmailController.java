package com.projcet.tstorycopyproject.domain.mail.controller;

import com.projcet.tstorycopyproject.global.CustomResponse;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.domain.mail.errorcode.EmailErrorCode;
import com.projcet.tstorycopyproject.domain.mail.request.EmailCertificationRq;
import com.projcet.tstorycopyproject.domain.mail.response.EmailCertificationRp;
import com.projcet.tstorycopyproject.domain.mail.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mails")
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/send-certification")
    public ResponseEntity<CustomResponse<Void>> sendCertificationNumber(@Validated @RequestBody EmailCertificationRq rq) throws Exception {
        if (emailService.userEmailDuplicationCheck(rq.getEmail())) {
            throw new CustomException(EmailErrorCode.ALREADY_USED_EMAIL);
        }
        emailService.sendEmailForCertification(rq.getEmail());
        return ResponseEntity.ok(new CustomResponse<>());
    }
    @GetMapping("/verify")
    public ResponseEntity<CustomResponse<EmailCertificationRp>> verifyCertificationNumber(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "certificationNumber") String certificationNumber) {
        emailService.verifyEmail(email, certificationNumber);

        return ResponseEntity.ok(new CustomResponse<>(EmailCertificationRp.builder()
                .email(email)
                .result(1)
                .build()));
    }

}
