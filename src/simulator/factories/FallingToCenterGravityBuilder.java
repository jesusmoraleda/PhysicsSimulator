package simulator.factories;


import org.json.JSONObject;
import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws>{

	@Override
	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		this.typeTag = jsonObject.getString("type");
		if(this.typeTag.equals("ftcg")) {
			return new FallingToCenterGravity();
		}
		else {
			return null;
		}
	}

}
