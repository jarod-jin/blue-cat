package cn.jarod.bluecat.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;

/**
 * @author jarod.jin 2019/9/3
 */
@Slf4j
@Configuration
public class MultipartConfig {

    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        log.info("file tmp location:{}",location);
        File tmpFile = new File(location);
        boolean dirExists = tmpFile.exists();
        if (dirExists) {
            dirExists = tmpFile.mkdirs();
        }
        if (dirExists){
            factory.setLocation(location);
        }
        return factory.createMultipartConfig();
    }
}
