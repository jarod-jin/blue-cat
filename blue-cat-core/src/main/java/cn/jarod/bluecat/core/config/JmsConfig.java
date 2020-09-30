package cn.jarod.bluecat.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import java.util.Locale;
import java.util.TimeZone;

public abstract class JmsConfig {

    /**
     * 格式 Format
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        StdDateFormat stdDateFormat = new StdDateFormat();
        stdDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        stdDateFormat = stdDateFormat.withLocale(Locale.ENGLISH);
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY).dateFormat(stdDateFormat);
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        mappingJackson2MessageConverter.setObjectMapper(builder.build());
        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
        mappingJackson2MessageConverter.setTypeIdPropertyName("documentType");
        return mappingJackson2MessageConverter;
    }
}
