package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	private PhysicsSimulator ps;
	private Factory<Body> factorybodies;
	private Factory<GravityLaws> factoryGlaws;

	public Controller(PhysicsSimulator ps, Factory<Body> factorybodies, Factory<GravityLaws> factoryGlaws) {
		this.ps = ps;
		this.factorybodies = factorybodies;
		this.factoryGlaws = factoryGlaws;
	}

	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray elArray = jsonInput.getJSONArray("bodies");
		for (int i = 0; i < elArray.length(); i++) {
			ps.addBody(factorybodies.createInstance(elArray.getJSONObject(i)));
		}
	}

	public void run(int n, OutputStream out) throws IOException {
		int i = 0;

		out.write("{\n\"states\": [\n".getBytes());
		while (i <= n) {
			out.write(ps.toString().getBytes());
			if (i != n) {
				out.write(",\n".getBytes());
			} else
				out.write('\n');
			ps.advance();
			i++;
		}
		out.write("]\n}".getBytes());
		out.close();

	}
	public void reset() {
		ps.reset();
	}

	public void setDeltaTime(double dt) {
		ps.setDeltaTime(dt);
	}

	public void addObserver(SimulatorObserver o) {
		ps.addObserver(o);
	}

	public void run(int n) {
		for(int i = 0; i < n; i++) {
			ps.advance();
		}
	}
	public Factory<GravityLaws> getGravityLawsFactory(){
		return this.factoryGlaws;
	}
	public void setGravityLaws(JSONObject info) {
		GravityLaws nglaws = this.factoryGlaws.createInstance(info);
		ps.setGravityLaws(nglaws);
	}

}

