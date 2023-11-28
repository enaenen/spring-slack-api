package com.boilerplate.apirequest.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SlackBotProperties {

	@Value("${slack.token.singing_secret}")
	private String singingSecret;

	@Value("$.slack.token.bot-token}")
	private String botToken;

	@Value("${slack.token.app-token}")
	private String appToken;

//	@Value("${slack.channel.}")
//	private String channelId;


}
