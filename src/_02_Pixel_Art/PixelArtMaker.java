package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PixelArtMaker implements MouseListener, ActionListener{
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	private static final String DATA_FILE = "src/_02_Pixel_Art/pixelStateSaved.dat";
	JButton saveButton = new JButton ("Save");
	ColorSelectionPanel csp;
	
	public void start() {
		gip = new GridInputPanel(this);	
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);
		
		window.add(gip);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		if(DATA_FILE.isEmpty()==false) {
			gp = load();
		}else {
			gp = new GridPanel(w, h, r, c);
		}
		csp = new ColorSelectionPanel();
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		gp.repaint();
		gp.addMouseListener(this);
		csp.add(saveButton);
		window.pack();

	}
	
	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
	
	public static void save(GridPanel data) {
		try(FileOutputStream fOS = new FileOutputStream(new File(DATA_FILE)); ObjectOutputStream oos = new ObjectOutputStream(fOS)){
			oos.writeObject(data);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static GridPanel load() {
		try (FileInputStream fIS = new FileInputStream(new File(DATA_FILE)); ObjectInputStream oIS = new ObjectInputStream(fIS)){
			return (GridPanel) oIS.readObject();
		}catch (IOException e){
			e.printStackTrace();
			return null;
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==saveButton) {
			save(gp);
		}
		
	}
	
}
