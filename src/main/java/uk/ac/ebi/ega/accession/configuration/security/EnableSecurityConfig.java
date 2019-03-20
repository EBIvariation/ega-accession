/*
 *
 * Copyright 2019 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package uk.ac.ebi.ega.accession.configuration.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import uk.ac.ebi.ega.accession.user.AccessioningUserRepository;

@ConditionalOnProperty(value = "security.enabled", havingValue = "true")
@Configuration
@EnableResourceServer
public class EnableSecurityConfig extends ResourceServerConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST).hasAnyRole("EDITOR", "ADMIN")
                .antMatchers(HttpMethod.PUT).hasAnyRole("EDITOR", "ADMIN")
                .antMatchers(HttpMethod.PATCH).hasAnyRole("EDITOR", "ADMIN")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("EDITOR", "ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    public AuthoritiesExtractor authoritiesExtractor(AccessioningUserRepository accessioningUserRepository) {
        return new CustomAuthoritiesExtractor(accessioningUserRepository);
    }
}