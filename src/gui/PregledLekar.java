package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
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
import korisnici.Lekar;
import net.miginfocom.swing.MigLayout;
import pregled.Pregled;
import pregled.PregledUtils;
import pregled.Status;

public class PregledLekar extends JFrame{
	
	private Korisnik lekar;
	private static ArrayList<Pregled> lista = new ArrayList<Pregled>();
	private JTable tablePregledLekar;
	private JButton btnIzmeni = new JButton("Izmena statusa pregleda");
	private JToolBar mainToolBar = new JToolBar();
	private Pregled pregled;
	
	public PregledLekar(Korisnik lekar) {
		this.lekar=lekar;
		setTitle("Pregledi pacijenata za lekara: " + this.lekar.getKorisnickoIme());
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
		
		lista.clear();
		for(Pregled p:PregledUtils.getPregledi()) {
			if(p.getLekar().getKorisnickoIme().equals(lekar.getKorisnickoIme())) {
				lista.add(p);
			}
		}
		
		String[] header = new String[] {"ID","Pacijent", "Lekar", "Termin", "Soba", "Opis", "Status"};
		
		int ukupno=lista.size();
		
		Object [][]content = new Object[ukupno][header.length];
		for(int i=0;i<lista.size();i++) {
			Pregled p = lista.get(i);	
			content[i][0] = p.getId();
			content[i][1] = p.getPacijent();
			content[i][2] = p.getLekar();
			content[i][3] = p.getTermin();
			content[i][4] = p.getSoba();
			content[i][5] = p.getOpis();
			content[i][6] = p.getStatus();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tablePregledLekar = new JTable(model);
		tablePregledLekar.setRowSelectionAllowed(true);
		tablePregledLekar.setColumnSelectionAllowed(false);
		tablePregledLekar.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tablePregledLekar.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tablePregledLekar);
		scrollPane.setPreferredSize(new Dimension(900, 400));
		scrollPane.setMaximumSize(new Dimension(900, 400));
		add(scrollPane, BorderLayout.CENTER);
		add(btnIzmeni, "right");
		
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int red = tablePregledLekar.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za izmenu.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tablePregledLekar.getModel();
					int id = Integer.parseInt(model.getValueAt(red, 0).toString());
					Pregled pregled = PregledUtils.najiPregled(id);
					//System.out.println(id);
					//System.out.println(pregled.getStatus().toString());
					if(pregled != null && (pregled.getStatus().equals(Status.ZAKAZAN)) || pregled.getStatus().equals(Status.ZATRAZEN)) {
						PregledLekarFunkcionalnosti plf = new PregledLekarFunkcionalnosti(pregled);
						plf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Ovaj pregled je vec Otkazan ili je Zavrsen", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		
	}

}
