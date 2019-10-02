package simulator.model;

import simulator.misc.Vector;

public class Body {
	protected String id;
	protected Vector v;//velocidad
	protected Vector a;//aceleracion
	protected Vector p;//posicion
	protected double m;//masa
	
	
	public Body(String id, Vector v, Vector a, Vector p, double m) {
		this.id = id;
		this.v = v;
		this.a = a;
		this.p = p;
		this.m = m;
	}

	public String getId() { return this.id; }
	public Vector getVelocity() { return this.v; }
	public Vector getAcceleration() { return this.a; }
	public Vector getPosition() { return this.p; }
	public double getMass() { return this.m; }
	void setVelocity( Vector v) { this.v = v;}
	void setAcceleration(Vector a) { this.a = a;}
	void setPosition(Vector p) { this.p = p;}
	
	// p + v*t + 1/2*a*t^2
	protected void move(double t) {
		setPosition(this.p.plus(this.v.scale(t)).plus(this.a.scale(0.5).scale(t*t)));
		setVelocity(this.v.plus(this.a.scale(t)));
	}
	@Override
	public String toString() {
		return "{ \"id\": " + "\""+id+"\"" + ", \"mass\": " + m + ", \"pos\": " + p + ", \"vel\": " + v + ", \"acc\": " + a +" }";
	}
	
	
	
}
