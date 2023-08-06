package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.EmailDetails;

public interface IEmailService {
    String sendMail(EmailDetails details);
}
