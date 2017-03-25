package org.bit.linc.plugins.plugins;

import java.util.ArrayList;

import org.bit.linc.commons.utils.ExResult;
import org.bit.linc.commons.utils.FileUtil;
import org.bit.linc.plugins.scripts.Script;
import com.google.gson.Gson;

public class Plugin {
	
	
	//下面的变量需要显示到Web中，不能用transient修饰
	private String name;//插件名
	private String intro;
	private String detail;
	private ArrayList<Script> scriptsList;

	
	/**
	 * 
	 * @param name plugin's name
	 */
	public Plugin(String name) {
		this.name = name;
	}
	public Plugin(String name,String intro){
		this.name = name;
		this.intro=intro;
	}
	public Plugin(String name,String intro,String detail){
		this.name = name;
		this.intro=intro;
		this.detail=detail;
	}
	/**
	 * get plugin's detail
	 * @return
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * set plugin's detail
	 * @return
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * get plugin's introduction
	 * @return
	 */
	public String getInfo() {
		return intro;
	}
	/**
	 * set plugin's introduction
	 * @return
	 */
	public void setInfo(String intro) {
		this.intro = intro;
	}
	/**
	 * get scripts.this method can't get scripts of this plugin real-time.if you want get scripts of this plugin really,
	 * you can use ScriptsUtil.getScriptList();
	 * @return
	 */
	public ArrayList<Script> getScriptsList() {
		return scriptsList;
	}
	/**
	 * this method just save scrpits temporary.
	 * @param scripts
	 */
	public void setScriptsList(ArrayList<Script> scriptsList) {
		this.scriptsList = scriptsList;
	}
	/**
	 * get plugin's name
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * create this plugin on file system
	 * @return
	 */
	public ExResult create(){
		ExResult result=new ExResult();
		result=FileUtil.CreateFile(false,PluginsUtil.getPluginsDir()+"/"+name);
		if(result.code==0){
			result=FileUtil.CreateFile(false,PluginsUtil.getPluginsDir()+"/"+name+"/scripts");
			if(result.code==0){
				result=FileUtil.CreateFile(true,PluginsUtil.getPluginsDir()+"/"+name+"/info.json");
				Gson gson=new Gson();
				String jsonString=gson.toJson(this);
				FileUtil.WriteFile(PluginsUtil.getPluginsDir()+"/"+name+"/info.json", jsonString);
				if(result.code==0){
					result=FileUtil.CreateFile(true,PluginsUtil.getPluginsDir()+"/"+name+"/scripts/"+name+"-start.sh");
					return result;
				}
			}
		}else if(result.code!=1){//1：already exist,so can't remove this plugin
			FileUtil.DeleteFile(PluginsUtil.getPluginsDir()+"/"+name);//if not success,clean all that have done
		}
		return result;
	}
	/**
	 * delete this plugin
	 */
	public void delete(){
		FileUtil.DeleteFile(PluginsUtil.getPluginsDir()+"/"+name);
	}
	
	
}
