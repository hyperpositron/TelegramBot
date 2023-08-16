package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.NotificationTaskService;
import pro.sky.telegrambot.service.TelegramBotService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.LocalDateTime.parse;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final Pattern PATTERN = Pattern.compile("(\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{2}) ([А-яA-z\\d,\\s!?:@.]+)");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");

    @Autowired
    private TelegramBot telegramBot;
    private final NotificationTaskService notificationTaskService;
    private final TelegramBotService telegramBotService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      NotificationTaskService notificationTaskService,
                                      TelegramBotService telegramBotService) {
        this.telegramBot = telegramBot;
        this.notificationTaskService = notificationTaskService;
        this.telegramBotService = telegramBotService;
    }

        @PostConstruct
        public void init () {
            telegramBot.setUpdatesListener(this);
        }

        @Override
        public int process (List<Update> updates) {
            updates.forEach(update -> {
                LOG.info("Processing update: {}", update);
                Long chatId = update.message().chat().id();
                Message message = update.message();
                String text = message.text();
                LocalDateTime dateTime;
                if (update.message() != null && text != null) {
                    Matcher matcher = PATTERN.matcher(text);
                    if (text.equals("/start")) {
                        telegramBotService.sendMessage(chatId,
                                "Для планирования задачи отправте её в формате:\\n*01.01.2022 20:00 Сделать домашнюю работу*",
                                ParseMode.Markdown
                        );
                    } else if (matcher.matches() && (dateTime = parse(matcher.group(1))) != null) {

                        notificationTaskService.save(chatId, matcher.group(2), dateTime);
                        telegramBotService.sendMessage(chatId, "Ваша задача успешно запланирована!");
                    } else {
                        telegramBotService.sendMessage(chatId, "Формат сообщения неверный!");
                    }
                } else {
                    telegramBotService.sendMessage(
                            chatId,
                            "Отправте команду /start или сообщение для планирования задачи!"
                    );
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }

                @Nullable
                private LocalDateTime parse (String dateTime){
                    try {
                        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
                    } catch (DateTimeParseException e) {
                        return null;
                    }
                }
            }


