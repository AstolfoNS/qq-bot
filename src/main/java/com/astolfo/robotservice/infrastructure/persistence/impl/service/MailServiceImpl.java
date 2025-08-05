package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.domain.service.MailService;
import com.astolfo.robotservice.infrastructure.common.constants.LogConstant;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    public void sendLogFileToEmail(String toEmail) {
        File infoFile = new File(System.getProperty("user.dir") + LogConstant.INFO_RELATIVE_PATH);
        File errorFile = new File(System.getProperty("user.dir") + LogConstant.ERROR_RELATIVE_PATH);

        if (!infoFile.exists()) {
            throw new RuntimeException("info日志文件不存在");
        }
        if (!errorFile.exists()) {
            throw new RuntimeException("error日志文件不存在");
        }

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("日志文件 - [INFO, ERROR]");
            helper.setText("请查看附件中的日志文件", false);
            helper.addAttachment("info.log", new FileSystemResource(infoFile));
            helper.addAttachment("error.log", new FileSystemResource(errorFile));

            mailSender.send(message);

            log.info("日志文件发送成功至: {}", toEmail);
        } catch (MessagingException exception) {
            log.error("发送日志邮件失败", exception);

            throw new RuntimeException("发送日志邮件失败", exception);
        }
    }

}
