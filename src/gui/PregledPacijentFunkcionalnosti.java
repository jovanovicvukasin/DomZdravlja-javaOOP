package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import pregled.Pregled;
import pregled.PregledUtils;
import pregled.Status;

public class PregledPacijentFunkcionalnosti extends JFrame {
	
	private JLabel lblOpis = new JLabel("Opis:");
	private JTextField txtOpis = new JTextField(30);
	
	private JLabel lblStatus = new JLabel("Status:");
	private JComboBox<Status> cmbStatus = new JComboBox<Status>(Status.values());
	
	private Pregled pregled;
	private JButton btnOk = new JButton("Potvrdi");
	
	public PregledPacijentFunkcionalnosti(Pregled pregled) {
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
		
		if(this.pregled != null) {
			popunjavanjePolja();
		}
		
		if(this.pregled.getStatus().equals(Status.ZAKAZAN)) {
			txtOpis.setEditable(false);
		}
		
		if(this.pregled.getStatus().equals(Status.ZATRAZEN)) {
			cmbStatus.removeItem(Status.ZAKAZAN);
			cmbStatus.removeItem(Status.ZAVRSEN);
		}
		
		if(this.pregled.getStatus().equals(Status.ZAKAZAN)) {
			cmbStatus.removeItem(Status.ZATRAZEN);
			cmbStatus.removeItem(Status.ZAVRSEN);
		}
		
		
		//cmbStatus.removeItem(Status.ZATRAZEN);
		//cmbStatus.removeItem(Status.ZAVRSEN);
		//cmbStatus.removeItem(Status.ZAKAZAN);
		
		add(lblOpis);
		add(txtOpis);
		add(lblStatus);
		add(cmbStatus);
		add(new JLabel());
		add(btnOk);
	}
	
	private void popunjavanjePolja() {
		txtOpis.setText(this.pregled.getOpis());
		cmbStatus.setSelectedItem(this.pregled.getStatus());
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String opis = txtOpis.getText();
				Status status = (Status) cmbStatus.getSelectedItem();
				pregled.setOpis(opis);
				pregled.setStatus(status);
				PregledUtils.snimiPregled();
				PregledPacijentFunkcionalnosti.this.dispose();
				PregledPacijentFunkcionalnosti.this.setVisible(false);
			}
		});
	}

}
