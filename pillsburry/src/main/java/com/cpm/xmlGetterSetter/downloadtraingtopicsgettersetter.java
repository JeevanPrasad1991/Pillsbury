package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class downloadtraingtopicsgettersetter {
	
	ArrayList<String> topic_cd = new ArrayList<String>();
	ArrayList<String> topic = new ArrayList<String>();
	
	public ArrayList<String> gettopic_cd() {
		return topic_cd;
	}
	public void settopic_id(String topic_cd) {
		this.topic_cd.add(topic_cd);
	}
	public ArrayList<String> gettopic() {
		return topic;
	}
	public void settopic(String topic) {
		this.topic.add(topic);
	}

}
