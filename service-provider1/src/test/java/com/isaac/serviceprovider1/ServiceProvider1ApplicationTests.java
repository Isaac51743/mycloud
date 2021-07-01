package com.isaac.serviceprovider1;

import com.isaac.serviceprovider1.domain.ElasticUser;
import com.isaac.serviceprovider1.domain.Ticket;
//import com.isaac.serviceprovider1.repository.ElasticSearchDao;
import com.isaac.serviceprovider1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@SpringBootTest
class ServiceProvider1ApplicationTests {

    @Autowired
    UserRepository repository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate; // key and val both String

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Test
    void contextLoads() {
        System.out.println(repository.getUserSpecifyPassword("a"));
    }

    @Test
    void appendTest() {
//        stringRedisTemplate.opsForHash();
//        stringRedisTemplate.opsForList();
//        stringRedisTemplate.opsForSet();
//        stringRedisTemplate.opsForZSet();
        stringRedisTemplate.opsForValue().append("my key", "my value");
    }

    @Test
    void getTest() {
        String result = stringRedisTemplate.opsForValue().get("my key");
        System.out.println(result);
    }

    @Test
    void setObjTest() {
        // stored serialized object
        redisTemplate.opsForValue().set("my obj json1", new Ticket( 999, "my movie name"));
    }
    @Test
    void testMail() throws MessagingException {
        // create a message
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("spring boot mail test"); // set subject
        simpleMailMessage.setText("text of spring boot mail"); // set content
        simpleMailMessage.setTo("wangbaozhong9@gmail.com"); // send to which account
        simpleMailMessage.setFrom("zhaoyuehang66@gmail.com"); // message from which account

        javaMailSender.send(simpleMailMessage);

        // create mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("spring boot mail test"); // set subject
        helper.setText("text of spring boot mail"); // set content
        helper.setTo("wangbaozhong9@gmail.com"); // send to which account
        helper.setFrom("zhaoyuehang66@gmail.com"); // message from which account
        helper.addAttachment("a.jpg", new File("/Users/yuehangzhao/Downloads/picture/53874.jpg"));

        javaMailSender.send(mimeMessage);
    }
}
