package ua.com.todolisttesttask.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;
import ua.com.todolisttesttask.exception.InvalidJwtAuthenticationException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class JwtTokenProviderTest {

    private static final String SECRET_KEY = "secret";
    private static final long VALIDITY_IN_MILLISECONDS = 3600000L;
    private static final String TOKEN_PREFIX = "eyJhbGciOiJIUzI1NiJ9";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String INVALID_TOKEN = "invalidToken";

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    private String token;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtTokenProvider,
                "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(jwtTokenProvider,
                "validityInMilliseconds", VALIDITY_IN_MILLISECONDS);
        jwtTokenProvider.init();
    }

    @Test
    public void createToken() {
        Long userId = 1L;
        token = jwtTokenProvider.createToken(userId);

        assertNotNull(token);
        assertTrue(token.startsWith(TOKEN_PREFIX));
    }

    @Test
    public void getAuthentication() {
        token = jwtTokenProvider.createToken(1L);
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername("1")).thenReturn(userDetails);

        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        assertNotNull(authentication);
    }

    @Test
    public void resolveToken() {
        when(httpServletRequest.getHeader("Authorization"))
                .thenReturn(BEARER_PREFIX + "testToken");

        String resolvedToken = jwtTokenProvider.resolveToken(httpServletRequest);

        assertEquals("testToken", resolvedToken);
    }

    @Test
    public void validateToken() {
        token = jwtTokenProvider.createToken(1L);

        assertTrue(jwtTokenProvider.validateToken(token));
        assertThrows(InvalidJwtAuthenticationException.class,
                () -> jwtTokenProvider.validateToken(INVALID_TOKEN));
    }
}
