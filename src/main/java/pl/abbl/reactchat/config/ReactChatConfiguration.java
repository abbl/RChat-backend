package pl.abbl.reactchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("pl.abbl.reactchat")

public class ReactChatConfiguration {
}
