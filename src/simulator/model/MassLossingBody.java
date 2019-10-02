package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body{

	// un n�mero (double) entre 0 y 1 que representa el factor de p�rdida de masa. 
	private double lossFactor;
	// un n�mero positivo (double) que indica el intervalo de tiempo (en segundos) despu�s del cual el objeto pierde masa. 
	private double lossFrequency;
	private double c;
	public MassLossingBody(String id, Vector v, Vector a, Vector p, double m,double lossFrequency, double lossFactor) {
		super(id,v,a,p,m);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		this.c = lossFrequency;
	}
	
	public void move(double t) {
		setPosition(this.p.plus(this.v.scale(t)).plus(this.a.scale(0.5).scale(t*t)));
		setVelocity(this.v.plus(this.a.scale(t)));
		if(this.c >= this.lossFrequency) {
			this.m = m*(1-this.lossFactor);
			this.c = 0.0;
		}
		c += t;
	}
}
