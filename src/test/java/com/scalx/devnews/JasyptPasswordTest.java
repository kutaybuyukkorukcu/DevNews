package com.scalx.devnews;

import com.scalx.devnews.helper.JasyptPropertyService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = JasyptPropertyService.class)
public class JasyptPasswordTest {

    @Autowired
    ApplicationContext appCtx;

    @Test
    public void whenDecryptedPasswordNeeded_GetFromService() {
        System.setProperty("jasypt.encryptor.password", "mocksecretkey");

        JasyptPropertyService service = appCtx
                .getBean(JasyptPropertyService.class);

        assertEquals("dbpassword", service.getProperty());

        Environment environment = appCtx.getBean(Environment.class);

        assertEquals(
                "dbpassword",
                service.getPasswordUsingEnvironment(environment));
    }

}
