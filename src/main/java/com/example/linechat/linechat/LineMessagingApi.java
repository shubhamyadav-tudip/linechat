package com.example.linechat.linechat;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

@RestController
@RequestMapping("/PUSH_MESSAGE")
public class LineMessagingApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(LineMessagingApi.class);
	 
	   /*This annotatino is for make this method call Async*/
	    @Async("asyncExecutor")
	    @CrossOrigin
	    @RequestMapping(value = "/TEST_LINE_API")
	    public void replyToLineMessaginAPI(@RequestBody LineMessageUtils lineRequest) throws InterruptedException {
	 
	        /*Get the channel access token*/
	        String authorization = "djKelBrDRcZ09w0DIlWUN5W4tpTKTN+pcWs0ayes1QirTH7+/rCSaiJ4rILPSY9sBd+EwUGJzwDFdyneG0pWkYOgYaxRwh+qr1R2zlv/nx5RrX7GOu1q2LziXeEA3ff4/9lRm3GhAziteafG/Smu2wdB04t89/1O/w1cDnyilFU=";
	        String replyMessage = "Welcome to Line Messaging API";
	        TextMessage textMessage = null;
	 
	        final LineMessagingClient client = LineMessagingClient.builder(authorization).build();
	 
	        textMessage = new TextMessage(replyMessage);
	 
	        final ReplyMessage replyMessage1 = new ReplyMessage(lineServerToAsyncApiRequest.getReplyToken(), textMessage);
	 
	        final BotApiResponse botApiResponse;
	        
	        System.out.println(replyMessage1);
	        try {
	            botApiResponse = client.replyMessage(replyMessage1).get();
	 
	        } catch (InterruptedException | ExecutionException exception) {
	            LOGGER.error("Unable to hit reply API for Line server : " + exception);
	            exception.printStackTrace();
	            return;
	        }
	 
	    }
}
