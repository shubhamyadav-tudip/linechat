package com.example.linechat.linechat;

import java.util.List;

public class JsonObjectDTO {
	
	private String Destination;
	
	private List<JsonObjectEventDTO> JsonObjectEventDTO;

	public String getDestination() {
		return Destination;
	}

	public void setDestination(String destination) {
		Destination = destination;
	}

	public List<JsonObjectEventDTO> getJsonObjectEventDTO() {
		return JsonObjectEventDTO;
	}

	public void setJsonObjectEventDTO(List<JsonObjectEventDTO> jsonObjectEventDTO) {
		JsonObjectEventDTO = jsonObjectEventDTO;
	}
	

	
	
	
}
