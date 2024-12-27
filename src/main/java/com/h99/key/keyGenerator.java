package com.h99.key;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class keyGenerator {
    public static void main(String[] args){
        SecureRandom secureRandom = new SecureRandom();

        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        String secretKey = Base64.getEncoder().encodeToString(key);
        log.info(secretKey);
    }

}
