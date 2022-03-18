package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import korisnici.Korisnik;

public class PacijentWindow extends JFrame{
	private Korisnik korisnik;
	
	public PacijentWindow(Korisnik korisnik) {
		setTitle("PACIJENT|" + "korisnicko ime: " + korisnik.getKorisnickoIme() + " " + "|lozinka: " + korisnik.getLozinka());
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
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu meni = new JMenu("MOJI PREGLEDI");
		
		JMenuItem itemPregledi = new JMenuItem("Pregledi");
		
		meni.add(itemPregledi);
		menuBar.add(meni);
		
		itemPregledi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PregledPacijent ppac = new PregledPacijent(korisnik);
				ppac.setVisible(true);
				
			}
		});
	}
}
