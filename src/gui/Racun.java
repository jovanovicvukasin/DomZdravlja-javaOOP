package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import korisnici.KategorijaOsiguranja;
import korisnici.Knjizica;
import korisnici.KorisnikUtils;
import net.miginfocom.swing.MigLayout;
import pregled.Pregled;
import pregled.PregledUtils;
import pregled.Status;

public class Racun extends JFrame {
	private JToolBar mainToolBar = new JToolBar();
	private JTable tablePregledi;
	private Pregled pregled;
	private JButton btnRacun = new JButton("Obracunaj");
	private JLabel lblRacun = new JLabel("Iznos racuna:");
	private JTextField txtRacun = new JTextField(10);
	private Knjizica knjizica;
	
	public Racun() {
		setTitle("Pregled racuna");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		Container c = this.getContentPane();
		c.setBackground(Color.white);
		initGUI();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap, top, left", "[]", "[]5[]");
		setLayout(layout);
		
		String[] header = new String[] {"ID","Pacijent", "Lekar", "Termin", "Soba", "Opis", "Status"};
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image img = kit.getImage("src/slike/slika.png");
		setIconImage(img);
		
		int brojac = PregledUtils.getPregledi().size();
		Object[][] content = new Object[brojac][header.length];
		
		
		for(int i=0; i<PregledUtils.getPregledi().size(); i++) {
			Pregled pre = PregledUtils.getPregledi().get(i);
			content[i][0] = pre.getId();
			content[i][1] = pre.getPacijent();
			content[i][2] = pre.getLekar();
			content[i][3] = pre.getTermin();
			content[i][4] = pre.getSoba();
			content[i][5] = pre.getOpis();
			content[i][6] = pre.getStatus();
			
			
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tablePregledi = new JTable(model);
		tablePregledi.setRowSelectionAllowed(true);
		tablePregledi.setColumnSelectionAllowed(false);
		tablePregledi.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tablePregledi.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tablePregledi);
		scrollPane.setPreferredSize(new Dimension(900, 400));
		scrollPane.setMaximumSize(new Dimension(900, 400));
		add(scrollPane, BorderLayout.CENTER);
		add(btnRacun, "left");
		add(new JLabel());
		add(lblRacun, "split 2, left");
		add(txtRacun);
		
		btnRacun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePregledi.getSelectedRow();
				double prva = 300.00;
				double druga = 50.00;
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za prikaz racuna.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tablePregledi.getModel();
					int id = Integer.parseInt(model.getValueAt(red, 0).toString());
					Pregled pregled = PregledUtils.najiPregled(id);
					Knjizica knjizica = KorisnikUtils.nadjiKnjizicu(pregled.getPacijent().getKnjizica().getBroj());
					if(pregled != null && pregled.getStatus().equals(Status.ZAVRSEN)) {
						knjizica.getKategorijaOsiguranja();
						if(knjizica.getKategorijaOsiguranja().equals(KategorijaOsiguranja.TRECA)) {
							txtRacun.setText("BESPLATNO");
						}if(knjizica.getKategorijaOsiguranja().equals(KategorijaOsiguranja.DRUGA)) {
							txtRacun.setText(String.valueOf(druga) + " din.");
						}if(knjizica.getKategorijaOsiguranja().equals(KategorijaOsiguranja.PRVA)) {
							txtRacun.setText( String.valueOf(prva) + " din.");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Ovaj pregled nije ZAVRSEN\nNema obracuna", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
			}
		});
		
	}
}
