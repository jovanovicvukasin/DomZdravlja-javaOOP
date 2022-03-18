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
import korisnici.Pacijent;
import net.miginfocom.swing.MigLayout;
import pregled.Pregled;
import pregled.PregledUtils;
import pregled.Status;

public class PregledPacijent extends JFrame{
	
	private Korisnik pacijent;
	public static Pacijent korisnik;

	private static ArrayList<Pregled> listaa = new ArrayList<Pregled>();
	private JTable tablePregledPacijent;
	private JButton btnZakazivanje = new JButton("Zakazi novi pregled");
	private JButton btnIzmeni = new JButton("Izmeni status i opis pregleda");
	private JToolBar mainToolBar = new JToolBar();
	private Pregled pregled;
	
	public PregledPacijent(Korisnik pacijent) {
		this.pacijent = pacijent;
		setTitle("Pregledi za pacijenta: " + this.pacijent.getKorisnickoIme());
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
	
	public void initGUI() {
		MigLayout layout = new MigLayout("wrap, top, left", "[]", "[]5[]");
		setLayout(layout);
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image img = kit.getImage("src/slike/slika.png");
		setIconImage(img);
		
		listaa.clear();
		for(Pregled p:PregledUtils.getPregledi()) {
			if(p.getPacijent().getKorisnickoIme().equals(pacijent.getKorisnickoIme())) {
				//if(!p.getStatus().equals(Status.OTKAZAN) && )
				listaa.add(p);
			}
		}
		
		String[] header = new String[] {"ID","Pacijent", "Lekar", "Termin", "Soba", "Opis", "Status"};
		
		int ukupno = listaa.size();
		
		Object [][]content = new Object[ukupno][header.length];
		for(int i=0;i<listaa.size();i++) {
			Pregled p = listaa.get(i);
			content[i][0] = p.getId();
			content[i][1] = p.getPacijent();
			content[i][2] = p.getLekar();
			content[i][3] = p.getTermin();
			content[i][4] = p.getSoba();
			content[i][5] = p.getOpis();
			content[i][6] = p.getStatus();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tablePregledPacijent = new JTable(model);
		tablePregledPacijent.setRowSelectionAllowed(true);
		tablePregledPacijent.setColumnSelectionAllowed(false);
		tablePregledPacijent.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tablePregledPacijent.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tablePregledPacijent);
		scrollPane.setPreferredSize(new Dimension(900, 400));
		scrollPane.setMaximumSize(new Dimension(900, 400));
		add(scrollPane, BorderLayout.CENTER);
		add(btnZakazivanje, "split 2, right");
		add(btnIzmeni);
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePregledPacijent.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za izmenu.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}
				else {
					DefaultTableModel model = (DefaultTableModel) tablePregledPacijent.getModel();
					int id = Integer.parseInt(model.getValueAt(red, 0).toString());
					Pregled pregled = PregledUtils.najiPregled(id);
					if(pregled != null && !pregled.getStatus().equals(Status.OTKAZAN) && !pregled.getStatus().equals(Status.ZAVRSEN)) {
						PregledPacijentFunkcionalnosti ppf = new PregledPacijentFunkcionalnosti(pregled);
						ppf.setVisible(true);
					}else {JOptionPane.showMessageDialog(null, "Ovaj pregled je otkazan ili zavrsen.", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
			}
		});
		
		btnZakazivanje.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// TODO Auto-generated method stub
				PregledZakazivanje pz = new PregledZakazivanje(pregled,korisnik);
				pz.setVisible(true);
				dispose();
				
			}
		});
		
		
	}

}
