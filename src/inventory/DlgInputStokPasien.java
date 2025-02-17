package inventory;
import fungsi.WarnaTable2;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgInputStokPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement pstampil,psstok;
    private ResultSet rstampil,rsstok;
    private WarnaTable2 warna=new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private DecimalFormat df3 = new DecimalFormat("###");
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private double ttl=0,y=0,stokbarang=0;
    private int jml=0,i=0,index=0;
    private String[] keranap,kodebarang,namabarang,kategori,satuan,kapasitas,stok,harga;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgInputStokPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Ke Ranap",
                    "Kode Barang",
                    "Nama Barang",
                    "Kategori",
                    "Satuan",
                    "Kapasitas",
                    "Stok",
                    "Harga"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(60);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(280);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(80);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        kdgudang.setDocument(new batasInput((byte)5).getKata(kdgudang));
        catatan.setDocument(new batasInput((byte)60).getKata(catatan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(config.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        }

        TCari.requestFocus();

        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){
                    kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }
                kdgudang.requestFocus();
                tampil();
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
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        kelas = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label18 = new widget.Label();
        catatan = new widget.TextBox();
        label11 = new widget.Label();
        Tgl = new widget.Tanggal();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        label22 = new widget.Label();
        norawat = new widget.TextBox();
        nm_pasien = new widget.TextBox();
        panelisi1 = new widget.panelisi();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        label10 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(102, 51, 0));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        kelas.setHighlighter(null);
        kelas.setName("kelas"); // NOI18N
        kelas.setSelectionColor(new java.awt.Color(255, 255, 255));
        kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Stok Obat & BHP Medis Pasien Di Ranap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
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
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(89, 74));
        panelisi3.setLayout(null);

        label18.setText("Keterangan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 80, 23);

        catatan.setName("catatan"); // NOI18N
        catatan.setPreferredSize(new java.awt.Dimension(207, 23));
        catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catatanKeyPressed(evt);
            }
        });
        panelisi3.add(catatan);
        catatan.setBounds(84, 40, 150, 23);

        label11.setText("Tanggal Stok :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 10, 80, 23);

        Tgl.setEditable(false);
        Tgl.setDisplayFormat("dd-MM-yyyy");
        Tgl.setName("Tgl"); // NOI18N
        Tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKeyPressed(evt);
            }
        });
        panelisi3.add(Tgl);
        Tgl.setBounds(84, 10, 150, 23);

        label21.setText("Asal Stok :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label21);
        label21.setBounds(276, 10, 90, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(369, 10, 101, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(472, 10, 300, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisi3.add(BtnGudang);
        BtnGudang.setBounds(774, 10, 28, 23);

        label22.setText("No.Rawat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label22);
        label22.setBounds(276, 40, 90, 23);

        norawat.setName("norawat"); // NOI18N
        norawat.setPreferredSize(new java.awt.Dimension(80, 23));
        norawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatKeyPressed(evt);
            }
        });
        panelisi3.add(norawat);
        norawat.setBounds(369, 40, 101, 23);

        nm_pasien.setEditable(false);
        nm_pasien.setName("nm_pasien"); // NOI18N
        nm_pasien.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nm_pasien);
        nm_pasien.setBounds(472, 40, 300, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi1.add(label12);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP", "Beli Luar", "Karyawan" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(100, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        panelisi1.add(Jeniskelas);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(25, 23));
        panelisi1.add(label10);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelisi1.add(BtnSimpan);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+C");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnCari,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(nmgudang.getText().trim().equals("")||kdgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Gudang");
        }else if(catatan.getText().trim().equals("")){
            Valid.textKosong(catatan,"Keterangan");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kosong..!!!!");
            tbDokter.requestFocus();
        }else{
            i= JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                for(i=0;i<tbDokter.getRowCount();i++){
                   if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                        if(Sequel.menyimpantf("stok_obat_pasien","?,?,?,?,?","Stok Obat Pasien",5,new String[]{
                            Valid.SetTgl(Tgl.getSelectedItem()+""),norawat.getText(),tabMode.getValueAt(i,1).toString(),
                            tabMode.getValueAt(i,0).toString(),kdgudang.getText()
                        })==true){
                            Trackobat.catatRiwayat(tabMode.getValueAt(i,1).toString(),0,Valid.SetAngka(tabMode.getValueAt(i,0).toString()),"Stok Pasien Ranap",var.getkode(),kdgudang.getText(),"Simpan");
                            Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tabMode.getValueAt(i,0).toString()+"'",
                                            "stok=stok-'"+tabMode.getValueAt(i,0).toString()+"'","kode_brng='"+tabMode.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");
                        }
                   }
                }
                for(index=0;index<tbDokter.getRowCount();index++){
                    tbDokter.setValueAt("",index,0);
                }
                tampil();
            }

        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,kdgudang,BtnCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, TCari, BtnSimpan);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catatanKeyPressed
        Valid.pindah(evt, kdgudang,BtnSimpan);
}//GEN-LAST:event_catatanKeyPressed

