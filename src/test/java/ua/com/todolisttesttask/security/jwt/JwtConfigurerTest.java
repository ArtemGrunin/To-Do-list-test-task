package ua.com.todolisttesttask.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


class JwtConfigurerTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpSecurity http;

    private JwtConfigurer jwtConfigurer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtConfigurer = new JwtConfigurer(jwtTokenProvider);
    }

    @Test
    void configure() throws Exception {
        jwtConfigurer.configure(http);
        verify(http).addFilterBefore(any(JwtTokenFilter.class),
                eq(UsernamePasswordAuthenticationFilter.class));
    }
}
