package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.Caret;

import korisnici.Korisnik;
import korisnici.KorisnikUtils;
import korisnici.Lekar;
import korisnici.Pacijent;
import net.miginfocom.swing.MigLayout;
import pregled.Pregled;
import pregled.PregledUtils;
import pregled.Status;

public class PregledZakazivanje extends JFrame {
	
	//private static int ID_generator = PregledUtils.getPregledi().size() + 10;
	//private static int ID_generator = 10;
	private JTextField txtPacijent = new JTextField(10);
	private JTextField txtLekar = new JTextField(10);
	private Pacijent pacijent;
	private Korisnik korisnik;
	private JLabel lblOpis = new JLabel("Kratak opis:");
	private JTextField txtOpis = new JTextField(30);
	private Pregled pregled;
	private JButton btnOk = new JButton("Potvrdi");


	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

	public PregledZakazivanje(Pregled pregled, Pacijent pacijent) {
		setTitle("Zakazivanje pregleda");
		this.pacijent = pacijent;
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
		
		//txtPacijent.setText(pacijent.getKorisnickoIme());
		//txtLekar.setText(null);
		
		//System.out.println(korisnik);

		
		//add(txtLekar);
		//add(txtPacijent);
		add(lblOpis);
		add(txtOpis);
		add(new JLabel());
		add(btnOk);
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Pregled novi = new Pregled();
				for(Pregled p:PregledUtils.getPregledi()) {
//					if(p.getId() == PregledUtils.getPregledi().size()) {
						novi.setId(p.getId()+1);
						System.out.println(p.getId()+1);
//					}
				}
				if(validacija() == true) {
					System.out.println(pacijent);

					//novi.setId(ID_generator++);
					novi.setPacijent((Pacijent)pacijent);
					
					novi.setLekar(pacijent.getLekar());
					java.util.Date termin;
					try {
						termin = DATE_TIME_FORMAT.parse("00.00.0000. 00:00");
						novi.setTermin(termin);	
						
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					novi.setSoba("");
					novi.setOpis(txtOpis.getText());
					novi.setStatus(Status.ZATRAZEN);
					PregledUtils.snimiPregledUListu(novi);
					dispose();
				}
				
			}
		});
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Niste uneli opis pregleda!";
		
		if (txtOpis.getText().trim().equals("")) {
			ok = false;
		}
		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
