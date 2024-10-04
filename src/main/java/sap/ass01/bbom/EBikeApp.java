package sap.ass01.bbom;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;


public class EBikeApp extends JFrame implements ActionListener, Listener {
    
    private VisualiserPanel centralPanel;
    private JButton addUserButton, addEBikeButton, startRideButton;
    private final EBikeService eBikeService;
    
    public EBikeApp(){
        this.eBikeService = new EBikeService();
		this.eBikeService.registerListener(this);
		setupView();
    }

	@Override
	public void notifyModelChanged() {
		refreshView();
	}

	@Override
	public void notifyRideStart(Ride ride) {
		ride.start(this);
	}

	protected void setupView() {
        setTitle("EBike App");        
        setSize(800,600);
        setResizable(false);
        
        setLayout(new BorderLayout());

		addUserButton = new JButton("Add User");
		addUserButton.addActionListener(this);

		addEBikeButton = new JButton("Add EBike");
		addEBikeButton.addActionListener(this);

		startRideButton = new JButton("Start Ride");
		startRideButton.addActionListener(this);
		
		JPanel topPanel = new JPanel();
		topPanel.add(addUserButton);		
		topPanel.add(addEBikeButton);		
		topPanel.add(startRideButton);		
	    add(topPanel,BorderLayout.NORTH);

        centralPanel = new VisualiserPanel(800,500,this);
	    add(centralPanel,BorderLayout.CENTER);

	    	    		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(-1);
			}
		});
    }

    public void display() {
    	SwingUtilities.invokeLater(() -> {
    		this.setVisible(true);
    	});
    }

    public void endRide(String rideId) {
    	eBikeService.endRide(rideId);
    }
    
    public Enumeration<EBike> getEBikes(){
    	return eBikeService.getEBikes();
    }
        
    public Collection<User> getUsers(){
    	return eBikeService.getUsers();
    }
    
    public void refreshView() {
    	centralPanel.refresh();
    }

    @Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addEBikeButton) {
	        JDialog d = new AddEBikeDialog(this, eBikeService);
	        d.setVisible(true);
        } else if (e.getSource() == this.addUserButton) {
		    JDialog d = new AddUserDialog(this, eBikeService);
		    d.setVisible(true);
        } else if (e.getSource() == this.startRideButton) {
	        JDialog d = new RideDialog(this, eBikeService);
	        d.setVisible(true);
        }
	}

	private void log(String msg) {
		System.out.println("[EBikeApp] " + msg);
	}

	public static class VisualiserPanel extends JPanel {
        private long dx;
        private long dy;
        private EBikeApp app;
        
        public VisualiserPanel(int w, int h, EBikeApp app){
            setSize(w,h);
            dx = w/2 - 20;
            dy = h/2 - 20;
            this.app = app;
        }

        public void paint(Graphics g){
    		Graphics2D g2 = (Graphics2D) g;
    		
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    		          RenderingHints.VALUE_ANTIALIAS_ON);
    		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
    		          RenderingHints.VALUE_RENDER_QUALITY);
    		g2.clearRect(0,0,this.getWidth(),this.getHeight());
            
    		var it = app.getEBikes().asIterator();
    		while (it.hasNext()) {
    			var b = it.next();
    			var p = b.getLocation();
    			int x0 = (int)(dx+p.x());
		        int y0 = (int)(dy-p.y());
		        g2.drawOval(x0,y0,20,20);
		        g2.drawString(b.getId(), x0, y0 + 35);
		        g2.drawString("(" + (int)p.x() + "," + (int)p.y() + ")", x0, y0+50);
    		} 
    		
    		var it2 = app.getUsers().iterator();
    		var y = 20;
    		while (it2.hasNext()) {
    			var u = it2.next();
    			g2.drawRect(10,y,20,20);
		        g2.drawString(u.getId() + " - credit: " + u.getCredit(), 35, y+15);
		        y += 25;
    		};
            
        }
        
        public void refresh(){
            repaint();
        }
    }

	
	
	public static void main(String[] args) {
		var w = new EBikeApp();
		w.display();
	}
	
}
