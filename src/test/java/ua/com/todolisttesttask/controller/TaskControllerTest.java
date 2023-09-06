package ua.com.todolisttesttask.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.todolisttesttask.dto.request.TaskRequestDto;
import ua.com.todolisttesttask.dto.response.TaskResponseDto;
import ua.com.todolisttesttask.model.Task;
import ua.com.todolisttesttask.security.jwt.JwtTokenProvider;
import ua.com.todolisttesttask.service.TaskService;
import ua.com.todolisttesttask.service.mapper.impl.TaskMapper;
import ua.com.todolisttesttask.util.SortService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;
    @Mock
    private TaskService taskService;
    @Mock
    private SortService sortService;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private HttpServletRequest request;
    private MockMvc mockMvc;
    private final Long userId = 1L;
    private final Task mockTask = new Task();
    private final TaskResponseDto mockResponse = new TaskResponseDto();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        when(jwtTokenProvider.resolveToken(request)).thenReturn("mockToken");
        when(jwtTokenProvider.getUserId("mockToken")).thenReturn(userId);
        when(taskMapper.mapToDto(mockTask)).thenReturn(mockResponse);
    }

    @Test
    public void testCreate() throws Exception {
        TaskRequestDto mockTaskRequest = new TaskRequestDto();

        when(taskMapper.mapToModel(mockTaskRequest, userId)).thenReturn(mockTask);
        when(taskService.add(mockTask)).thenReturn(mockTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockTaskRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAll() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Task> tasks = List.of(mockTask);
        when(taskService.getAll(userId, pageRequest)).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllWithDifferentAmounts() throws Exception {
        List<Task> tasks = List.of(mockTask);
        when(taskService.getAll(anyLong(), any())).thenReturn(tasks);

        mockMvc.perform(get("/tasks")
                        .param("amount", "10"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tasks")
                        .param("amount", "50"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllWithDifferentPages() throws Exception {
        List<Task> tasks = List.of(mockTask);
        when(taskService.getAll(anyLong(), any())).thenReturn(tasks);

        mockMvc.perform(get("/tasks")
                        .param("page", "1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tasks")
                        .param("page", "5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllWithDifferentSortBy() throws Exception {
        List<Task> tasks = List.of(mockTask);
        when(taskService.getAll(anyLong(), any())).thenReturn(tasks);

        mockMvc.perform(get("/tasks")
                        .param("sortBy", "title"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tasks")
                        .param("sortBy", "creationDate"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTask() throws Exception {
        Long taskId = 1L;

        when(taskService.get(taskId, userId)).thenReturn(mockTask);

        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {
        Long taskId = 1L;
        TaskRequestDto requestDto = new TaskRequestDto();
        Task updatedMockTask = new Task();
        TaskResponseDto updatedResponse = new TaskResponseDto();

        when(taskMapper.mapToModel(requestDto)).thenReturn(mockTask);
        when(taskService.update(mockTask)).thenReturn(updatedMockTask);
        when(taskMapper.mapToDto(updatedMockTask)).thenReturn(updatedResponse);

        mockMvc.perform(put("/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTask() throws Exception {
        Long taskId = 1L;

        doNothing().when(taskService).delete(taskId, userId);

        mockMvc.perform(delete("/tasks/" + taskId))
                .andExpect(status().isNoContent());
    }
}
