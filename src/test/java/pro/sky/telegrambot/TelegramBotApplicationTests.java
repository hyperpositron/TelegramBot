package pro.sky.telegrambot;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.Task;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.TaskRepository;
import pro.sky.telegrambot.scheduler.Scheduler;

import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.shortThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TelegramBotApplicationTests {

//	@MockBean
//	TaskRepository repository;
//
//	@SpyBean
//	Scheduler scheduler;
//
//	@InjectMocks
//	TelegramBotUpdatesListener listener;

	@Test
	void contextLoads() {
	}

//	Не совсем понятно, что тестировать, учитывая, что вся работа выполнятеся внешними сервисами
//	@Test
//	void testCheckTasks() {
//		LocalDateTime time = LocalDateTime.now();
//		when(repository.findAll()).thenReturn(List.of(
//				new NotificationTask(1L, 1L, "Do your work", LocalDateTime.now())
//		));
//		assertEquals();
//	}

}
