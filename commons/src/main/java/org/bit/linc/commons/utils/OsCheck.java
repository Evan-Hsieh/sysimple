package org.bit.linc.commons.utils;

public class OsCheck {
	public static enum OSType{
		Windows,Linux
	}
	public static OSType getOperatingSystemType(){
		if("Linux".equals(System.getProperty("os.name"))){
			return OSType.Linux;
		}else{
			return OSType.Windows;
		}
	}

}
