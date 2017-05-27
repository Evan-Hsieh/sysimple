package org.bit.linc.commons.utils;

import java.util.UUID;

public class UniqueID {
	public static UUID getUUID(){
		return UUID.randomUUID();
	}
	
	public static String getUUIDAsStr(){
		return getUUID().toString();
	}
	
	public static String getUUIDAsStr(String newSysbol){
		return getUUIDAsStr().replace("-", newSysbol);
	}
}
