package com.nflabs.zeppelin.zengine;

import java.util.Map;

/**
 * Zeppelin Context. This context is passed to Zeppelin UDF's ZQL template
 * @author moon
 *
 */
public class ZContext {
	public String in;
	public String out;
	public String arg;
	private Map<String, Object> params;
	
	/**
	 * Initialize Zeppelin Context
	 * @param tableIn input table name
	 * @param tableOut output table name
	 * @param arg arguments
	 * @param params parameters to UDF
	 */
	public ZContext(String tableIn, String tableOut, String arg, Map<String, Object> params){
		this.in = tableIn;
		this.out = tableOut;
		this.arg = arg;
		this.params = params;
	}
	
	/**
	 * Get parameter by name
	 * @param paramName
	 * @return
	 */
	public Object param(String paramName){
		return params.get(paramName);
	}
	
	/**
	 * Get input table name
	 * @return
	 */
	public String in(){
		return in;
	}
	
	/**
	 * Get output table name
	 * @return
	 */
	public String out(){
		return out;
	}
	
	/**
	 * Get arguments
	 * @return
	 */
	public String arg(){
		return arg;
	}
}
