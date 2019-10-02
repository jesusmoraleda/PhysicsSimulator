package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {

	@Override
	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		this.typeTag = jsonObject.getString("type");

		if(this.typeTag.equals("nlug")) {
			return new NewtonUniversalGravitation();
		}
		else {
			return null;
		}
	}

}
