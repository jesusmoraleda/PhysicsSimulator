package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {

	@Override
	protected Body createTheInstance(JSONObject jsonObject) {
		double darr[] = new double[2];
		this.typeTag = jsonObject.getString("type");
		
		if(this.typeTag.equals("mlb")) {
			String id = jsonObject.getJSONObject("data").getString("id");

			//JSONArray jsonArray = jsonObject.getJSONArray("pos");
			JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("pos");
			if (jsonArray != null) {
				int len = jsonArray.length();
				for (int i = 0; i < len; i++)
					darr[i] = jsonArray.getDouble(i);
			}
			Vector p = new Vector(darr);

			//jsonArray = jsonObject.getJSONArray("vel");
			jsonArray = jsonObject.getJSONObject("data").getJSONArray("vel");
			if (jsonArray != null) {
				int len = jsonArray.length();
				for (int i = 0; i < len; i++)
					darr[i] = jsonArray.getDouble(i);
			}
			Vector v = new Vector(darr);

			Vector a = new Vector(2);

			double m = jsonObject.getJSONObject("data").getDouble("mass");
			double freq = jsonObject.getJSONObject("data").getDouble("freq");
			double factor = jsonObject.getJSONObject("data").getDouble("factor");

			return new MassLossingBody(id,v,a,p,m,freq,factor);
		}
		else {
			return null;
		}
	}

}
