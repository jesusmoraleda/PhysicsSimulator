package simulator.factories;


import org.json.*;

public abstract class Builder<T> {

	protected String typeTag;
	protected String desc;

	public T createInstance(JSONObject info) {
		this.typeTag = info.get("type").toString();
		//JSONArray leng = (JSONArray) info.get("data");

		/*try {
			switch(typeTag) {
			case "basic" : 
				if(leng.length() == 4) return createTheInstance(info);
				else throw new IllegalArgumentException();
			case "mlb" :
				if(leng.length() == 6) return createTheInstance(info);
				else throw new IllegalArgumentException();
			case "nlug" :
				if(leng.length() == 0) return createTheInstance(info);
				else throw new IllegalArgumentException();
			case "ftcg" :
				if(leng.length() == 0) return createTheInstance(info);
				else throw new IllegalArgumentException();
			case "ng" :
				if(leng.length() == 0) return createTheInstance(info);
				else throw new IllegalArgumentException();
			default : 
				return null;
			}

		}catch(IllegalArgumentException eo) {
			System.err.println("Illegal Argument");
		}*/
		try {
			switch(typeTag) {
			case "basic" : 
				return createTheInstance(info);
			case "mlb" :
				return createTheInstance(info);
			case "nlug" :
				 return createTheInstance(info);
			case "ftcg" :
				return createTheInstance(info);
			case "ng" :
				return createTheInstance(info);
			default : 
				return null;
			}

		}catch(IllegalArgumentException eo) {
			System.err.println("Illegal Argument");
		}
		return null;

	}
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", typeTag);
		info.put("data", createData());
		info.put("desc", desc);
		return info;
	}
	public JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "");
		data.put("pos","");
		data.put("vel","");
		data.put("mass", "");
		return data;
	}
	protected abstract T createTheInstance(JSONObject jsonObject);
}
