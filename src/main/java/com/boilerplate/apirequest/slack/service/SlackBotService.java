package com.boilerplate.apirequest.slack.service;

import com.boilerplate.apirequest.config.SlackBotProperties;
import com.boilerplate.apirequest.slack.dto.SlackResponse;
import com.boilerplate.apirequest.slack.dto.SlackUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.ClassFormatException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackBotService {

	private final SlackBotProperties slackBotConfig;
	private final ObjectMapper objectMapper;


	private final String AUTHORIZATION = "Authorization";
	private final String CONTENT_TYPE = "CONTENT_TYPE";
	private final String APPLICATION_FORM = "application/x-www-form-urlencoded";
	private final String BEARER = "Bearer ";

	private final String SLACK_ERROR_MESSAGE = "Slack API Response Error";
	private final String ERROR_RESPONSE = "error";
	private final String INTRA42_EMAIL_DOMAIN = "@student.42seoul.kr";


	public SlackUserInfo requestSlackUserInfoByIntraNickname(String intraId) {
		return requestSlackUserInfo(intraId + INTRA42_EMAIL_DOMAIN);
	}

	private SlackUserInfo requestSlackUserInfo(String emailAddress) {
		String url = "https://slack.com/api/users.lookupByEmail?email=" + emailAddress;
		HttpHeaders headers = new HttpHeaders();
		String slackToken = BEARER + slackBotConfig.getAppToken();
		headers.add(AUTHORIZATION, slackToken);
		headers.add(CONTENT_TYPE, APPLICATION_FORM);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(
				url,
				HttpMethod.GET,
				request,
				String.class
		);

		try {
			SlackResponse slackResponse = objectMapper.readValue(response.getBody(),
					SlackResponse.class);
			if (slackResponse.getOk().equals(ERROR_RESPONSE)) {
				log.error("Error occurred {}", slackResponse);
				throw new IllegalArgumentException(SLACK_ERROR_MESSAGE);
			}
			return slackResponse.getSlackUserInfo();
		} catch (IOException e) {
			log.error("Error occurred {}", e.getMessage());
			throw new ClassFormatException(SLACK_ERROR_MESSAGE);
		}
	}

	public void sendSlackMessage(String intraId, String message) {
		SlackUserInfo slackUserInfo;
		if (intraId.contains("@"))
			slackUserInfo = requestSlackUserInfo(intraId);
		else
			slackUserInfo = requestSlackUserInfoByIntraNickname(intraId);
		String slackUserIdentifyId = slackUserInfo.getId();
		sendMessageToSlack(message, slackUserIdentifyId);
	}

	private void sendMessageToSlack(String message, String channelId) {
		log.info("sendMessageToSlack: message = {} channelID = {}", message, channelId);
		try {
			MethodsClient methods = Slack.getInstance().methods(slackBotConfig.getAppToken());

			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
					.channel(channelId) // DM & channel
					.text(message)
					.build();
			methods.chatPostMessage(request);
		} catch (SlackApiException | IOException e) {
			log.error("[Slack error] {} ", e.getMessage());
		}
	}

	public void sendSlackChannel(String channel, String message) {
		sendMessageToSlack(message, channel);
	}
}