private void TglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKeyPressed
        Valid.pindah(evt,TCari,kdgudang);
}//GEN-LAST:event_TglKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tbDokter.getRowCount();
            for(int r=0;r<row2;r++){
                tbDokter.setValueAt("",r,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        tampil();
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        tampil();
        Tgl.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        tampil();
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        BtnGudangActionPerformed(null);
    }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    bangsal.isCek();
    bangsal.emptTeks();
    bangsal.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgStokPasien opname=new DlgStokPasien(null,false);
        opname.isCek();
        opname.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        opname.setLocationRelativeTo(internalFrame1);
        opname.setAlwaysOnTop(false);
        opname.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void norawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_norawatKeyPressed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    getData();
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                int row=tbDokter.getSelectedRow();
                if(row!= -1){
                    tabMode.setValueAt("", row,0);
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
        tampil();
    }//GEN-LAST:event_JeniskelasItemStateChanged

    private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
        Valid.pindah(evt, norawat,TCari);
    }//GEN-LAST:event_JeniskelasKeyPressed

    private void kelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgInputStokPasien dialog = new DlgInputStokPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnGudang;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox Kd2;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl;
    private widget.TextBox catatan;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdgudang;
    private widget.TextBox kelas;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label9;
    private widget.TextBox nm_pasien;
    private widget.TextBox nmgudang;
    private widget.TextBox norawat;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        jml=0;
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        keranap=null;
        keranap=new String[jml];
        kodebarang=null;
        kodebarang=new String[jml];
        namabarang=null;
        namabarang=new String[jml];
        kategori=null;
        kategori=new String[jml];
        satuan=null;
        satuan=new String[jml];
        kapasitas=null;
        kapasitas=new String[jml];
        stok=null;
        stok=new String[jml];
        harga=null;
        harga=new String[jml];

        index=0;
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                keranap[index]=tbDokter.getValueAt(i,0).toString();
                kodebarang[index]=tbDokter.getValueAt(i,1).toString();
                namabarang[index]=tbDokter.getValueAt(i,2).toString();
                kategori[index]=tbDokter.getValueAt(i,3).toString();
                satuan[index]=tbDokter.getValueAt(i,4).toString();
                kapasitas[index]=tbDokter.getValueAt(i,5).toString();
                stok[index]=tbDokter.getValueAt(i,6).toString();
                harga[index]=tbDokter.getValueAt(i,7).toString();
                index++;
            }
        }

        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new String[]{keranap[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],kapasitas[i],stok[i],harga[i]});
        }
        try{
            pstampil=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama,"+
                    " databarang.kapasitas,databarang.kode_sat,databarang.kelas1,"+
                    " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,"+
                    " databarang.vvip,databarang.beliluar,databarang.karyawan  "+
                    " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " where databarang.status='1' and databarang.kode_brng like ? or "+
                    " databarang.status='1' and databarang.nama_brng like ? or "+
                    " databarang.status='1' and databarang.kode_sat like ? or "+
                    " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            try{
                pstampil.setString(1,"%"+TCari.getText().trim()+"%");
                pstampil.setString(2,"%"+TCari.getText().trim()+"%");
                pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                rstampil=pstampil.executeQuery();
                while(rstampil.next()){
                    stokbarang=0;
                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                    try {
                        psstok.setString(1,kdgudang.getText());
                        psstok.setString(2,rstampil.getString("kode_brng"));
                        rsstok=psstok.executeQuery();
                        if(rsstok.next()){
                            stokbarang=rsstok.getDouble(1);
                        }
                    } catch (Exception e) {
                        stokbarang=0;
                        System.out.println("Notif Stok : "+e);
                    } finally{
                        if(rsstok!=null){
                            rsstok.close();
                        }
                        if(psstok!=null){
                            psstok.close();
                        }
                    }

                    if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getString("kapasitas"),stokbarang,rstampil.getString("kelas1")});
                    }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getString("kapasitas"),stokbarang,rstampil.getString("kelas2")});
                    }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getString("kapasitas"),stokbarang,rstampil.getString("kelas3")});
                    }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getString("kapasitas"),stokbarang,rstampil.getString("utama")});
                    }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getString("kapasitas"),stokbarang,rstampil.getString("vip")});
                    }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getString("kapasitas"),stokbarang,rstampil.getString("vvip")});
                    }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getString("kapasitas"),stokbarang,rstampil.getString("beliluar")});
                    }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getString("kapasitas"),stokbarang,rstampil.getString("karyawan")});
                    }
                }
            }catch(Exception e){
                System.out.println("Notif Data Barang : "+e);
            }finally{
                if(rstampil!=null){
                    rstampil.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }

    }

    public void isCek(){
         BtnSimpan.setEnabled(var.getmanajemen());
    }

    public void setNoRm(String no_rawat,String pasien){
        norawat.setText(no_rawat);
        nm_pasien.setText(pasien);
        kelas.setText(Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "+
                "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",no_rawat));
        if(kelas.getText().equals("Kelas 1")){
            Jeniskelas.setSelectedItem("Kelas 1");
        }else if(kelas.getText().equals("Kelas 2")){
            Jeniskelas.setSelectedItem("Kelas 2");
        }else if(kelas.getText().equals("Kelas 3")){
            Jeniskelas.setSelectedItem("Kelas 3");
        }else if(kelas.getText().equals("Kelas Utama")){
            Jeniskelas.setSelectedItem("Utama/BPJS");
        }else if(kelas.getText().equals("Kelas VIP")){
            Jeniskelas.setSelectedItem("VIP");
        }else if(kelas.getText().equals("Kelas VVIP")){
            Jeniskelas.setSelectedItem("VVIP");
        }
    }

    private void getData() {
        int row=tbDokter.getSelectedRow();
        if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Asal Stok");
        }else if(row!= -1){
            int kolom=tbDokter.getSelectedColumn();
            if((kolom==1)){
               if(!tabMode.getValueAt(row,0).toString().equals("")){
                   if(Double.parseDouble(tabMode.getValueAt(row,0).toString())>Double.parseDouble(tabMode.getValueAt(row,6).toString())){
                        JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                        TCari.requestFocus();
                        tabMode.setValueAt("", row,0);
                   }
                }
            }
        }
    }




}
