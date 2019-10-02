package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	private JLabel _currTime;     // for current time
	private JLabel _currLaws;     // for gravity laws
	private JLabel _numOfBodies;   // for number of bodies
	private static JLabel time;     // for current time
	private static JLabel laws;     // for gravity laws
	private static JLabel bodies;   // for number of bodies
	private double _timecount = 0.0;
	private double _bodiescount = 0.0;
	
	StatusBar(Controller ctrl) {
		initGUI(ctrl);
		ctrl.addObserver(this);
	}
	private void initGUI(Controller ctrl) {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		_currTime = new JLabel("Time:");
		_currTime.setPreferredSize(new Dimension(50,30));
		add(_currTime);
		
		time = new JLabel(String.valueOf(_timecount));
		time.setPreferredSize(new Dimension(50,30));
		add(time);
		
		_numOfBodies=new JLabel("Bodies:");
		_numOfBodies.setPreferredSize(new Dimension(50,30));
		add(_numOfBodies);
		
		bodies = new JLabel(String.valueOf(_bodiescount));
		bodies.setPreferredSize(new Dimension(50,30));
		add(bodies);
		
		_currLaws=new JLabel("Laws:");
		_currLaws.setPreferredSize(new Dimension(50,30));
		add(_currLaws);
		
		laws = new JLabel(ctrl.getGravityLawsFactory().getInfo().get(0).getString("desc"));
		laws.setPreferredSize(new Dimension(400,30));
		add(laws);
		
		
	}
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				StatusBar.time.setText(String.valueOf(0.0));
				StatusBar.bodies.setText(String.valueOf(0.0));
				_bodiescount = 0.0;
			} });

		
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				_bodiescount++;
				StatusBar.bodies.setText(String.valueOf(_bodiescount));
			} });
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				_timecount += time;
				StatusBar.time.setText(String.valueOf(_timecount));
			} });
		
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				StatusBar.laws.setText(gLawsDesc);
			} });

		
	}
	@Override
	public void onListBodies(List<Body> bodies) {
		// TODO Auto-generated method stub
		
	}
}
