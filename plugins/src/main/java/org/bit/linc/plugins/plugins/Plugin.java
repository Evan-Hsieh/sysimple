package org.bit.linc.plugins.plugins;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.bit.linc.commons.cmdline.CmdCallBack;
import org.bit.linc.commons.cmdline.CmdType;
import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.ExResult;
import org.bit.linc.commons.utils.FileUtil;
import org.bit.linc.plugins.scripts.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Plugin {
	private static Logger logger=LoggerFactory.getLogger(Plugin.class);
	private String name;//插件名
	private String intro;
	private String detail;
	private ArrayList<Script> scriptsList;
	private transient Script pluginEntrance;
	
	
	/**
	 * Constructors of class Plugin
	 */
	//This no-argument constructor is necessary for JAXB
	public Plugin(){}
	public Plugin(String name) {
		this.name = name;
		this.intro="";
		this.scriptsList=new ArrayList<Script>();
	}
	public Plugin(String name,String intro){
		this(name);
		this.intro=intro;
	}
	public Plugin(String name,String intro,String detail){
		this(name,intro);
		this.detail=detail;
	}
	/**
	 * set/get plugin's name
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}


	/**
	 * set plugin's detail
	 * @return
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * get plugin's detail
	 * @return
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * get plugin's introduction
	 * @return
	 */
	public String getIntro() {
		return intro;
	}
	/**
	 * set plugin's introduction
	 * @return
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}
	/**
	 * get scripts.this method can't get scripts of this plugin real-time.if you want get scripts of this plugin really,
	 * you can use ScriptsUtil.getScriptList();
	 * @return
	 */
	@XmlElementWrapper(name="scripts")
	@XmlElement(name="script")
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
	 * create this plugin on file system
	 * @return
	 */
	public ExResult create(){
		ExResult result=new ExResult();
		String pluginDir=PluginsUtil.getPluginsDir()+"/"+name;
		result=FileUtil.CreateFile(false,pluginDir);
		if(result.code==0){
			//create the dir named scripts
			result=FileUtil.CreateFile(false,pluginDir+"/scripts");
			//create the script file
			result=FileUtil.CreateFile(true,pluginDir+"/scripts/"+name+"-start.sh");
			result=FileUtil.CreateFile(true,pluginDir+"/scripts/"+name+"-start.bat");
			//add the data of these two scripts into the field scriptsList
			scriptsList.add(new Script(name+"-start.sh","you can start the plugin from this script in linux"));
			scriptsList.add(new Script(name+"-start.bat","you can start the plugin from this script in windows"));
			//update 
			updateInfoXml();
			if(result.code!=0){
				FileUtil.DeleteFile(pluginDir);//if not success,clean all that have done
			}
		}else if(result.code!=1){//1：already exist,so can't remove this plugin
			FileUtil.DeleteFile(pluginDir);//if not success,clean all that have done
		}
		return result;
	}
	/**
	 * delete this plugin on file system
	 */
	public void delete(){
		FileUtil.DeleteFile(PluginsUtil.getPluginsDir()+"/"+name);
	}
	
	
	/**
	 * run this plugin
	 * @param interFile intermediate file
	 * @param callBack Class's name that implements CmdCallBack,You need to define your printLine() in this Class
	 * @throws SysimpleException :can not run plugin in this environment or *-start.sh/*-start.bat is not exist
	 */
	public void run(String interFile,CmdCallBack callBack) throws SysimpleException{
		try{
			if(CmdType.DOS.equals(CmdType.getCurrentType())){
				for(int i=0;i<scriptsList.size();i++){
					if(scriptsList.get(i).getName().endsWith("-start.bat")){
						pluginEntrance=scriptsList.get(i);
						pluginEntrance.run(interFile, callBack);
					}
				}
			}else{
				for(int i=0;i<scriptsList.size();i++){
					if(scriptsList.get(i).getName().endsWith("-start.sh")){
						pluginEntrance=scriptsList.get(i);
						pluginEntrance.run(interFile, callBack);
					}
				}
			}
		}catch(SysimpleException e){
			throw e;
		}
	}
	
	/**
	 * to know whether this plugin is running;
	 * @return
	 */
	public boolean isRun(){
		return pluginEntrance.isRun();
	}
	
	/**
	 * stop this plugin
	 */
	public void stop(){
		if(isRun()){
			this.pluginEntrance.stop();
		}
	}
	
	/**
	 * create script on file system
	 * @return
	 */
	public ExResult createScript(Script script){
		ExResult result=new ExResult();
		result=FileUtil.CreateFile(true,PluginsUtil.getPluginsDir()+"/"+name+"/scripts/"+script.getName());
		if(result.code==0){
			scriptsList.add(script);
			updateInfoXml();
		}
		return result;
	}
	/**
	 * delete script on file system
	 */
	public void deleteScript(String scriptName){
		FileUtil.DeleteFile(PluginsUtil.getPluginsDir()+"/"+name+"/scripts/"+scriptName);
		for(int i=0;i<scriptsList.size();i++){
			if(scriptsList.get(i).getName().equals(scriptName)){
				scriptsList.remove(i);
				updateInfoXml();
			}
		}
	}
	
	public void updateInfoXml(){
		String pluginDir=PluginsUtil.getPluginsDir()+"/"+name;
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Plugin.class);
			Marshaller marshaller = context.createMarshaller();
			//format the info.xml file, then it will be legible.
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshaller.marshal(this,new File(pluginDir+"/info.xml"));
		} catch (JAXBException e) {
			logger.error("something error in serializing plugin to info.xml");
			e.printStackTrace();
		}
	}
	
	
}
