package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import korisnici.Korisnik;
import pregled.PregledUtils;

public class MSWindow extends JFrame{
	private Korisnik korisnik;
	
	public MSWindow(Korisnik korisnik) {
		setTitle("MED.SESTRA|" + "korisnicko ime: " + korisnik.getKorisnickoIme() + " " + "|lozinka: " + korisnik.getLozinka());
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(700,500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		Container c = this.getContentPane();
		c.setBackground(Color.white);
		//ikonica
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image img = kit.getImage("src/slike/slika.png");
		setIconImage(img);
		
		//menuBar za medicinsku sestru
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu meni1 = new JMenu("Korisnici");
		JMenu meni2 = new JMenu("PREGLEDI");
		
		JMenuItem pregled = new JMenuItem("Pregledi");
		JMenuItem racun = new JMenuItem("Naplata racuna");
		JMenuItem itemMedicinskaSestra = new JMenuItem("Med. sestre");
		JMenuItem itemLekar = new JMenuItem("Lekari");
		JMenuItem itemPacijent = new JMenuItem("Pacijenti");
		JMenuItem itemZKnjizica = new JMenuItem("Zdr. knjizice");
		
		meni1.add(itemMedicinskaSestra);
		meni1.add(itemLekar);
		meni1.add(itemPacijent);
		meni1.add(itemZKnjizica);
		meni2.add(pregled);
		meni2.add(racun);
		menuBar.add(meni1);
		menuBar.add(meni2);
		
		
		itemLekar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LekarForma lf = new LekarForma();
				lf.setVisible(true);
			}
		});
		
		itemMedicinskaSestra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MSForma msf = new MSForma();
				msf.setVisible(true);
				
			}
		});
		
		itemPacijent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PacijentForma pf = new PacijentForma();
				pf.setVisible(true);
			}
		});
		
		itemZKnjizica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ZKForma zkf = new ZKForma();
				zkf.setVisible(true);
				
			}
		});
		
		pregled.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PregledForma prf = new PregledForma();
				prf.setVisible(true);
				
			}
		});
		
		racun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Racun rac = new Racun();
				rac.setVisible(true);
				
			}
		});
		
	}


}
