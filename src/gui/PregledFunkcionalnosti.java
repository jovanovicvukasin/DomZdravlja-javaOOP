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

import korisnici.KorisnikUtils;
import korisnici.Lekar;
import korisnici.Pacijent;
import net.miginfocom.swing.MigLayout;
import pregled.Pregled;
import pregled.PregledUtils;
import pregled.Status;

public class PregledFunkcionalnosti extends JFrame {
	
	//private static int ID_generator = PregledUtils.getPregledi().size() +10;
	//private static int ID_generator = 10;
	private JLabel lblPacijent = new JLabel("Pacijent:");
	private JLabel lblLekar = new JLabel("Lekar:");
	private JLabel lblTermin = new JLabel("Termin pregleda");
	private JLabel lblSoba = new JLabel("Soba:");
	private JLabel lblOpis = new JLabel("Kratak opis:");
	private JLabel lblStatus = new JLabel("Status:");
	private JComboBox cmbPacijenti;
	private JComboBox cmbLekari;
	private static JTextField txtTermin = new JTextField(15);
	private JTextField txtSoba = new JTextField(10);
	private JTextField txtOpis = new JTextField(30);
	private JComboBox<Status> cmbStatus = new JComboBox<Status>(Status.values());

	private Pregled pregled;
	private JButton btnOk = new JButton("Potvrdi");
	private JButton btnOk2 = new JButton("Potvrdi");
	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	
	public PregledFunkcionalnosti(Pregled pregled) {
		
		this.pregled = pregled;
		if(this.pregled == null) {
			setTitle("Zakazivanje pregleda");
		}else {
			setTitle("Potvrda zatrazenog pregleda");
			txtOpis.setEditable(false);
			
			
		}
		
		
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
		
		ArrayList<String> lekari = new ArrayList<String>();
		try {
			lekari = KorisnikUtils.preuzmiKorImeLekara();
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.cmbLekari = new JComboBox(lekari.toArray());
		cmbLekari.setSelectedIndex(0);
		
		ArrayList<String> pacijenti = new ArrayList<String>();
		try {
			pacijenti = PregledUtils.preuzmiKorImePacijenta();
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.cmbPacijenti = new JComboBox(pacijenti.toArray());
		cmbPacijenti.setSelectedIndex(0);
		
		//cmbStatus.setSelectedItem(Status.ZAKAZAN);
		cmbStatus.removeItem(Status.OTKAZAN);
		cmbStatus.removeItem(Status.ZATRAZEN);
		cmbStatus.removeItem(Status.ZAVRSEN);
		
		if(this.pregled == null) {
			setTitle("Zakazivanje pregleda");
			add(lblPacijent);
			add(cmbPacijenti);
			add(lblLekar);
			add(cmbLekari);
			add(lblTermin);
			add(txtTermin);
			add(lblSoba);
			add(txtSoba);
			add(lblOpis);
			add(txtOpis);
			add(lblStatus);
			add(cmbStatus);
			add(new JLabel());
			add(btnOk);
			//add(btnOk2);
		}else {
			add(lblTermin);
			add(txtTermin);
			add(lblSoba);
			add(txtSoba);
			add(lblOpis);
			add(txtOpis);
			add(new JLabel());
			add(btnOk2);
		}
		
	}
	
	
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			
			//Dugme za zakazivanje novog pregleda
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Pregled novi = new Pregled();
				for(Pregled p:PregledUtils.getPregledi()) {
//					if(p.getId() == PregledUtils.getPregledi().size()) {
						novi.setId(p.getId()+1);
						System.out.println(p.getId()+1);
//					}
				}
				if(validacija() == true) {
					
					//novi.setId(ID_generator+1);
					novi.setPacijent(KorisnikUtils.nadjiPacijenta(cmbPacijenti.getSelectedItem().toString()));
					novi.setLekar(KorisnikUtils.nadjiLekara(cmbLekari.getSelectedItem().toString()));
					java.util.Date dateTime = null;
					while(dateTime == null) {
						String tekst = txtTermin.getText();
						try {
							dateTime = DATE_TIME_FORMAT.parse(tekst);
							novi.setTermin(dateTime);
						}catch (Exception e) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "Datum treba biti u\ndd.MM.yyyy. HH:mm\nformatu.", "Greska", JOptionPane.WARNING_MESSAGE);
							txtTermin.setText("");
							return;
						}
					}
					
					/*java.util.Date termin;
					try {
						termin = DATE_TIME_FORMAT.parse(txtTermin.getText());
						novi.setTermin(termin);	
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					novi.setSoba(txtSoba.getText());
					novi.setOpis(txtOpis.getText());
					novi.setStatus(Status.ZAKAZAN);
					PregledUtils.snimiPregledUListu(novi);
					dispose();
					
				}
			}
		});
		
		//Dugme za dopunu pregleda
		btnOk2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(validacija() == true) {
					java.util.Date termin;
					try {
						termin = DATE_TIME_FORMAT.parse(txtTermin.getText());
						pregled.setTermin(termin);	
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String soba = txtSoba.getText().trim();
					String opis = txtOpis.getText().trim();
					Status status = Status.ZAKAZAN;
					pregled.setSoba(soba);
					pregled.setOpis(opis);
					pregled.setStatus(status);
					PregledUtils.snimiPregled();
					PregledFunkcionalnosti.this.dispose();
					PregledFunkcionalnosti.this.setVisible(false);
				}
				
			}
		});
	}
	
	private void popunjavanjePolja() {
		txtTermin.setText("");
		txtSoba.setText("");
		txtOpis.setText(this.pregled.getOpis());
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Sva polja moraju biti popunjena:\n";
		
		if(txtTermin.getText().trim().equals("")) {
			poruka += "Termin;\n";
			ok = false;
		}
		if (txtSoba.getText().trim().equals("")) {
			poruka += "Soba;\n";
			ok = false;
		}
		if (txtOpis.getText().trim().equals("")) {
			poruka += "Opis;\n";
			ok = false;
		}
		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
