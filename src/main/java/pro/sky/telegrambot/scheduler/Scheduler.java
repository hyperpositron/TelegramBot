package pro.sky.telegrambot.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Scheduler {
    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private TaskRepository repository;

    @Autowired
    TelegramBotUpdatesListener listener;

    @Scheduled(cron = "0 0/1 * * * *")
    public void runTask() {
//        return repository.findAll().stream()
//                .filter(t -> t.getTime().equals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)))
//                .collect(Collectors.toList());
        logger.info("scheduling method is called");
        List<NotificationTask> result = repository.findTaskByTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        logger.info("list of tasks retrieved: {}", result);
        result.forEach(listener::sendTask);
    }
}
