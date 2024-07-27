package xyz.wavit.global.property;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "fcm.certification")
public class FcmProperty {

    private String type;
    private String project_id;
    private String private_key_id;
    private String private_key;
    private String client_email;
    private String client_id;
    private String auth_uri;
    private String token_uri;
    private String auth_provider_x509_cert_url;
    private String client_x509_cert_url;
    private String universe_domain;

    @Override
    public String toString() {
        return "FcmProperty{" +
                "type='" + type + '\'' +
                ", project_id='" + project_id + '\'' +
                ", private_key_id='" + private_key_id + '\'' +
                ", private_key='" + private_key + '\'' +
                ", client_email='" + client_email + '\'' +
                ", client_id='" + client_id + '\'' +
                ", auth_uri='" + auth_uri + '\'' +
                ", token_uri='" + token_uri + '\'' +
                ", auth_provider_x509_cert_url='" + auth_provider_x509_cert_url + '\'' +
                ", client_x509_cert_url='" + client_x509_cert_url + '\'' +
                ", universe_domain='" + universe_domain + '\'' +
                '}';
    }

}