package com.projcet.tstorycopyproject.domain.mail.service;

import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.UserRepository;
import com.projcet.tstorycopyproject.global.utils.CommonUtils;
import com.projcet.tstorycopyproject.global.utils.RedisUtils;
import com.projcet.tstorycopyproject.domain.mail.errorcode.EmailErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtils redisUtils;
    private final UserRepository userRepository;


    public void sendEmailForCertification(String email) throws Exception {
        log.info("send email for certification");
        String certificationNumber = CommonUtils.createCertificationNumber();
        String email1 = email; // 받는 사람 이메일 주소
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
                "저희 프로젝트 테스트에 참여해 주셔서 감사합니다." + 	//html 형식으로 작성 !
                        "<br><br>" +
                        "인증 번호는 " + certificationNumber + "입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요"; //이메일 내용 삽입
        redisUtils.setDataExpire("certification" + email, certificationNumber, 60 * 5L);
        sendMail(email1, title, content);
    }

    private void sendMail(String email ,String title, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject(title);
        helper.setText(content);
        javaMailSender.send(mimeMessage);
    }
    public void verifyEmail(String email, String certificationNumber) {
        if (!isVerify(email, certificationNumber)) {
            throw new CustomException(EmailErrorCode.INVALID_CERTIFICATION_NUMBER);
        }
    }

    private boolean isVerify(String email, String certificationNumber) {
        boolean validatedEmail = isEmailExists(email);
        if (!isEmailExists(email)) {
            throw new CustomException(EmailErrorCode.NOT_FOUND_EMAIL);
        }
        return (validatedEmail &&
                redisUtils.getData(email).equals(certificationNumber));
    }

    private boolean isEmailExists(String email) {
        return redisUtils.isExists(email);
    }
    public boolean userEmailDuplicationCheck(String email) {
        return userRepository.findByUserEmail(email).isPresent();
    }


}
