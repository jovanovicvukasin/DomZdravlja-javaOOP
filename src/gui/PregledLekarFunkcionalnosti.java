package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import pregled.Pregled;
import pregled.PregledUtils;
import pregled.Status;

public class PregledLekarFunkcionalnosti extends JFrame{
	
	private JLabel lblStatus = new JLabel("Status:");
	private JComboBox<Status> cmbStatus = new JComboBox<Status>(Status.values());
	
	private Pregled pregled;
	private JButton btnOk = new JButton("Potvrdi");
	
	public PregledLekarFunkcionalnosti(Pregled pregled) {
		this.pregled = pregled;
		setTitle("Izabran pregled: " + this.pregled.getId());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGUI();
		initActions();
		setResizable(false);
		setSize(400,400);
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		cmbStatus.removeItem(Status.ZATRAZEN);
		cmbStatus.removeItem(Status.ZAKAZAN);
		
		add(lblStatus);
		add(cmbStatus);
		add(new JLabel());
		add(btnOk);
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Status status = (Status) cmbStatus.getSelectedItem();
				pregled.setStatus(status);
				PregledUtils.snimiPregled();
				PregledLekarFunkcionalnosti.this.dispose();
				PregledLekarFunkcionalnosti.this.setVisible(false);
				
			}
		});
		
	}
}
