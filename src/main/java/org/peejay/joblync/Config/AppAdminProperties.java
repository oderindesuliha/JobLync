package org.peejay.joblync.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app-admin")
@Component
@Data
public class AppAdminProperties {
    private Admin admin;
    private SubAdmin subadmin;

    @Data
    public static class Admin {
        private String email;
        private String password;
        private String level;
    }

    @Data
    public static class SubAdmin {
        private String email;
        private String password;
        private String level;
    }
}
