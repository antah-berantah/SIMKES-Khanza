package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.config;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

public class DlgSirkulasiBarang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize(); 
    private double ttltotaljual=0,totaljual=0,jumlahjual=0,ttltotalbeli=0,totalbeli=0,jumlahbeli=0,
                   ttltotalpesan=0,totalpesan=0,jumlahpesan=0,
                   ttltotalpiutang=0,totalpiutang=0,jumlahpiutang=0,ttltotalretbeli=0,totalretbeli=0,jumlahretbeli=0,
                   ttltotalretjual=0,totalretjual=0,jumlahretjual=0,ttltotalretpiut=0,totalretpiut=0,jumlahretpiut=0,
                   jumlahpasin=0,totalpasien=0,ttltotalpasien=0,stok=0,aset=0,ttlaset=0;
    private DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6,ps7,ps8,psstok,ps9;
    private ResultSet rs,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rsstok,rs9;

    /** 
     * @param parent
     * @param modal */
    public DlgSirkulasiBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Barang","Nama Barang","Satuan","Stok","Pengadaan","Pemesanan","Penjualan",
                      "Ke Pasien","Piutang Jual","Retur Beli","Retur Jual","Retur Piutang"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());         
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(config.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {prosesCari();}
                @Override
                public void removeUpdate(DocumentEvent e) {prosesCari();}
                @Override
                public void changedUpdate(DocumentEvent e) {prosesCari();}
            });
        }   
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgSirkulasiBarang")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                    }  
                    kdbar.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgSirkulasiBarang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();                    
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
     
    }    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sirkulasi Obat, Alkes & BHP Medis Keluar Masuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal Transaksi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(113, 23));
        panelisi4.add(label11);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl2);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label17);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        btnBarang.setMnemonic('1');
        btnBarang.setToolTipText("Alt+1");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari);

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(79, 30));
        panelisi1.add(label9);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+A");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi1.add(BtnAll);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','"+
                                tabMode.getValueAt(i,10).toString()+"','"+
                                tabMode.getValueAt(i,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Sirkulasi Barang Keluar Masuk"); 
            }
            Sequel.AutoComitTrue();
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.MyReport("rptSirkulasi.jrxml","report","::[ Sirkulasi Barang ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        var.setform("DlgSirkulasiBarang");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdbar.setText("");
        nmbar.setText("");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSirkulasiBarang dialog = new DlgSirkulasiBarang(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBarang;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{   
            ps=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng, "+
                        "kodesatuan.satuan from databarang inner join kodesatuan   "+
                        "on databarang.kode_sat=kodesatuan.kode_sat "+
                        "where databarang.nama_brng like ? and databarang.kode_brng like ? or "+
                        "databarang.nama_brng like ? and databarang.nama_brng like ? or "+
                        "databarang.nama_brng like ? and kodesatuan.satuan like ? "+
                        " order by databarang.kode_brng");
            try {
                ttltotaljual=0;ttltotalbeli=0;ttltotalpesan=0;
                ttltotalpiutang=0;ttltotalretbeli=0;ttltotalretjual=0;
                ttltotalretpiut=0;ttltotalpasien=0;ttlaset=0;
                ps.setString(1,"%"+nmbar.getText()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+nmbar.getText()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+nmbar.getText()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();            
                while(rs.next()){
                    totaljual=0;jumlahjual=0;totalbeli=0;jumlahbeli=0;totalpiutang=0;jumlahpiutang=0;
                    totalpesan=0;jumlahpesan=0;
                    totalretbeli=0;jumlahretbeli=0;totalretjual=0;jumlahretjual=0;totalretpiut=0;jumlahretpiut=0;
                    jumlahpasin=0;stok=0;aset=0;

                    psstok=koneksi.prepareStatement("select sum(stok),(sum(stok)*h_beli) as aset "+
                        "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                        "where gudangbarang.kode_brng=?");
                    try {
                        psstok.setString(1,rs.getString(1));
                        rsstok=psstok.executeQuery();
                        if(rsstok.next()){
                            stok=rsstok.getDouble(1);
                            aset=rsstok.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rsstok!=null){
                            rsstok.close();
                        }
                        if(psstok!=null){
                            psstok.close();
                        }
                    }

                    //pembelian  
                    ps2=koneksi.prepareStatement("select sum(detailbeli.jumlah2), sum(detailbeli.subtotal) "+
                        " from pembelian inner join detailbeli "+
                        " on pembelian.no_faktur=detailbeli.no_faktur "+
                        " where detailbeli.kode_brng=? and pembelian.tgl_beli "+
                        " between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahbeli=rs2.getDouble(1);
                            totalbeli=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }          
                    
                    //pemesanan
                    ps9=koneksi.prepareStatement("select sum(detailpesan.jumlah2), sum(detailpesan.subtotal) "+
                        " from pemesanan inner join detailpesan "+
                        " on pemesanan.no_faktur=detailpesan.no_faktur "+
                        " where detailpesan.kode_brng=? and pemesanan.tgl_pesan "+
                        " between ? and ? ");
                    try {
                        ps9.setString(1,rs.getString(1));
                        ps9.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps9.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs9=ps9.executeQuery();
                        if(rs9.next()){                    
                            jumlahpesan=rs9.getDouble(1);
                            totalpesan=rs9.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Pemesanan : "+e);
                    } finally{
                        if(rs9!=null){
                            rs9.close();
                        }
                        if(ps9!=null){
                            ps9.close();
                        }
                    }
                        
                    //penjualan
                    ps3=koneksi.prepareStatement("select sum(detailjual.jumlah), sum(detailjual.total) "+
                        " from penjualan inner join detailjual "+
                        " on penjualan.nota_jual=detailjual.nota_jual "+
                        " where detailjual.kode_brng=? and "+
                        " penjualan.tgl_jual  between ? and ? ");
                    try {
                        ps3.setString(1,rs.getString(1));
                        ps3.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps3.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs3=ps3.executeQuery();
                        if(rs3.next()){                    
                            jumlahjual=rs3.getDouble(1);
                            totaljual=rs3.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Penjualan : "+e);
                    } finally{
                        if(rs3!=null){
                            rs3.close();
                        }
                        if(ps3!=null){
                            ps3.close();
                        }
                    }

                    //piutang  
                    ps4=koneksi.prepareStatement("select sum(detailpiutang.jumlah), sum(detailpiutang.total) "+
                        " from piutang inner join detailpiutang "+
                        " on piutang.nota_piutang=detailpiutang.nota_piutang "+
                        " where detailpiutang.kode_brng=? and "+
                        " piutang.tgl_piutang between ? and ? ");
                    try {
                        ps4.setString(1,rs.getString(1));
                        ps4.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps4.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs4=ps4.executeQuery();
                        if(rs4.next()){                    
                            jumlahpiutang=rs4.getDouble(1);
                            totalpiutang=rs4.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Piutang : "+e);
                    } finally{
                        if(rs4!=null){
                            rs4.close();
                        }
                        if(ps4!=null){
                            ps4.close();
                        }
                    }

                    //returbeli
                    ps5=koneksi.prepareStatement("select sum(detreturbeli.jml_retur2), sum(detreturbeli.total) "+
                        " from returbeli inner join detreturbeli "+
                        " on returbeli.no_retur_beli=detreturbeli.no_retur_beli "+
                        " where detreturbeli.kode_brng=? and "+
                        " returbeli.tgl_retur between ? and ? ");
                    try {
                        ps5.setString(1,rs.getString(1));
                        ps5.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps5.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs5=ps5.executeQuery();
                        if(rs5.next()){                    
                            jumlahretbeli=rs5.getDouble(1);
                            totalretbeli=rs5.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Retur Beli : "+e);
                    } finally{
                        if(rs5!=null){
                            rs5.close();
                        }
                        if(ps5!=null){
                            ps5.close();
                        }
                    }

                    //returjual
                    ps6=koneksi.prepareStatement("select sum(detreturjual.jml_retur), sum(detreturjual.subtotal) "+
                        " from returjual inner join detreturjual "+
                        " on returjual.no_retur_jual=detreturjual.no_retur_jual "+
                        " where detreturjual.kode_brng=? and "+
                        " returjual.tgl_retur between ? and ? ");
                    try {
                        ps6.setString(1,rs.getString(1));
                        ps6.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps6.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs6=ps6.executeQuery();
                        if(rs6.next()){                    
                            jumlahretjual=rs6.getDouble(1);
                            totalretjual=rs6.getDouble(2);
                        } 
                    } catch (Exception e) {
                        System.out.println("Notifikasi Retur Jual : "+e);
                    } finally{
                        if(rs6!=null){
                            rs6.close();
                        }
                        if(ps6!=null){
                            ps6.close();
                        }
                    }               

                    ps7=koneksi.prepareStatement("select sum(detreturpiutang.jml_retur), sum(detreturpiutang.subtotal) "+
                        " from returpiutang inner join detreturpiutang "+
                        " on returpiutang.no_retur_piutang=detreturpiutang.no_retur_piutang "+
                        " where detreturpiutang.kode_brng=? and "+
                        " returpiutang.tgl_retur between ? and ?");
                    try {
                        ps7.setString(1,rs.getString(1));
                        ps7.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps7.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs7=ps7.executeQuery();
                        if(rs7.next()){                    
                            jumlahretpiut=rs7.getDouble(1);
                            totalretpiut=rs7.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Retur Piutang : "+e);
                    } finally{
                        if(rs7!=null){
                            rs7.close();
                        }
                        if(ps7!=null){
                            ps7.close();
                        }
                    }

                    ps8=koneksi.prepareStatement("select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
                    try {
                        ps8.setString(1,rs.getString(1));
                        ps8.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps8.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs8=ps8.executeQuery();
                        if(rs8.next()){                    
                            jumlahpasin=rs8.getDouble(1);
                            totalpasien=rs8.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Pemberian Obat : "+e);
                    } finally{
                        if(rs8!=null){
                            rs8.close();
                        }
                        if(ps8!=null){
                            ps8.close();
                        }
                    }

                    if((aset>0)||(jumlahbeli>0)||(jumlahpesan>0)||(jumlahjual>0)||(jumlahpasin>0)||(jumlahpiutang>0)||
                            (jumlahretbeli>0)||(jumlahretjual>0)||(jumlahretpiut>0)){
                        tabMode.addRow(new Object[]{rs.getString(1),rs.getString(2),
                                   rs.getString(3),Valid.SetAngka(stok)+" ("+Valid.SetAngka(aset)+")",
                                   Valid.SetAngka(jumlahbeli)+" ("+Valid.SetAngka(totalbeli)+")",
                                   Valid.SetAngka(jumlahpesan)+" ("+Valid.SetAngka(totalpesan)+")",
                                   Valid.SetAngka(jumlahjual)+" ("+Valid.SetAngka(totaljual)+")",
                                   Valid.SetAngka(jumlahpasin)+" ("+Valid.SetAngka(totalpasien)+")",
                                   Valid.SetAngka(jumlahpiutang)+" ("+Valid.SetAngka(totalpiutang)+")",
                                   Valid.SetAngka(jumlahretbeli)+" ("+Valid.SetAngka(totalretbeli)+")",
                                   Valid.SetAngka(jumlahretjual)+" ("+Valid.SetAngka(totalretjual)+")",
                                   Valid.SetAngka(jumlahretpiut)+" ("+Valid.SetAngka(totalretpiut)+")"
                                  }); 
                    }

                    ttltotalbeli=ttltotalbeli+totalbeli;
                    ttltotalpesan=ttltotalpesan+totalpesan;
                    ttltotaljual=ttltotaljual+totaljual;
                    ttlaset=ttlaset+aset;
                    ttltotalpasien=ttltotalpasien+totalpasien;
                    ttltotalpiutang=ttltotalpiutang+totalpiutang;
                    ttltotalretbeli=ttltotalretbeli+totalretbeli;
                    ttltotalretjual=ttltotalretjual+totalretjual;
                    ttltotalretpiut=ttltotalretpiut+totalretpiut;
                }   
                tabMode.addRow(new Object[]{"","","","","","","","","","","",""}); 
                tabMode.addRow(new Object[]{"<>>","Total :","",Valid.SetAngka(ttlaset),
                                   Valid.SetAngka(ttltotalbeli),Valid.SetAngka(ttltotalpesan),
                                   Valid.SetAngka(ttltotaljual),Valid.SetAngka(ttltotalpasien),
                                   Valid.SetAngka(ttltotalpiutang),Valid.SetAngka(ttltotalretbeli),
                                   Valid.SetAngka(ttltotalretjual),Valid.SetAngka(ttltotalretpiut)
                                  }); 
            } catch (Exception e) {
                System.out.println("Notifikasi Data Barang : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    public void isCek(){
         BtnPrint.setEnabled(var.getmanajemen());
    }
    
}
