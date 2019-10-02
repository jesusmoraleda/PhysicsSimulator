package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	double delta_time;
	GravityLaws glaws;
	List<Body> bodies;
	double tiempo_actual;
	List<SimulatorObserver> observers;

	public PhysicsSimulator(double tpp, GravityLaws glaws, List<Body> bodies) {
		this.delta_time = tpp;
		this.glaws = glaws;
		this.bodies = bodies;
		this.tiempo_actual = 0.0;
		observers = new ArrayList<>();
	}

	public void advance() {
		glaws.apply(bodies);
		for(Body body : bodies) {
			body.move(delta_time);
		}
		this.tiempo_actual += delta_time;
		for (int j = 0; j < this.observers.size(); j++) {
			this.observers.get(j).onAdvance(bodies, tiempo_actual);
		}
		
	}
	public void addBody(Body b) {
		int i = 0;
		while(i < bodies.size()) {
			if(bodies.get(i).id.equals(b.id))
				throw new IllegalArgumentException();
			else i++;
		}
		bodies.add(b);
		for (int j = 0; j < this.observers.size(); j++) {
			this.observers.get(j).onBodyAdded(bodies, b);
		}
	}
	public String toString() {
		String cadena="";
		for (int i=0; i<bodies.size(); i++) {
			cadena = cadena + bodies.get(i).toString();
			if(i != bodies.size()-1) {
				cadena += ", ";
			}
		}
		return "{ \"time\": " + tiempo_actual  + ", \"bodies\": [ " + cadena + " ] }"; 
	}

	//vacia la lista de cuerpos y pone el tiempo a 0.0
	public void reset() {
		this.bodies.clear();
		this.tiempo_actual = 0.0;
		for (int i = 0; i < this.observers.size(); i++) {
			this.observers.get(i).onReset(bodies, tiempo_actual, delta_time, glaws.toString());
		}
	}

	//cambia el tiempo real por paso (delta-time de aqu� en adelante) a dt. Si dt tiene un valor no v�lido lanza una excepci�n de tipo IllegalArgumentException.
	public void setDeltaTime(double dt) throws NumberFormatException {
		//si dejo esto siempre me peta el delta_time
		//if(!Double.isNaN(dt))
			//throw new IllegalArgumentException("Dt is not a number");
		this.delta_time = dt;
		for (int i = 0; i < this.observers.size(); i++) {
			this.observers.get(i).onDeltaTimeChanged(delta_time);
		}
	}
	// cambia las leyes de gravedad del simulador a gravityLaws. Lanza una IllegalArgumentException si el valor no es v�lido, es decir, si es null.
	public void setGravityLaws(GravityLaws gravitylaws) {
		if(gravitylaws == null)
			throw new IllegalArgumentException("gravitylaws is not null");
		this.glaws=gravitylaws;
		for (int i = 0; i < this.observers.size(); i++) {
			this.observers.get(i).onGravityLawChanged(glaws.toString());
		}
	}
	public void addObserver(SimulatorObserver o) {
		if(!this.observers.contains(o)) {
			observers.add(o);
			o.onRegister(bodies, tiempo_actual, delta_time, glaws.toString());
		}
	}
	
	public void listBodies(SimulatorObserver o, List<Body> bodies){
		o.onListBodies(bodies);
	}
}
