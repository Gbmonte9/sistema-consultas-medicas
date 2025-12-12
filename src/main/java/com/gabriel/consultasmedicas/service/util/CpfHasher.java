// src/main/java/com/gabriel/consultasmedicas/service/util/CpfHasher.java
package com.gabriel.consultasmedicas.service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CpfHasher {

    @Value("${security.cpf.hash-secret}")
    private String hashSecret;

    public String hash(String rawCpf) {
        try {
       
            String saltedCpf = rawCpf + hashSecret;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(saltedCpf.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash SHA-256", e);
        }
    }
}