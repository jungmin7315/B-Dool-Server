package com.bdool.memberhubservice.mail.domain.verification.controller;

import com.bdool.memberhubservice.mail.domain.verification.entity.Verification;
import com.bdool.memberhubservice.mail.domain.verification.entity.model.VerificationModel;
import com.bdool.memberhubservice.mail.domain.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/verification")
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/")
    public ResponseEntity<Verification> createVerification(VerificationModel verificationModel) {
        return ResponseEntity.ok(verificationService.save(verificationModel));
    }

    @GetMapping("/{verificationId}")
    public ResponseEntity<Optional<Verification>> getVerificationById(@PathVariable Long verificationId) {
        return ResponseEntity.ok(verificationService.findById(verificationId));
    }

    @GetMapping("/")
    public ResponseEntity<List<Verification>> getAllVerification() {
        return ResponseEntity.ok(verificationService.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getVerificationCount() {
        return ResponseEntity.ok(verificationService.count());
    }

    @GetMapping("/exists/{verificationId}")
    public ResponseEntity<Boolean> checkVerificationExists(@PathVariable Long verificationId) {
        return ResponseEntity.ok(verificationService.existsById(verificationId));
    }

    @DeleteMapping("/{verificationId}")
    public ResponseEntity<Void> deleteVerificationById(@PathVariable Long verificationId) {
        verificationService.deleteById(verificationId);
        return ResponseEntity.noContent().build();
    }
}
