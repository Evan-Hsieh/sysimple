package org.bit.linc.commons.cmdline;

public class CmdType {
	/**
	 * run in DOS environment
	 */
	public final static String DOS="cmd";
	/**
	 * run in linux environment
	 */
	public final static String Linux="/bash/bin";
	
	private static String currentType;


	/**
	 * get which CmdType to use
	 * @return
	 */
	public static String getCurrentType() {
		if(null==currentType||currentType.equals("")){
			if("Linux".equals(System.getProperty("os.name"))){
				currentType=Linux;
			}else{
				currentType=DOS;
			}
		}
		return currentType;
	}
	
}