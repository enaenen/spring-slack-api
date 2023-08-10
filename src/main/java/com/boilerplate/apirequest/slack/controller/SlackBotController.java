package com.boilerplate.apirequest.slack.controller;

import com.boilerplate.apirequest.slack.service.SlackBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slack")
@RequiredArgsConstructor
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

}
