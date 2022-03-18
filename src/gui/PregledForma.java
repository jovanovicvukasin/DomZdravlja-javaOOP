package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import korisnici.Korisnik;
import korisnici.KorisnikUtils;
import korisnici.Uloga;
import net.miginfocom.swing.MigLayout;
import pregled.Pregled;
import pregled.PregledUtils;
import pregled.Status;

public class PregledForma extends JFrame{
	private JToolBar mainToolBar = new JToolBar();
	private JTable tablePregledi;
	private Pregled pregled;
	private JButton btnZakazivanje = new JButton("Zakazivanje");
	private JButton btnPotvrdaPregleda = new JButton("Odobri zatrazene preglede");
	private JButton btnObrisi = new JButton("Obrisi pregled");
	
	
	public PregledForma() {
		//PregledUtils.ucitavanjePregleda();
		setTitle("Prikaz svih pregleda");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		add(btnZakazivanje, "split 3,right");
		add(btnPotvrdaPregleda);
		add(btnObrisi);
		
		btnZakazivanje.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PregledFunkcionalnosti pfu = new PregledFunkcionalnosti(pregled);
				pfu.setVisible(true);
				dispose();
			}
		});
		
		btnPotvrdaPregleda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePregledi.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za izmenu.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tablePregledi.getModel();
					int id = Integer.parseInt(model.getValueAt(red, 0).toString());
					Pregled pregled = PregledUtils.najiPregled(id);
					if(pregled != null && pregled.getStatus().equals(Status.ZATRAZEN)) {
						PregledFunkcionalnosti pf = new PregledFunkcionalnosti(pregled);
						pf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Ovaj pregled nije Zatrazen.", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePregledi.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za brisanje.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(tablePregledi.getValueAt(red, 0).toString());
					Pregled p = PregledUtils.najiPregled(id);
					if(p!=null) {
						int izabran = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrdi brisanje pregleda", JOptionPane.YES_NO_OPTION);
						if(izabran == JOptionPane.YES_OPTION) {
							PregledUtils.getPregledi().remove(p);
							DefaultTableModel model = (DefaultTableModel) tablePregledi.getModel();
							model.removeRow(red);
							PregledUtils.snimiPregled();
						}
					}
				}
				
			}
		});
		
	}

}
