package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.domain.service.MailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender mailSender;

    private static final String logRelativePath = "/logs/application.log";

    public void sendLogFileToEmail(String toEmail) {
        File logFile = new File(System.getProperty("user.dir") + logRelativePath);

        if (!logFile.exists()) {
            throw new RuntimeException("日志文件不存在: " + logRelativePath);
        }

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("日志文件 - application.log");
            helper.setText("请查看附件中的日志文件", false);
            helper.addAttachment("application.log", new FileSystemResource(logFile));

            mailSender.send(message);

            log.info("日志文件发送成功至: {}", toEmail);
        } catch (MessagingException exception) {
            log.error("发送日志邮件失败", exception);

            throw new RuntimeException("发送日志邮件失败", exception);
        }
    }

}
