package wmi.amu.edu.pl.pri.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
public class LdapProviderConfiguration {

    // Provide AD LDAP provider as a bean (LDAP is required for this module)
    @Bean
    public ActiveDirectoryLdapAuthenticationProvider ldapAuthenticationProvider(
            @Value("${pri.ldap.domain}") String domain,
            @Value("${spring.ldap.urls}") String url,
            @Value("${spring.ldap.base}") String base
    ) {
        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(domain, url, base);
        provider.setConvertSubErrorCodesToExceptions(true);
        provider.setUseAuthenticationRequestCredentials(true);
        return provider;
    }

}
