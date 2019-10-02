package simulator.view;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private Controller _ctrl;
	private JButton open_button;
	private JButton physics_button;
	private JButton run_button;
	private JButton stop_button;
	private JButton exit_button;
	private JLabel delay_label;
	private JSpinner delay_spinner;
	private JLabel steps_label;
	private JSpinner steps_spinner;
	private JLabel dt_label;
	private JTextField dt_text;
	volatile Thread _thread;
	private SpinnerNumberModel modelo;

	public ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		setLayout(new FlowLayout(FlowLayout.LEFT));

		initButtons();
		initLabels();

	}
	private void initLabels() {
		
		delay_label = new JLabel("Delay:");
		delay_label.setPreferredSize(new Dimension(50,30));
		add(delay_label);

		modelo = new SpinnerNumberModel(0,0,1000,1);
		delay_spinner = new JSpinner(modelo);
		delay_spinner.setPreferredSize(new Dimension(100,30));
		add(delay_spinner);

		steps_label = new JLabel("Steps:");
		steps_label.setPreferredSize(new Dimension(50,30));
		add(steps_label);

		steps_spinner = new JSpinner();
		steps_spinner.setPreferredSize(new Dimension(100,30));
		add(steps_spinner);

		dt_label = new JLabel("Delta-Time:");
		dt_label.setPreferredSize(new Dimension(100,30));
		add(dt_label);

		dt_text = new JTextField();
		dt_text.setPreferredSize(new Dimension(100,30));
		add(dt_text);



	}
	private void initButtons() {
		//OPEN BUTTON
		open_button = new JButton();
		open_button.setPreferredSize(new Dimension(30,30));
		open_button.setBackground(Color.LIGHT_GRAY);
		open_button.setIcon(new ImageIcon("resources/icons/open.png"));
		open_button.setToolTipText("Load bodies file into the editor");

		open_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				//donde queremos que se abra el file chooser. Para cada ordenador es distinto :(
				fileChooser.setCurrentDirectory(new File("C:\\Users\\Jesus Martin\\eclipse-workspace\\PhysicsSimulator\\resources"));
				int seleccion = fileChooser.showOpenDialog(ControlPanel.this);
				if (seleccion == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					FileInputStream fichero;
					try {
						fichero = new FileInputStream(file);
						_ctrl.reset();
						_ctrl.loadBodies(fichero);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

			}

		});
		add(open_button);

		//PHYSICS BUTTON
		String mensaje = "Select gravity laws to be used:";
		String mensajeTitulo = "Gravity Laws selector";
		physics_button = new JButton();
		physics_button.setPreferredSize(new Dimension(30,30));
		physics_button.setBackground(Color.LIGHT_GRAY);
		physics_button.setIcon(new ImageIcon("resources/icons/physics.png"));
		physics_button.setToolTipText("Select a law of gravity");

		List<JSONObject> law =_ctrl.getGravityLawsFactory().getInfo();
		String[] laws = new String[law.size()];
		for(int i = 0; i < law.size(); i++) {
			laws[i] = law.get(i).getString("desc");
		}
		physics_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String respuesta = (String) JOptionPane.showInputDialog(null, mensaje, mensajeTitulo,
						JOptionPane.DEFAULT_OPTION, null, laws, laws[0]);
				for(int i = 0; i < law.size(); i++) {
					if(respuesta == laws[i]) {
						_ctrl.setGravityLaws(law.get(i));
						i = law.size();
					}
				}
			}

		});
		add(physics_button);

		//RUN BUTTON
		run_button = new JButton();
		run_button.setPreferredSize(new Dimension(30,30));
		run_button.setBackground(Color.LIGHT_GRAY);
		run_button.setIcon(new ImageIcon("resources/icons/run.png"));
		run_button.setToolTipText("Start the simulation");
		run_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open_button.setEnabled(false);
				physics_button.setEnabled(false);
				exit_button.setEnabled(false);
				_ctrl.setDeltaTime(Double.parseDouble(dt_text.getText()));//Caja de texto pasado a double
				int spinner = (int) delay_spinner.getValue();
				Long _spinnervalue = (long)spinner;
				
				
				_thread = new Thread(new Runnable() {
					public void run() {
						run_sim((int) steps_spinner.getValue(), _spinnervalue);
						open_button.setEnabled(true);
						physics_button.setEnabled(true);
						exit_button.setEnabled(true);
						_thread = null;
					}
				});
				_thread.start();
				
				
			}

		});
		add(run_button);

		//STOP BUTTON
		stop_button = new JButton();
		stop_button.setPreferredSize(new Dimension(30,30));
		stop_button.setBackground(Color.LIGHT_GRAY);
		stop_button.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop_button.setToolTipText("Stop the simulation");

		stop_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(_thread != null) {
					_thread.interrupt();
				}
			}

		});
		add(stop_button);

		//EXIT BUTTON
		exit_button = new JButton();
		exit_button.setPreferredSize(new Dimension(30,30));
		exit_button.setBackground(Color.LIGHT_GRAY);
		exit_button.setIcon(new ImageIcon("resources/icons/exit.png"));
		exit_button.setToolTipText("Exit");

		exit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int salir = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Confirm option",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(String.valueOf(salir)==null) {	
				}else if(salir == 0){
					System.exit(0);
				}
			}

		});
		add(exit_button);


	}

	private void run_sim(int n, long delay) {
		while(n > 0 && !_thread.isInterrupted()) {
			try {
				_ctrl.run(1);
				_thread.sleep(delay);
				n--;
			} catch (Exception e) {
				if(!_thread.isInterrupted()) {
					JOptionPane.showMessageDialog(null, "El hilo ha sido interrumpido");
				}
				else {
					SwingUtilities.invokeLater( new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(null, "Error");
						} });
					
				}
				
				return;
			}
		}
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				dt_text.setText(String.valueOf(dt));
			} });
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				dt_text.setText(String.valueOf(dt));
			} });

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		dt_text.setText(String.valueOf(dt));

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
