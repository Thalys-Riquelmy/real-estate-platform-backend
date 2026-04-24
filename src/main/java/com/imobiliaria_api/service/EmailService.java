package com.imobiliaria_api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    
    public void sendEmail(String to, String subject, String body) {
        log.info("=========================================");
        log.info("SIMULANDO ENVIO DE EMAIL PARA: {}", to);
        log.info("ASSUNTO: {}", subject);
        log.info("MENSAGEM: {}", body);
        log.info("=========================================");
    }
}
