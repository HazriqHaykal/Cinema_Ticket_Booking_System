
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Admin {

    JPanel dashboardPnl;
    JPanel innerPanel;
    JLabel userNameLbl;
    JButton lOutBtn;
    JButton pswdBtn;
    JButton addBtn;
    JButton revenueBtn; // New button for revenue report
    DataConnector dCon;
    ImageIcon icon;
    ResultSet rs;
    ImageIcon ic;
    Blob bl;
    ScrollPane scrollPane;
    String uName;
    MainGUI.ButtonHandler hnd;

    Admin(MainGUI.ButtonHandler h) throws SQLException {
        hnd = h;
        initGUI();
    }

    private void initGUI() throws SQLException {

        dashboardPnl = new JPanel();
        dashboardPnl.setBackground(Color.BLACK);
        dCon = new DataConnector();

        innerPanel = new JPanel();
        innerPanel.setLayout(null);
        innerPanel.setBounds(100, 100, 1000, 500);
        innerPanel.setVisible(true);

        lOutBtn = new JButton("LOG OUT");
        lOutBtn.setFocusPainted(false);
        lOutBtn.setBorderPainted(false);
        lOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pswdBtn = new JButton("Change Password");
        pswdBtn.setFocusPainted(false);
        pswdBtn.setBorderPainted(false);
        pswdBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn = new JButton("Add");
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Initialize the revenue report button
        revenueBtn = new JButton("Revenue Dashboard");
        revenueBtn.setFocusPainted(false);
        revenueBtn.setBorderPainted(false);
        revenueBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        scrollPane = new ScrollPane();

        scrollPane.setBounds(0, 100, 1095, 500);
        userNameLbl = new JLabel("");
        userNameLbl.setFont(new Font("Times new roman", Font.BOLD, 18));
        userNameLbl.setOpaque(true);
        userNameLbl.setBackground(Color.black);
        userNameLbl.setForeground(Color.WHITE);

        userNameLbl.setText("       You're signed in as " + this.uName);

        dashboardPnl.setLayout(null);

        dashboardPnl.setSize(1100, 680);

        userNameLbl.setBounds(0, 30, 500, 30);

        addBtn.setBounds(610, 30, 100, 30);
        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.WHITE);

        // Set bounds and styles for the revenue report button
        revenueBtn.setBounds(720, 30, 140, 30);
        revenueBtn.setBackground(Color.BLACK);
        revenueBtn.setForeground(Color.WHITE);

        pswdBtn.setBounds(870, 30, 140, 30);
        pswdBtn.setBackground(Color.BLACK);
        pswdBtn.setForeground(Color.WHITE);

        lOutBtn.setBounds(1020, 30, 80, 30);
        lOutBtn.setBackground(Color.BLACK);
        lOutBtn.setForeground(Color.WHITE);

        addBtn.addActionListener(hnd);
        pswdBtn.addActionListener(hnd);
        lOutBtn.addActionListener(hnd);
        revenueBtn.addActionListener(hnd); // Add action listener for revenue button

        dashboardPnl.add(userNameLbl);
        dashboardPnl.add(addBtn);
        dashboardPnl.add(revenueBtn); // Add revenue button to the dashboard
        dashboardPnl.add(pswdBtn);
        dashboardPnl.add(lOutBtn);
        dashboardPnl.setVisible(false);

    }

    public void updateDashboard() throws SQLException, IOException {

        innerPanel.removeAll();
        int posX = 100, posY = 10;
        int n = dCon.getNumberOfMoviesTobePlayed();

        rs = dCon.getScheduledMoviesRecords();
        rs.next();
        JLabel schedual_Lbl = new JLabel("Scheduled Movies:");
        schedual_Lbl.setBounds(posX, posY, 500, 40);
        schedual_Lbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        innerPanel.add(schedual_Lbl);
        posY = posY + 45;
        JLabel[] Schedual_id = new JLabel[n];
        JLabel[] movie_picture = new JLabel[n];
        JLabel[] movie_name = new JLabel[n];
        JPanel[] movie_pnl = new JPanel[n];
        JLabel[] flag = new JLabel[n];

        for (int i = 0; i < n; i++) {
            flag[i] = new JLabel("sm");
            Blob b = rs.getBlob(3);
            Image img;
            try {
                img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Schedual_id[i] = new JLabel(rs.getString(1));
            movie_picture[i] = new JLabel(icon);
            movie_picture[i].setBounds(0, 0, 200, 200);

            movie_name[i] = new JLabel("  " + rs.getString(2));
            movie_name[i].setBounds(0, 0 + 200, 200, 35);
            movie_name[i].setFont(new Font("Times new roman", Font.BOLD, 18));

            Schedual_id[i].setVisible(false);
            movie_pnl[i] = new JPanel();
            movie_pnl[i].setLayout(null);
            movie_pnl[i].add(movie_picture[i]);
            movie_pnl[i].add(movie_name[i]);
            movie_pnl[i].add(Schedual_id[i]);
            movie_pnl[i].add(flag[i]);
            movie_pnl[i].setBackground(Color.WHITE);
            movie_pnl[i].setBounds(posX, posY, 200, 235);
            movie_pnl[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            movie_pnl[i].addMouseListener(hnd);
            posX = (posX + 225) % 900;

            if (posX == 100 && i != n - 1) {
                posY = posY + 250;
            }

            innerPanel.add(movie_pnl[i]);
            rs.next();
        }
        posX = 100;
        posY = posY + 250;
        n = dCon.getCountOfAllMovies();

        rs = dCon.getAllMoviesRecord();
        rs.next();
        JLabel allMoviesLbl = new JLabel("All Available Movies:");
        allMoviesLbl.setBounds(posX, posY + 20, 500, 40);
        allMoviesLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        innerPanel.add(allMoviesLbl);
        posY = posY + 65;

        Schedual_id = new JLabel[n];
        movie_picture = new JLabel[n];
        movie_name = new JLabel[n];
        movie_pnl = new JPanel[n];
        flag = new JLabel[n];

        for (int i = 0; i < n; i++) {
            flag[i] = new JLabel("am");
            Blob b = rs.getBlob(3);
            Image img;
            try {
                img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Schedual_id[i] = new JLabel(rs.getString(1));
            movie_picture[i] = new JLabel(icon);
            movie_picture[i].setBounds(0, 0, 200, 200);

            movie_name[i] = new JLabel("  " + rs.getString(2));
            movie_name[i].setBounds(0, 0 + 200, 200, 35);
            movie_name[i].setFont(new Font("Times new roman", Font.BOLD, 18));

            Schedual_id[i].setVisible(false);
            movie_pnl[i] = new JPanel();
            movie_pnl[i].setLayout(null);
            movie_pnl[i].add(movie_picture[i]);
            movie_pnl[i].add(movie_name[i]);
            movie_pnl[i].add(Schedual_id[i]);
            movie_pnl[i].add(flag[i]);
            movie_pnl[i].setBackground(Color.WHITE);
            movie_pnl[i].setBounds(posX, posY, 200, 235);
            movie_pnl[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            movie_pnl[i].addMouseListener(hnd);
            posX = (posX + 225) % 900;

            if (posX == 100 && i != n - 1) {
                posY = posY + 250;
            }

            innerPanel.add(movie_pnl[i]);
            rs.next();
        }

        innerPanel.setPreferredSize(new Dimension(950, posY + 250));
        scrollPane.add(innerPanel);
        dashboardPnl.add(scrollPane);

    }

    /**
     * Displays the revenue dashboard with ticket sales statistics.
     * RISK MITIGATION: This method performs database aggregation queries (SUM, COUNT)
     * only when explicitly called via button click. It does NOT run automatically
     * on admin panel load/refresh to prevent database performance impact.
     * 
     * @throws SQLException if database query fails
     */
    public void displayRevenueDashboard() throws SQLException {
        innerPanel.removeAll();
        innerPanel.setBackground(Color.BLACK);
        
        // Fetch revenue data only on-demand (button click) - not automatically
        double totalRevenue = dCon.getTotalRevenue();
        int totalTickets = dCon.getTotalTicketsSold();
        
        JLabel revenueTitleLbl = new JLabel("Revenue Dashboard");
        revenueTitleLbl.setBounds(100, 50, 500, 50);
        revenueTitleLbl.setFont(new Font("Times new roman", Font.BOLD, 35));
        revenueTitleLbl.setForeground(Color.WHITE);
        innerPanel.add(revenueTitleLbl);
        
        JLabel totalRevenueLbl = new JLabel("Total Revenue:");
        totalRevenueLbl.setBounds(100, 150, 300, 40);
        totalRevenueLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        totalRevenueLbl.setForeground(Color.WHITE);
        innerPanel.add(totalRevenueLbl);
        
        JLabel totalRevenueValueLbl = new JLabel("Rs. " + String.format("%.2f", totalRevenue));
        totalRevenueValueLbl.setBounds(450, 150, 400, 40);
        totalRevenueValueLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        totalRevenueValueLbl.setForeground(Color.GREEN);
        innerPanel.add(totalRevenueValueLbl);
        
        JLabel totalTicketsLbl = new JLabel("Total Tickets Sold:");
        totalTicketsLbl.setBounds(100, 220, 300, 40);
        totalTicketsLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        totalTicketsLbl.setForeground(Color.WHITE);
        innerPanel.add(totalTicketsLbl);
        
        JLabel totalTicketsValueLbl = new JLabel(String.valueOf(totalTickets));
        totalTicketsValueLbl.setBounds(450, 220, 400, 40);
        totalTicketsValueLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
        totalTicketsValueLbl.setForeground(Color.CYAN);
        innerPanel.add(totalTicketsValueLbl);
        
        if (totalTickets > 0) {
            double averagePrice = totalRevenue / totalTickets;
            JLabel avgPriceLbl = new JLabel("Average Ticket Price:");
            avgPriceLbl.setBounds(100, 290, 300, 40);
            avgPriceLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
            avgPriceLbl.setForeground(Color.WHITE);
            innerPanel.add(avgPriceLbl);
            
            JLabel avgPriceValueLbl = new JLabel("Rs. " + String.format("%.2f", averagePrice));
            avgPriceValueLbl.setBounds(450, 290, 400, 40);
            avgPriceValueLbl.setFont(new Font("Times new roman", Font.BOLD, 25));
            avgPriceValueLbl.setForeground(Color.YELLOW);
            innerPanel.add(avgPriceValueLbl);
        }
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(100, 360, 150, 40);
        refreshBtn.setFont(new Font("Times new roman", Font.BOLD, 18));
        refreshBtn.setBackground(Color.DARK_GRAY);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(hnd);
        innerPanel.add(refreshBtn);
        
        JButton backToHomeBtn = new JButton("Back to Home");
        backToHomeBtn.setBounds(270, 360, 200, 40);
        backToHomeBtn.setFont(new Font("Times new roman", Font.BOLD, 18));
        backToHomeBtn.setBackground(Color.DARK_GRAY);
        backToHomeBtn.setForeground(Color.WHITE);
        backToHomeBtn.setFocusPainted(false);
        backToHomeBtn.setBorderPainted(false);
        backToHomeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backToHomeBtn.addActionListener(hnd);
        innerPanel.add(backToHomeBtn);
        
        innerPanel.setPreferredSize(new Dimension(950, 450));
        scrollPane.add(innerPanel);
        dashboardPnl.add(scrollPane);
    }

    public void setData(String uName) {
        this.uName = uName;
        userNameLbl.setText("       You're signed in as " + this.uName + " (ADMIN)");
    }

}
