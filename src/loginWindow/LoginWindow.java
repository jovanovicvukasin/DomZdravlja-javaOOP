package loginWindow;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gui.LekarWindow;
import gui.MSWindow;
import gui.PacijentWindow;
import gui.PregledPacijent;
import korisnici.Korisnik;
import korisnici.KorisnikUtils;
import korisnici.Lekar;
import korisnici.MedicinskaSestra;
import korisnici.Pacijent;
import net.miginfocom.swing.MigLayout;
import pregled.PregledUtils;

public class LoginWindow extends JFrame {
	
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JButton btnCancel;
	
	public LoginWindow() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		setTitle("Dom zdravlja");
		setResizable(false);
		setSize(400,400);
		Container c = this.getContentPane();
		c.setBackground(Color.white);
		initGUI();
		initActions();
	}
	
	public void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]20[][]20[]");
		setLayout(layout);
		this.lblUsername = new JLabel("Korisnicko ime");
		this.txtUsername = new JTextField(20);
		this.passwordField = new JPasswordField(20);
		this.lblPassword = new JLabel("Lozinka");
		this.btnLogin = new JButton("Potvrdi");
		this.btnCancel = new JButton("Odustani");
		this.getRootPane().setDefaultButton(btnLogin);
		lblUsername.setForeground(new Color(70, 130, 180));
		lblPassword.setForeground(new Color(70, 130, 180));
		btnLogin.setForeground(new Color(70, 130, 180));
		btnCancel.setForeground(new Color(70, 130, 180));
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image img = kit.getImage("src/slike/slika.png");
		setIconImage(img);
		
		//prikaz prozora na sredini ekrana
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		ImageIcon addIcon = new ImageIcon("src/slike/slika.png");
		JLabel ImageLabel = new JLabel();
		ImageLabel.setIcon(addIcon);
		add(new JLabel());
		add(ImageLabel);
		add(lblUsername,"align");
		add(txtUsername,"wrap,pushx");
		add(lblPassword,"align label");
		add(passwordField, "wrap,pushx");
		add(new JLabel());
		add(btnLogin, "split 2");
		add(btnCancel);
		
	}
	
	private void initActions() {
		//kada kliknemo na cancel dugme
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Kliknuto na odustani!");
				
				//obrisano sve iz txt polja
				txtUsername.setText("");
				passwordField.setText("");
				//zatvaramo loginWindow i program
				dispose();
				
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String korisnickoIme = txtUsername.getText();
				String lozinka = new String(passwordField.getPassword());
				KorisnikUtils.ucitavanjeKorisnika();
				PregledUtils.ucitavanjePregleda();
				//KorisnikUtils.ucitavanjeKnjizica();
				
				boolean login = false;
				for(Korisnik kor : KorisnikUtils.getSviKorisnici()) {
					if(kor.getKorisnickoIme().equals(korisnickoIme) &&
							(kor.getLozinka().equals(lozinka))) {
						if(kor instanceof MedicinskaSestra) {
							LoginWindow.this.setVisible(false);
							LoginWindow.this.dispose();
							MSWindow msw = new MSWindow(kor);
							msw.setVisible(true);
						}else if (kor instanceof Lekar) {
							LoginWindow.this.setVisible(false);
							LoginWindow.this.dispose();
							LekarWindow lw = new LekarWindow(kor);
							lw.setVisible(true);
						}else if (kor instanceof Pacijent) {
							LoginWindow.this.setVisible(false);
							LoginWindow.this.dispose();
							PacijentWindow pw = new PacijentWindow(kor);
							pw.setVisible(true);
							PregledPacijent.korisnik = (Pacijent) kor;
						}else {
							JOptionPane.showMessageDialog(null, "Ne postoji", "Poruka",JOptionPane.WARNING_MESSAGE);
						}
						login=true;
						break;
					}
					}
				if (login != true) {
					JOptionPane.showMessageDialog(null, "Pogresan unos", "Greska", JOptionPane.WARNING_MESSAGE);

				}
				
				
			}
		});
	

}}
