package org.bit.linc.plugins.plugins;

import java.util.ArrayList;

import org.bit.linc.commons.utils.ExResult;
import org.bit.linc.commons.utils.FileUtil;
import org.bit.linc.plugins.scripts.Script;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plugin {
	@SerializedName("pluginName")
	private String name;//插件名
	@SerializedName("pluginDetail")
	private String detail;
	@SerializedName("pluginIntroduction")
	private String intro;
	@Expose
	private ArrayList<Script> scripts;
	
	/**
	 * @param path plugin's absolutePath
	 * @param name plugin's name
	 */
	public Plugin(String name) {
		this.name = name;
	}
	public Plugin(String name,String intro,String detail){
		this.name = name;
		this.intro=intro;
		this.detail=detail;
	}
	public Plugin(String name,String intro){
		this.name = name;
		this.intro=intro;
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
	public void setInfo(String info) {
		this.intro = info;
	}
	/**
	 * get scripts.this method can't get scripts of this plugin real-time.if you want get scripts of this plugin really,
	 * you can use ScriptsUtil.getScriptList();
	 * @return
	 */
	public ArrayList<Script> getScripts() {
		return scripts;
	}
	/**
	 * this method just save scrpits temporary.
	 * @param scripts
	 */
	public void setScripts(ArrayList<Script> scripts) {
		this.scripts = scripts;
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
				Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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
