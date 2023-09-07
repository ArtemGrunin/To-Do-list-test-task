package ua.com.todolisttesttask.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.security.AuthenticationService;
import ua.com.todolisttesttask.security.jwt.JwtTokenProvider;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockitoSettings
class AuthenticationControllerTest {

    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "someToken";

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void jwtTokenOnSuccessfulLogin() throws Exception {
        User user = new User();
        user.setId(1L);

        when(authenticationService.login(EMAIL, PASSWORD)).thenReturn(user);
        when(jwtTokenProvider.createToken(user.getId())).thenReturn(TOKEN);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + EMAIL + "\",\"password\":\"" + PASSWORD + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(TOKEN));
    }
}
