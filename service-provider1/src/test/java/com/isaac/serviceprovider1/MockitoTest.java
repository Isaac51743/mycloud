package com.isaac.serviceprovider1;

import com.isaac.serviceprovider1.controller.TicketController;
import com.isaac.serviceprovider1.domain.User;
import com.isaac.serviceprovider1.repository.UserRepository;
import com.isaac.serviceprovider1.service.SchedulerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MockitoTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    TicketController ticketController;

    @Mock
    SchedulerService schedulerService;

    @Test
    void test1() {
        List<User> userList = new ArrayList<>();
        User user1 = new User("m@m", "mfirst", "mlast", "pass", "address", "number", "active");
        User user2 = new User("m1@m1", "mfirst", "mlast", "pass", "address", "number", "active");
        User user3 = new User("m2@m2", "mfirst", "mlast", "pass", "address", "number", "active");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        Mockito.when(userRepository.getUserSpecifyPassword("testPass")).thenReturn(userList);
        Mockito.doNothing().when(schedulerService).testHello("anyString()");
        Assertions.assertEquals(userList, ticketController.getUserWithPassword("testPass"), "result of getUserWithPassword() not matched");
    }

}
