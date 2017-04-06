package org.bit.linc.plugins.scripts;

import java.util.ArrayList;
import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.ExResult;
import org.bit.linc.commons.utils.FileUtil;
import org.bit.linc.plugins.plugins.PluginsUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ScriptsUtil {

	/**
	 * 获取plugin下的脚本列表
	 * @param pluginName plugin的文件夹名称
	 * @return
	 * @throws SysimpleException
	 */
	public static ArrayList<Script> getScriptList(String pluginName) throws SysimpleException{
		String scriptPath=PluginsUtil.getPluginsDir()+"/"+pluginName+"/scripts";
		ExResult result=FileUtil.ReadFileByLine(scriptPath+"/scripts-info.json");
		if(result.code==0){
			String content=result.message;
			Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			ArrayList<Script> scriptsTmp=gson.fromJson(content.trim(), new TypeToken<ArrayList<Script>>(){}.getType());
			return scriptsTmp;
		}
		return null;
	}
}
