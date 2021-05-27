package com.example.linechat.linechat;

import java.util.HashMap;

import com.google.gson.JsonElement;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class AiUtils {
	private String DialogFlowToken;
	
	public AiUtils(String AiToken) {
		this.DialogFlowToken=AiToken;
	}
	
	public AIResponse filterAiResponseMsg(String msg2AiAgent) {
		AIConfiguration config=null;
		AIDataService dataService=null;
		AIRequest request=null;
		AIResponse response=null;
		AIResponse responseContent=null;
		try {
			config=new AIConfiguration(DialogFlowToken);
			dataService=new AIDataService(config);
			request=new AIRequest(msg2AiAgent);
			request.setLanguage("zh-TW");
			response=dataService.request(request);
			
			if(response.getStatus().getCode()==200) {
				String intent=response.getResult().getMetadata().getIntentName();
				HashMap<String,JsonElement> parameters=response.getResult().getParameters();
				responseContent=new AIResponse();
//				responseContent.setIntentName(intent);
//				responseContent.setParameters(parameters);
				System.out.println("AI Response Content: "+responseContent);
			}
			else {
				System.err.println(response.getStatus().getErrorDetails());
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return responseContent;
	}
}

