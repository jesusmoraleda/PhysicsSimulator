package simulator.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	private static final int _WIDTH = 1000;
	private static final int _HEIGHT = 1000; 

	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;

	Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder (
				BorderFactory.createLineBorder(Color.black, 2), "Viewer",
				TitledBorder.LEFT, TitledBorder.TOP));
		setPreferredSize(new Dimension(1000,1000));
		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;
		addKeyListener(new KeyListener() {
			// ...
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					break;
				case '=':
					autoScale();
					break;
				case 'h':
					_showHelp = !_showHelp;
					break;
				default:
				}
				repaint(); }

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}


			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		addMouseListener(new MouseListener() {
			// ...
			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}


			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		}); }
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


		// calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		// TODO draw a cross at center
		gr.setPaint(Color.RED);
		gr.drawRect(_centerX,_centerY-5,1,10);//linea vertical
		gr.drawRect(_centerX-5,_centerY,10,1);//linea horizontal

		// TODO draw help if _showHelp is true
		if (_showHelp) {
			String str = "h: toogle help, +: zoom-in, -: zoom-out, =:fit";
			gr.drawString(str, 5,25);
			str = "Scaling ratio: " + _scale;
			gr.drawString(str, 5,40);

		}
		
		// TODO draw bodies
		double radio = 5.0;
		double diametro =(radio * 2);
		gr.setStroke(new BasicStroke(1.f));//Grosor
		for(int i = 0; i < _bodies.size(); i++) {
			gr.setPaint(Color.blue);
			Vector xy = _bodies.get(i).getPosition();
			double x = xy.coordinate(0);
			double y = xy.coordinate(1);
			gr.drawOval(_centerX + (int) (x/_scale), _centerY - (int) (y/_scale),(int)diametro, (int)diametro );
			gr.fillOval(_centerX + (int) (x/_scale), _centerY - (int) (y/_scale),(int)diametro, (int)diametro );
			gr.setPaint(Color.BLACK);
			gr.drawString("b"+i,_centerX + (int) (x/_scale), _centerY - (int) (y/_scale)-10);//nombre
		}
	}
	// other private/protected methods
	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector p = b.getPosition();
			for (int i = 0; i < p.dim(); i++)
				max = Math.max(max,
						Math.abs(b.getPosition().coordinate(i)));
		}
		double size = Math.max(1.0, Math.min((double) getWidth(),
				(double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				setbodies(bodies);
				autoScale();
				repaint();
			} });

	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				setbodies(bodies);
				autoScale();
				repaint();
			} });
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				setbodies(bodies);
				autoScale();
				repaint();
			} });

	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		repaint();

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
	private void setbodies(List<Body> bodies) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				_bodies = bodies;
			} });
		

	}
}
