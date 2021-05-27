package com.example.linechat.linechat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LineMessageUtils {
	private static Logger logger = LoggerFactory.getLogger(LineMessageUtils.class);

	public void ReplyLineMsg2Client(String replyToken, String LineAccessToken, JsonObject msg2LineServer) {
		
//		System.out.println(Message2Client.getJsonObjectEventDTO());
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			headers.add("Authorization", "Bearer {" + LineAccessToken + "}");
			HttpEntity<String> entity = new HttpEntity<String>(msg2LineServer.toString(), headers);
			System.out.println("entity: "+entity);
			System.out.println("Token: "+LineAccessToken);
			System.out.println("ObjectJson: "+msg2LineServer);

			// Send Request and Parse Result
			RestTemplate restTemplate = new RestTemplate();
			System.out.println("restTemplate"+restTemplate);
			ResponseEntity<String> lineServerResponse = restTemplate
					.exchange("https://line-messaging1.p.rapidapi.com/message/reply", HttpMethod.POST, entity, String.class);
			System.out.println("Line request: "+lineServerResponse);
			System.out.println(lineServerResponse.getStatusCode());

			if (lineServerResponse.getStatusCode() == HttpStatus.OK) {
				logger.info("Send Message to Line Server is success.");
			} else {
				logger.info("Send Message to Line Server is failed, due to: " + lineServerResponse.getStatusCode()
						+ ",  " + lineServerResponse.getBody());
			}
		} catch (Exception ex) {
			logger.error("ReplyLineMsg2Client failed ", ex);
			ex.printStackTrace();
		}
	}
 
	public JsonObject GenerateLineReplyClientMsg(String LineReplyToken, JsonArray Reply2ClientMsgs) {
		JsonObject Msg2Client = new JsonObject();
		try {
			Msg2Client.addProperty("replyToken", LineReplyToken);
			Msg2Client.add("messages", Reply2ClientMsgs);
		} catch (Exception ex) {
			System.out.println("GenerateLineReplyClientMsg Failed , due to: " + ex.toString());
			ex.printStackTrace();
		}
		return Msg2Client;
	}

	public List<LineClientMsg> FilterLineClient2ServerMsg(HttpServletRequest request, String lineChannelSecret) {
		List<LineClientMsg> LineClientMsgs = null;
		String httpRequestBody = null;
		try {
			LineClientMsgs = new ArrayList<LineClientMsg>();
//			System.out.println("shubham yadav"+LineClientMsgs);
			InputStream inputStream = request.getInputStream();
			httpRequestBody = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
					.collect(Collectors.joining("\n"));
			JsonParser parser = new JsonParser();
			JsonObject lineMsg = (JsonObject) parser.parse(httpRequestBody);
//			System.out.println("request:"+lineMsg);

			JsonArray lineEvents = lineMsg.get("events").getAsJsonArray();
			Iterator<JsonElement> lineEventIterator = lineEvents.iterator();
			while (lineEventIterator.hasNext()) {
				LineClientMsg lineClientMsg = new LineClientMsg();
				JsonElement lineEvent = lineEventIterator.next();
				System.out.println(lineEvent);
				lineClientMsg.setReplyToken(lineEvent.getAsJsonObject().get("replyToken").getAsString());
				String msgType = lineEvent.getAsJsonObject().get("message").getAsJsonObject().get("type").getAsString();
				if (msgType.equals("text")) {
					lineClientMsg.setMsgsFromClient(
							lineEvent.getAsJsonObject().get("message").getAsJsonObject().get("text").getAsString());
				}
				LineClientMsgs.add(lineClientMsg);
			}
			/* Decrypt */
			SecretKeySpec key = new SecretKeySpec(lineChannelSecret.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(key);
			byte[] source = httpRequestBody.getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return LineClientMsgs;
	}

	public JsonArray GenerateMsgArray(Object sendSingleResponseToClient) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LineClientMsg> ReplyLineMsg2Client(HttpServletRequest request, String lineSecret) {
		// TODO Auto-generated method stub
		return null;
	}
}
