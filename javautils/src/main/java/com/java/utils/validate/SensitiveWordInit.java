package com.java.utils.validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *初始化敏感词库
 */
public class SensitiveWordInit {
	private String ENCODING = "GBK";   
	@SuppressWarnings("rawtypes")
	public HashMap sensitiveWordMap;
	
	public SensitiveWordInit(){
		super();
	}
	
	@SuppressWarnings("rawtypes")
	public Map initKeyWord(){
		try {
			
			Set<String> keyWordSet = readSensitiveWordFile("filepath");
			addSensitiveWordToHashMap(keyWordSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
		sensitiveWordMap = new HashMap(keyWordSet.size());    
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();    
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       
				Object wordMap = nowMap.get(keyChar);       
				
				if(wordMap != null){       
					nowMap = (Map) wordMap;
				}
				else{     
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");     
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");    
				}
			}
		}
	}

	@SuppressWarnings("resource")
	private Set<String> readSensitiveWordFile(String filepath) throws Exception{
		Set<String> set = null;
		
		File file = new File(filepath);    
		InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
		try {
			if(file.isFile() && file.exists()){      
				set = new HashSet<String>();
				BufferedReader bufferedReader = new BufferedReader(read);
				String txt = null;
				while((txt = bufferedReader.readLine()) != null){    
					set.add(txt);
			    }
			}
			else{         
				throw new Exception("文件不存在");
			}
		} catch (Exception e) {
			throw e;
		}finally{
			read.close();     
		}
		return set;
	}
}
