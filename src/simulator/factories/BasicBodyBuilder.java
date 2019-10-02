package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	@Override
	protected Body createTheInstance(JSONObject jsonObject) {
		double darr[] = new double[2]; 

		this.typeTag = jsonObject.getString("type");
		if(this.typeTag.equals("basic")){
			
			String id = jsonObject.getJSONObject("data").getString("id");

			JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("pos");
			if (jsonArray != null) { 
				int len = jsonArray.length();
				for (int i=0;i<len;i++)
					darr[i] = jsonArray.getDouble(i);
			}
			Vector p = new Vector(darr);

			jsonArray = jsonObject.getJSONObject("data").getJSONArray("vel");
			if (jsonArray != null) { 
				int len = jsonArray.length();
				for (int i=0;i<len;i++)
					darr[i] = jsonArray.getDouble(i);
			}
			Vector v = new Vector(darr);

			Vector a = new Vector(2);

			double m = jsonObject.getJSONObject("data").getDouble("mass");

			return new Body(id,v,a,p,m);
		}else {
			return null;
		}

	}



}
