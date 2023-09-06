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
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JwtTokenFilterTest {

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
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilter_ValidToken() throws IOException, ServletException {
        String token = "validToken";

        when(jwtTokenProvider.resolveToken(httpServletRequest)).thenReturn(token);
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(token)).thenReturn(authentication);

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Authentication authInContext = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(authentication, authInContext);
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDoFilter_InvalidToken() throws IOException, ServletException {
        String token = "invalidToken";

        when(jwtTokenProvider.resolveToken(httpServletRequest)).thenReturn(token);
        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDoFilter_NoToken() throws IOException, ServletException {
        when(jwtTokenProvider.resolveToken(httpServletRequest)).thenReturn(null);

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }
}
