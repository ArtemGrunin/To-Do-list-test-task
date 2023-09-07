package ua.com.todolisttesttask.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.LENIENT)
class JwtConfigurerTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpSecurity http;

    @InjectMocks
    private JwtConfigurer jwtConfigurer;

    @BeforeEach
    void setUp() {
        jwtConfigurer = new JwtConfigurer(jwtTokenProvider);
    }

    @Test
    void shouldConfigureWithJwtTokenFilter() throws Exception {
        jwtConfigurer.configure(http);
        verify(http).addFilterBefore(any(JwtTokenFilter.class),
                eq(UsernamePasswordAuthenticationFilter.class));
    }
}
