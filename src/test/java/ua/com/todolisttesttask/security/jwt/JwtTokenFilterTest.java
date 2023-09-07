package ua.com.todolisttesttask.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class JwtTokenFilterTest {

    private static final String VALID_TOKEN = "validToken";
    private static final String INVALID_TOKEN = "invalidToken";

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void doFilter_ValidToken() throws IOException, ServletException {
        when(jwtTokenProvider.resolveToken(httpServletRequest)).thenReturn(VALID_TOKEN);
        when(jwtTokenProvider.validateToken(VALID_TOKEN)).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(VALID_TOKEN)).thenReturn(authentication);

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Authentication authInContext = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(authentication, authInContext);
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void doFilter_InvalidToken() throws IOException, ServletException {
        when(jwtTokenProvider.resolveToken(httpServletRequest)).thenReturn(INVALID_TOKEN);
        when(jwtTokenProvider.validateToken(INVALID_TOKEN)).thenReturn(false);

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void doFilter_NoToken() throws IOException, ServletException {
        when(jwtTokenProvider.resolveToken(httpServletRequest)).thenReturn(null);

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }
}
