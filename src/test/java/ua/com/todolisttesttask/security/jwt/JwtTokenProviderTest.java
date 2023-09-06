package ua.com.todolisttesttask.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;
import ua.com.todolisttesttask.exception.InvalidJwtAuthenticationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtTokenProviderTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest httpServletRequest;

    private JwtTokenProvider jwtTokenProvider;

    private String token;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtTokenProvider = new JwtTokenProvider(userDetailsService);
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "secret");
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", 3600000L);
        jwtTokenProvider.init();
    }

    @Test
    public void testCreateToken() {
        Long userId = 1L;
        token = jwtTokenProvider.createToken(userId);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9"));
    }

    @Test
    public void testGetAuthentication() {
        token = jwtTokenProvider.createToken(1L);
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername("1")).thenReturn(userDetails);

        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        assertNotNull(authentication);
    }

    @Test
    public void testResolveToken() {
        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer testToken");

        String resolvedToken = jwtTokenProvider.resolveToken(httpServletRequest);

        assertEquals("testToken", resolvedToken);
    }

    @Test
    public void testValidateToken() {
        token = jwtTokenProvider.createToken(1L);

        assertTrue(jwtTokenProvider.validateToken(token));
        assertThrows(InvalidJwtAuthenticationException.class, () -> jwtTokenProvider.validateToken("invalidToken"));
    }
}
