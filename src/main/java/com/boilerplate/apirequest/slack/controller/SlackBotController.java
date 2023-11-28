package com.boilerplate.apirequest.slack.controller;

import com.boilerplate.apirequest.slack.dto.MessageDto;
import com.boilerplate.apirequest.slack.service.SlackBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slack")
@RequiredArgsConstructor
@Log4j2
public class SlackBotController {

	private final SlackBotService slackBotService;
	@GetMapping("/test")
	public String sendDirectMessage() {
		return "OK";
	}

	@GetMapping("/send/{intraId}/{message}")
	public void sendDirectMessage(@PathVariable String intraId, @PathVariable String message) {
		slackBotService.sendSlackMessage(intraId, message);
	}

	@PostMapping("/send/dm")
	public void sendDirectMessage(MessageDto messageDto){
		log.info("sendDirectMessage called : {}",messageDto);
		slackBotService.sendSlackMessage(messageDto.getChannel(), messageDto.getMessage());
	}

	@PostMapping("/send/channel")
	public void sendChannelMessage(MessageDto messageDto){
		slackBotService.sendSlackChannel(messageDto.getChannel(), messageDto.getMessage());
	}
}
