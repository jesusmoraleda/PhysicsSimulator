package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver{
	
	
	private final static int _columnCount = 5;
	
	private int row = 5;
	private int column;
	private List<Body> _bodies; 
	public String [] _columnNames;
	
	BodiesTableModel(Controller ctrl) {
		_columnNames = new String[] {"Id", "Mass", "Position", "Velocity", "Acceleration"};
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
		
	}
	@Override
	public int getRowCount() {
		if(_bodies.size() != 0)
			return _bodies.size();
		else return row;
	}
	@Override
	public int getColumnCount() {
		return _columnCount;

	}
	@Override
	public String getColumnName(int column) {
		if(column >= 0 && column < _columnNames.length)
			return _columnNames[column];
		else return "";
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(_bodies.size() == 0) return "";
		if(columnIndex == 0)
			return _bodies.get(rowIndex).getId();
		else if ( columnIndex == 1)
			return _bodies.get(rowIndex).getMass();
		else if (columnIndex == 2)
			return _bodies.get(rowIndex).getPosition();
		else if (columnIndex == 3)
			return _bodies.get(rowIndex).getVelocity();
		else if (columnIndex == 4)
			return _bodies.get(rowIndex).getAcceleration();
		else return "";
	}


	public List<Body> get_bodies() {
		return _bodies;
	}
	public void set_bodies(List<Body> _bodies) {
		this._bodies = _bodies;
	}
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				set_bodies(bodies);
				fireTableStructureChanged();
			} });
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				set_bodies(bodies);
				fireTableStructureChanged();
			} });
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				set_bodies(bodies);
				fireTableStructureChanged();
			} });
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				set_bodies(bodies);
				fireTableStructureChanged();
			} });

	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onListBodies(List<Body> bodies) {
		// TODO Auto-generated method stub
		
	}

}
