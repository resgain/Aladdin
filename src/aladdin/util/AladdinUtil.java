package aladdin.util;

import java.io.StringWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.resgain.embeddb.Db4oServiceFactory;
import org.resgain.renderer.bean.VelocitySLF4JLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aladdin.entity.Attribute;
import aladdin.service.AladdinDbConfig;
import aladdin.service.AladdinService;

import com.resgain.sparrow.pdm.bean.Column;
import com.resgain.sparrow.pdm.bean.Table;

public class AladdinUtil
{
	private static AladdinUtil instance = new AladdinUtil();
	private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
	private static Map<String, CompiledScript> ccMap = new Hashtable<>();
	private static VelocityEngine ve = new VelocityEngine();
	
	private static Logger logger = LoggerFactory.getLogger(AladdinUtil.class);
	
	static {
        Velocity.addProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, VelocitySLF4JLog.class.getName()); 
        Velocity.addProperty("input.encoding", "UTF-8"); 
        Velocity.addProperty("output.encoding", "UTF-8"); 
		ve.init();
		
		List<Attribute> attList = Db4oServiceFactory.getInstance(AladdinService.class, AladdinDbConfig.INSTANCE).getAttributeList();
		for (Attribute att : attList) {
			try {
				System.out.println(att.getName()+":\t"+att.getContent());
				putScript(att.getName(), att.getContent());
			} catch (Exception e) {
				logger.error("属性{}的方法体编译错误:{}", att.getName(), e);
			}
		}
	}

	public static AladdinUtil getInstance(){
		return instance;
	}
	
	public Object tpa(String method, Object v) throws Exception{
		if(!(v instanceof Table) && !(v instanceof Column))
			return v;
		CompiledScript cs = getCompiledScriptByName(method);
		if(cs!=null)
			return scriptMacro(cs, v);
		String value = (v instanceof Table)?((Table)v).getCode():((Column)v).getCode();
		switch (method) {
		case "class":
			return toClassOrPropName(value, true);
		case "prop":
			return toClassOrPropName(value, false);
		case "get":
			return (v instanceof Column)?getBeanPropMethodName((Column)v):"get"+toClassOrPropName(value, true);
		case "set":
			return "set"+toClassOrPropName(value, true);
		case "type":
			return (v instanceof Column)?getBeanPropType((Column)v):toClassOrPropName(value, true);
		default:
			break;
		}
		return null;
	}
	
	public static void putScript(String name, String script) throws Exception
	{
		ccMap.remove(name);
		Compilable compilable = (Compilable) engine;
		ccMap.put(name, compilable.compile("("+script+"(obj))")); //解析编译脚本函数;
	}	
	
	public static void removeScript(String name)
	{
		if(ccMap.containsKey(name))
			ccMap.remove(name);
	}
	
    public static String parse(String content, Map<String, Object> context) throws Exception
    {
        if(context==null)
            return content;
        StringWriter sw = new StringWriter();
        ve.evaluate(new VelocityContext(context), sw, "LOG", content);
        return sw.toString();
    }	
	
	private static CompiledScript getCompiledScriptByName(String name)
	{
		if(ccMap.containsKey(name))
			return ccMap.get(name);
		return null;
	}
	
	private static Object scriptMacro(CompiledScript cc, Object obj) throws Exception
	{
		Bindings bindings = engine.createBindings(); //Local级别的Binding
		bindings.put("obj", obj);
		return cc.eval(bindings);
	}	

	private static String toClassOrPropName(String str, boolean flag)
    {
        StringBuffer sb = new StringBuffer();
        String split[] = str.split("_");
        int len = split.length;
        for (int i = 0; i < len; i++) {
            sb.append(split[i].substring(0, 1).toUpperCase()).append(split[i].substring(1).toLowerCase());
        }
        String ret = sb.toString();
        return flag?ret:(ret.substring(0, 1).toLowerCase()+ret.substring(1));
    }
	
	private static String getBeanPropMethodName(Column column)
	{
		if(column.getDomain()!=null && column.getDomain().getCode().toLowerCase().indexOf("boolean")>=0)
			return "is"+toClassOrPropName(column.getCode(), true);
		return "get"+toClassOrPropName(column.getCode(), true);
	}
	
	private static String getBeanPropType(Column column)
	{
        String jtype=column.getType().toLowerCase();
        if(jtype.indexOf("char")>=0 || jtype.indexOf("clob")>=0 || jtype.indexOf("text")>=0)
        	jtype="String";
        else if(jtype.indexOf("date")>=0)
        	jtype="Date";
        else if(jtype.indexOf("money")>=0 || jtype.indexOf("double")>=0 || jtype.indexOf("float")>=0)
        	jtype="double";
        else if(jtype.indexOf("image")>=0 || jtype.indexOf("blob")>=0)
        	jtype="byte[]";
        else
        	jtype = "long";   
        if(column.getDomain()!=null && column.getDomain().getCode().toLowerCase().indexOf("boolean")>=0)
        	jtype = "Boolean";
        return jtype;
	}

}