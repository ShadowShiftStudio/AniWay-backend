package com.shadowshiftstudio.aniway.service.auth;

import com.shadowshiftstudio.aniway.dto.auth.EmailDetails;

public interface IEmailService {
    String sendMail(EmailDetails details);
}
