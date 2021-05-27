package com.example.linechat.linechat;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ai.api.model.AIResponse;

@Controller
public class CallbackController implements ServletContextAware {

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
	}
	private String DialogflowToken="a07bc2127c61497fad437dd8c197f951", LineID="1655800970", LineSecret = "43847b79f42eac311136af84bd5aadb4", LineToken="gQNvWB8oXbC3FmRpMk4bYxB5tXiXHbxax/Q4hdq7UtHZHD76WkgVQyS8QA9PNYykBd+EwUGJzwDFdyneG0pWkYOgYaxRwh+qr1R2zlv/nx4gRGVeerf3HzsanFn5wJirhGFxJfYjTiVO3SyNC7cnngdB04t89/1O/w1cDnyilFU=";
	private static Logger logger = LoggerFactory.getLogger(CallbackController.class);
	
	@RequestMapping(value="/callback",method=RequestMethod.POST)
	public ResponseEntity<LineClientMsg> handleLineCallBack(HttpServletRequest request, HttpServletResponse response) {
		String replyToken=null;
		LineMessageUtils lineUtils=new LineMessageUtils();
//		System.out.println("test:---"+lineUtils);
		List<LineClientMsg> LineResponseParams=null;
		try {
			request.setCharacterEncoding("UTF-8");
			LineResponseParams=(List<LineClientMsg>) lineUtils.FilterLineClient2ServerMsg(request, LineSecret);
			logger.info("Line Message from Client: "+LineResponseParams);
			System.out.println("Line Message from Client: "+LineResponseParams);
			Iterator<LineClientMsg> ClientMsgIterator=LineResponseParams.iterator();
			AiUtils aiUtil=new AiUtils(this.DialogflowToken);
			while(ClientMsgIterator.hasNext()) {
				LineClientMsg clientMsg=ClientMsgIterator.next();
				replyToken=clientMsg.getReplyToken();
				System.out.println("Message from Client: "+clientMsg.getMsgsFromClient());
//				AIResponse AiAgentResponse=aiUtil.filterAiResponseMsg(clientMsg.getMsgsFromClient());
//				String[] systemName=AiAgentResponse.getResult().getMetadata().getIntentName().split("-");
//				logger.info("Ai Intent : "+AiAgentResponse.getIntentName()+" System Name: "+systemName[0]);
//				System.out.println("Ai Intent : "+AiAgentResponse.getIntentName()+" System Name: "+systemName[0]);
				IAiService aiService=null;
				JsonArray MsgArray=null;
				JsonObject Msg2LineServer=null;
//				switch (systemName[0]) {
//					case "SFC":
//						aiService=new IAiService();
//						break;
//					case "RealTime":
//						aiService=new RealTimeAi();
//						break;
//					case "ERP":
//						aiService=new ERPAi();
//						break;
//					case "Agentflow":
//						aiService=new AgentflowAi();
//						break;
//					case "Notes":
//						aiService=new NotesAi();
//						break;
//					case "BI":
//						aiService=new BIAi();
//						break;
//					case "PLM":
//						aiService=new PLMAi();
////						break;
//					default:
//						break;
//				}
				
				if(aiService !=null) {
//					aiService.setParameters(AiAgentResponse.getParameters(),AiAgentResponse.getIntentName());
//					MsgArray=lineUtils.GenerateMsgArray(aiService.SendSingleResponseToClient(systemName[1]));
					logger.info("Message to line client: "+MsgArray);
				}
				else {
					JsonObject noAiFound=new JsonObject();
					MsgArray=new JsonArray();
					noAiFound.addProperty("type", "text");
//					if(systemName[0].equals("SAY_HELLO")) {
						noAiFound.addProperty("text", "Hello dasdadas");
//					}
//					else {
//						noAiFound.addProperty("text", "asddasd");
//					}
					MsgArray.add(noAiFound);
				}
				Msg2LineServer=lineUtils.GenerateLineReplyClientMsg(replyToken, MsgArray);
				logger.info("Message to Line Server: "+Msg2LineServer);
				System.out.println("Message to Line Server: "+Msg2LineServer+" , and reply token: "+replyToken);
				lineUtils.ReplyLineMsg2Client(replyToken, LineToken, Msg2LineServer);
				System.out.println("line:"+lineUtils);
			}
		}
		catch(Exception ex) {
			logger.error("error",ex);
			System.out.println(ex);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

//	@PostMapping(value="/callback/bean")
//	private boolean handleLineCallBackRequest(HttpServletRequest request, HttpServletResponse response, JsonObject Message2Client) {
//		String replyToken=null;
//		LineMessageUtils lineUtils=new LineMessageUtils();
////		List<LineClientMsg> LineResponseParams=null;
////		Iterator<LineClientMsg> ClientMsgIterator=LineResponseParams.iterator();
////		System.out.println("test:---"+ClientMsgIterator);
//
//		try {
//			
////			while(ClientMsgIterator.hasNext()) {
////				LineClientMsg clientMsg=ClientMsgIterator.next();
////				replyToken=clientMsg.getReplyToken();
//			request.setCharacterEncoding("UTF-8");
//			lineUtils.ReplyLineMsg2Client("c86c1a99a5fa400091896fde4af76fab", LineToken, Message2Client);
////			logger.info("Line Message from Client: "+LineResponseParams);
////			System.out.println("Reply "+LineResponseParams);
//			}
////		}
//		catch(Exception ex) {
//			logger.error("error",ex);
//			System.out.println(ex);
//		}
//	
//		
//		return true;
//
//	}
	@Override
	public String toString() {
		return "CallbackController [DialogflowToken=" + DialogflowToken + ", LineID=" + LineID + ", LineSecret="
				+ LineSecret + ", LineToken=" + LineToken + "]";
	}

	public String getDialogflowToken() {
		return DialogflowToken;
	}

	public void setDialogflowToken(String dialogflowToken) {
		DialogflowToken = dialogflowToken;
	}

	public String getLineID() {
		return LineID;
	}

	public void setLineID(String lineID) {
		LineID = lineID;
	}

	public String getLineSecret() {
		return LineSecret;
	}

	public void setLineSecret(String lineSecret) {
		LineSecret = lineSecret;
	}

	public String getLineToken() {
		return LineToken;
	}

	public void setLineToken(String lineToken) {
		LineToken = lineToken;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CallbackController.logger = logger;
	}

}
