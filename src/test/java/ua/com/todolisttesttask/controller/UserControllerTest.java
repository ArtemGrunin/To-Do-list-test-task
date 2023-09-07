package ua.com.todolisttesttask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.todolisttesttask.dto.request.UserRequestDto;
import ua.com.todolisttesttask.dto.response.UserResponseDto;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.UserService;
import ua.com.todolisttesttask.service.mapper.RequestDtoMapper;
import ua.com.todolisttesttask.service.mapper.ResponseDtoMapper;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@MockitoSettings(strictness = Strictness.LENIENT)
public class UserControllerTest {

    private static final String USER_EMAIL = "test@test.com";
    private static final String USER_PASSWORD = "password";

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private RequestDtoMapper<UserRequestDto, User> userRequestMapper;

    @Mock
    private ResponseDtoMapper<UserResponseDto, User> userResponseMapper;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegister() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail(USER_EMAIL);
        requestDto.setPassword(USER_PASSWORD);

        User user = new User();
        user.setId(1L);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setEmail(USER_EMAIL);

        when(userRequestMapper.mapToModel(requestDto)).thenReturn(user);
        when(userService.create(user)).thenReturn(user);
        when(userResponseMapper.mapToDto(user)).thenReturn(responseDto);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is(USER_EMAIL)));
    }
}
