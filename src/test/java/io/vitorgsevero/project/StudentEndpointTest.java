/*package io.vitorgsevero.project;

import io.vitorgsevero.project.model.Student;
import io.vitorgsevero.project.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.DELETE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @MockBean
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class Config{
        @Bean
        public RestTemplateBuilder restTemplateBuilder(){
            return new RestTemplateBuilder().basicAuthorization("vitorgsevero6", "vitorgsevero");
        }
    }

    @Before
    public void setup(){
        Student student = new Student(1L, "Vitor", "vitorgsevero@gmail.com");
        BDDMockito.when(studentRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
    }

    @Test
    public void listStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode(){
        restTemplate = restTemplate.withBasicAuth("1", "1");
        System.out.println(port);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/", String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void getStudentsByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode(){
        restTemplate = restTemplate.withBasicAuth("1", "1");
        System.out.println(port);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/1", String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200(){
        List<Student> students = asList(new Student(1L, "Vitor", "vitorgsevero@gmail.com"),
                new Student(2L, "Vitoor", "vitor@mail.com"));
        BDDMockito.when(studentRepository.findAll()).thenReturn(students);
        System.out.println(port);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/", String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getStudentsByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200(){
        setup();
        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, 1);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getStudentsByIdWhenUsernameAndPasswordAreCorrectAndStudentDoesNotExistShouldReturnStatusCode404(){
        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, -115);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode200(){
        BDDMockito.doNothing().when(studentRepository).deleteById(1L);
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/admin/students/{id}", DELETE, null, String.class, 1L);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @WithMockUser(username = "vs", password = "vs", roles = {"USER", "ADMIN"})
    public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistsShouldReturnStatusCode404() throws Exception {
        BDDMockito.doNothing().when(studentRepository).deleteById(1L);
     //   ResponseEntity<String> exchange = restTemplate.exchange("/v1/admin/students/{id}", DELETE, null, String.class, -1L);
    //    Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/admin/students/{id}", -1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "vs", password = "vs", roles = {"USER"})
    public void deleteWhenUserDoesNotHaveHasRoleAdminShouldReturnStatusCode403() throws Exception {
        BDDMockito.doNothing().when(studentRepository).deleteById(1L);
     //   ResponseEntity<String> exchange = restTemplate.exchange("/v1/admin/students/{id}", DELETE, null, String.class, -1L);
    //    Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/admin/students/{id}", -1L))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    @Test
    public void createWhenNameIsNulShouldReturnStatusCode400BadRequest() throws Exception {
        Student student = new Student(3L, null, "gus@gmail.com");
        BDDMockito.when(studentRepository.save(student)).thenReturn(student);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/v1/admin/students/", student, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
       Assertions.assertThat(responseEntity.getBody()).contains("fieldMessage", "The field name is required");
    }
    @Test
    public void createShouldPersistDataAndReturnStatusCode201() throws Exception {
        Student student = new Student(3L, "Gustavo", "gus@gmail.com");
        BDDMockito.when(studentRepository.save(student)).thenReturn(student);
        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("/v1/admin/students/", student, Student.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        Assertions.assertThat(responseEntity.getBody().getId()).isNotNull();
    }


}
*/
