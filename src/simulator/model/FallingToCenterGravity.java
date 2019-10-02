package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class FallingToCenterGravity implements GravityLaws{


	private final double G = -9.81;
	
	@Override
	public void apply(List<Body> bodies) {
		for (int i = 0; i < bodies.size(); i++) {
			Vector acel = bodies.get(i).p.direction();
			acel = acel.scale(G);
			bodies.get(i).setAcceleration(acel);
		}
	}
	
	@Override
	public String toString() {
		return "FallingToCenterGravity [G=" + G + "]";
	}

}
