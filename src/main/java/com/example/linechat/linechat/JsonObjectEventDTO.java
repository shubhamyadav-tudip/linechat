package com.example.linechat.linechat;

import java.util.List;

public class JsonObjectEventDTO {

	private String type;
	
	private String timestamp;
	
	private String replyToken;
	
	private String mode;
	
	private List<JsonObjectMessageDTO> jsonObjectMessageDTO;
	
	private List<JsonObjectSourceDTO> jsonObjectSourceDTO;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getReplyToken() {
		return replyToken;
	}

	public void setReplyToken(String replyToken) {
		this.replyToken = replyToken;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<JsonObjectMessageDTO> getJsonObjectMessageDTO() {
		return jsonObjectMessageDTO;
	}

	public void setJsonObjectMessageDTO(List<JsonObjectMessageDTO> jsonObjectMessageDTO) {
		this.jsonObjectMessageDTO = jsonObjectMessageDTO;
	}

	public List<JsonObjectSourceDTO> getJsonObjectSourceDTO() {
		return jsonObjectSourceDTO;
	}

	public void setJsonObjectSourceDTO(List<JsonObjectSourceDTO> jsonObjectSourceDTO) {
		this.jsonObjectSourceDTO = jsonObjectSourceDTO;
	}	


}
