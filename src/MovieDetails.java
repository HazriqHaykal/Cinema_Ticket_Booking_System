
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MovieDetails {

    public JFrame MDetailsFr;
    public String given_id;
    public JLabel rate;
    
    
    
    public MovieDetails() {
        initMovieDetails();
    }
    
    public void initMovieDetails(){
        
        MDetailsFr = new JFrame("Movie Details");
        
      
        MDetailsFr.setLayout(null);
        MDetailsFr.setBounds(100, 0, 700, 600);
        MDetailsFr.setResizable(false);
        MDetailsFr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MDetailsFr.setVisible(true);
        MDetailsFr.setAlwaysOnTop(true);
        
        
    }
    
}
