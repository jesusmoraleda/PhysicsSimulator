package simulator.model;

import java.util.List;
import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws {


	private final double G = 6.67E-11;

	@Override
	public void apply(List<Body> bodies) {
		for (int i = 0; i < bodies.size(); i++) {
			Body ibody = bodies.get(i);
			if (ibody.m == 0.0) {//si la masa es 0 se inicializan los vectores con ceros
				ibody.setAcceleration(new Vector(ibody.getAcceleration().dim()));
				ibody.setVelocity(new Vector(ibody.getAcceleration().dim()));
			} else {
				Vector sumafij = new Vector(ibody.getAcceleration().dim());//variable para almacenar las sumas de las Fij
				for (int j = 0; j < bodies.size(); j++) {
					if (j != i) {
						Body jbody = bodies.get(j);
						double fij = G * ((ibody.m * jbody.m) / Math.pow((jbody.p.distanceTo(ibody.p)), 2));
						Vector d = jbody.p.minus(ibody.p).direction();
						Vector vfij = d.scale(fij);
						sumafij = sumafij.plus(vfij);
						//sumafij = sumafij.plus((jbody.p.minus(ibody.p).direction()).scale(G * ((ibody.m * jbody.m) / Math.pow((jbody.p.distanceTo(ibody.p)), 2))));
					}
				}
				ibody.setAcceleration(sumafij.scale(1 / ibody.getMass()));
			}
		}

	}
	@Override
	public String toString() {
		return "NewtonUniversalGravitation [G=" + G + "]";
	}


}
