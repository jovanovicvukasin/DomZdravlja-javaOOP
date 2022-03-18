package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import korisnici.KategorijaOsiguranja;
import korisnici.Knjizica;
import korisnici.KorisnikUtils;
import net.miginfocom.swing.MigLayout;

public class ZKFunkcionalnosti extends JFrame{
	
	private JLabel lblKategorijaOsiguranja = new JLabel("Kategorija osiguranja:");
	private JComboBox<KategorijaOsiguranja> cmbKategorijaOsiguranja = new JComboBox<KategorijaOsiguranja>(KategorijaOsiguranja.values());
	private Knjizica knjizica;
	private JButton btnOk = new JButton("Potvrdi");
	
	public ZKFunkcionalnosti(Knjizica knjizica) {
		this.knjizica = knjizica;
		setTitle("Izmena knjizice: " + this.knjizica.getBroj());
		
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
		
		if(this.knjizica != null) {
			popunjavanjePolja();
		}
		
		add(lblKategorijaOsiguranja);
		add(cmbKategorijaOsiguranja);
		add(new JLabel());
		add(btnOk);
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				KategorijaOsiguranja kategorijaOsiguranja = (KategorijaOsiguranja) cmbKategorijaOsiguranja.getSelectedItem();
				knjizica.setKategorijaOsiguranja(kategorijaOsiguranja);
				
				KorisnikUtils.snimiKnjizicu();
				ZKFunkcionalnosti.this.dispose();
				ZKFunkcionalnosti.this.setVisible(false);
			}
		});
	}
	
	private void popunjavanjePolja() {
		cmbKategorijaOsiguranja.setSelectedItem(this.knjizica.getKategorijaOsiguranja());
	}
}