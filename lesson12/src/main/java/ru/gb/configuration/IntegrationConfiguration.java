package ru.gb.configuration;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.springframework.integration.file.support.FileExistsMode.APPEND;

@Configuration
public class IntegrationConfiguration {

    @Bean
    public MessageChannel textInputChannel(){
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriterChannel(){
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "textInputChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> stringGenericTransformer(){
        var date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return text -> "%s: %s".formatted(date, text);
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler messageHandler(){
        FileWritingMessageHandler fileWritingMessageHandler =
                new FileWritingMessageHandler(
                        new File("""
                        C:\\Development\\Repository\\GeekBrains\\TECH SPECIALIZATION\\JAVA WEB DEVELOPMENT\\java_spring\\spring-framework\\lesson12\\src\\main\\resources\\FilesArchive"""));

        fileWritingMessageHandler.setExpectReply(false);
        fileWritingMessageHandler.setAppendNewLine(true);
        fileWritingMessageHandler.setFileExistsMode(APPEND);

        return fileWritingMessageHandler;
    }
}
