package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws>{

	@Override
	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		this.typeTag = jsonObject.getString("type");

		if(this.typeTag.equals("ng")) {
			return new NoGravity();
		}
		else {
			return null;
		}
	}

}
