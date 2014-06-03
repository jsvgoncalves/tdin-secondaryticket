package resources;

import org.json.JSONObject;

public class JSONHelper {
	
	public static String getString(JSONObject obj, String key, String def)
	{
		try {
			return obj.getString(key);
		} catch(Exception e) {
			return def;
		}
	}
	
	public static String getString(JSONObject obj, String key)
	{
		return JSONHelper.getString(obj, key, null);
	}
	
}
