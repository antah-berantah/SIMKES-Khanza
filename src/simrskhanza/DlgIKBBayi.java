package simrskhanza;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgIKBBayi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Date lahir;
    private LocalDate today=LocalDate.now();
    private LocalDate birthday;
    private Period p;
    private long p2;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private String pengurutan="",bulan="",tahun="",awalantahun="",awalanbulan="",posisitahun="",
            nokk="",nmbayi="",tgllhr="",jamlhr="",jk="",jnslhr="",lahirke="",brt="",
            pjg="",pnlglhr="",nikibu="",nmibu="",alamatibu="",kerjaibu="",nikayah="",
            nmayah="",alamatayah="",kerjaayah="",noskl="",pnlgnama="",tindaklhr="",
            bpjsibu="",bpjsayah="",notlp="",bpjsby="",nikplpr="",nmplpr="",almtplpr="",
            krjplpr="",niks1="",nms1="",almts1="",krjs1="",niks2="",nms2="",almts2="",
            krjs2="",umribu="",umrayah="",umrplpr="",umrs1="",umrs2="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgIKBBayi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.RM","Nama Anak/Bayi","J.K","Tgl.Lahir","Jam Lahir",
            "Umur","Tgl.Daftar","Nama Ibu","Umur Ibu","Nama Ayah",
            "Umur Ayah","Alamat Ibu","Berat Bayi","Panjang Badan",
            "Lk.Kepala","Proses Lahir","Kelahiran Ke","Keterangan",
            "Diagnosa","Penyulit Kehamilan","Ketuban","Lk.Perut",
            "Lk.Dada","Penolong","No.SKL"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 25; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(30);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(60);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(70);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(100);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(100);
            }else if(i==21){
                column.setPreferredWidth(60);
            }else if(i==22){
                column.setPreferredWidth(60);
            }else if(i==23){
                column.setPreferredWidth(150);
            }else if(i==24){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoRm.setDocument(new batasInput((byte)15).getKata(NoRm));
        NmBayi.setDocument(new batasInput((byte)40).getKata(NmBayi));
        AlamatIbu.setDocument(new batasInput((int)200).getKata(AlamatIbu));
        Nmibu.setDocument(new batasInput((byte)50).getKata(Nmibu));
        UmurIbu.setDocument(new batasInput((byte)8).getKata(UmurIbu));
        NmAyah.setDocument(new batasInput((byte)50).getKata(NmAyah));
        UmurAyah.setDocument(new batasInput((byte)8).getKata(UmurAyah));
        Proses.setDocument(new batasInput((byte)60).getKata(Proses));
        Diagnosa.setDocument(new batasInput((byte)60).getKata(Diagnosa));
        Ketuban.setDocument(new batasInput((byte)60).getKata(Ketuban));
        PenyulitKehamilan.setDocument(new batasInput((byte)60).getKata(PenyulitKehamilan));
        Berat.setDocument(new batasInput((byte)10).getOnlyAngka(Berat));
        Panjang.setDocument(new batasInput((byte)10).getKata(Panjang));
        LingkarKepala.setDocument(new batasInput((byte)10).getKata(LingkarKepala));
        LingkarPerut.setDocument(new batasInput((byte)10).getKata(LingkarPerut));
        LingkarDada.setDocument(new batasInput((byte)10).getKata(LingkarDada));
        keterangan.setDocument(new batasInput((byte)50).getKata(keterangan));
        NoSKL.setDocument(new batasInput((byte)30).getKata(NoSKL));
        Anakke.setDocument(new batasInput((byte)2).getOnlyAngka(Anakke));
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(config.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        } 
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){                   
                    KdPenolong.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                    NmPenolong.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
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
        ChkInput.setSelected(false);
        isForm();
        
        pengurutan=Sequel.cariIsi("select urutan from set_urut_no_rkm_medis");
        tahun=Sequel.cariIsi("select tahun from set_urut_no_rkm_medis");
        bulan=Sequel.cariIsi("select bulan from set_urut_no_rkm_medis");
        posisitahun=Sequel.cariIsi("select posisi_tahun_bulan from set_urut_no_rkm_medis");
    }
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private String[] hlm;
    private String jkelcari="",tglcari="";
    /*
    private DlgIbu ibu=new DlgIbu(null,false);
    private DlgInapBayi ranap=new DlgInapBayi(null,false);
    private DlgJalanBayi ralan=new DlgJalanBayi(null,false);*/

    /** This method is called from within the constructor to
     * initialize the forem.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        MnKartu = new javax.swing.JMenuItem();
        MnInformasiBayi = new javax.swing.JMenuItem();
        MnSKL = new javax.swing.JMenuItem();
        MnSKL1 = new javax.swing.JMenuItem();
        MnSKL2 = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        label29 = new widget.Label();
        cmbCrJk = new widget.ComboBox();
        ckTglCari = new widget.CekBox();
        DTPCari1 = new widget.Tanggal();
        label33 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        NoRm = new widget.TextBox();
        label18 = new widget.Label();
        label22 = new widget.Label();
        label24 = new widget.Label();
        Proses = new widget.TextBox();
        Anakke = new widget.TextBox();
        label25 = new widget.Label();
        LingkarKepala = new widget.TextBox();
        label27 = new widget.Label();
        label28 = new widget.Label();
        JKel = new widget.ComboBox();
        label23 = new widget.Label();
        label30 = new widget.Label();
        Lahir = new widget.Tanggal();
        label31 = new widget.Label();
        Diagnosa = new widget.TextBox();
        Nmibu = new widget.TextBox();
        label26 = new widget.Label();
        label19 = new widget.Label();
        NmAyah = new widget.TextBox();
        label20 = new widget.Label();
        UmurIbu = new widget.TextBox();
        label21 = new widget.Label();
        AlamatIbu = new widget.TextBox();
        label32 = new widget.Label();
        jam = new widget.ComboBox();
        menit = new widget.ComboBox();
        detik = new widget.ComboBox();
        label34 = new widget.Label();
        Berat = new widget.TextBox();
        Panjang = new widget.TextBox();
        Daftar = new widget.Tanggal();
        scrollPane2 = new widget.ScrollPane();
        keterangan = new widget.TextArea();
        NmBayi = new widget.TextBox();
        label35 = new widget.Label();
        UmurAyah = new widget.TextBox();
        label36 = new widget.Label();
        UmurBayi = new widget.TextBox();
        label37 = new widget.Label();
        PenyulitKehamilan = new widget.TextBox();
        label38 = new widget.Label();
        Ketuban = new widget.TextBox();
        label39 = new widget.Label();
        LingkarDada = new widget.TextBox();
        LingkarPerut = new widget.TextBox();
        label40 = new widget.Label();
        label41 = new widget.Label();
        NoSKL = new widget.TextBox();
        jLabel24 = new widget.Label();
        KdPenolong = new widget.TextBox();
        NmPenolong = new widget.TextBox();
        BtnPenjab = new widget.Button();
        BtnKelurahan1 = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        MnKartu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu.setForeground(new java.awt.Color(70, 70, 70));
        MnKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnKartu.setText("Kartu Berobat");
        MnKartu.setIconTextGap(5);
        MnKartu.setName("MnKartu"); // NOI18N
        MnKartu.setPreferredSize(new java.awt.Dimension(250, 25));
        MnKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartuActionPerformed(evt);
            }
        });
        Popup.add(MnKartu);

        MnInformasiBayi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInformasiBayi.setForeground(new java.awt.Color(70, 70, 70));
        MnInformasiBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnInformasiBayi.setText("Label Informasi Bayi");
        MnInformasiBayi.setIconTextGap(5);
        MnInformasiBayi.setName("MnInformasiBayi"); // NOI18N
        MnInformasiBayi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnInformasiBayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInformasiBayiActionPerformed(evt);
            }
        });
        Popup.add(MnInformasiBayi);

        MnSKL.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSKL.setForeground(new java.awt.Color(70, 70, 70));
        MnSKL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSKL.setText("Surat Keterangan Lahir 1");
        MnSKL.setIconTextGap(5);
        MnSKL.setName("MnSKL"); // NOI18N
        MnSKL.setPreferredSize(new java.awt.Dimension(250, 28));
        MnSKL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSKLActionPerformed(evt);
            }
        });
        Popup.add(MnSKL);

        MnSKL1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSKL1.setForeground(new java.awt.Color(70, 70, 70));
        MnSKL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSKL1.setText("Surat Keterangan Lahir 2");
        MnSKL1.setIconTextGap(5);
        MnSKL1.setName("MnSKL1"); // NOI18N
        MnSKL1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnSKL1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSKL1ActionPerformed(evt);
            }
        });
        Popup.add(MnSKL1);

        MnSKL2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSKL2.setForeground(new java.awt.Color(70, 70, 70));
        MnSKL2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSKL2.setText("Surat Keterangan Lahir 3");
        MnSKL2.setIconTextGap(5);
        MnSKL2.setName("MnSKL2"); // NOI18N
        MnSKL2.setPreferredSize(new java.awt.Dimension(250, 28));
        MnSKL2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSKL2ActionPerformed(evt);
            }
        });
        Popup.add(MnSKL2);

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Bayi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label29.setText("J.K. :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi2.add(label29);

        cmbCrJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SEMUA", "LAKI-LAKI", "PEREMPUAN" }));
        cmbCrJk.setName("cmbCrJk"); // NOI18N
        cmbCrJk.setPreferredSize(new java.awt.Dimension(120, 23));
        cmbCrJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbCrJkKeyPressed(evt);
            }
        });
        panelisi2.add(cmbCrJk);

        ckTglCari.setSelected(true);
        ckTglCari.setText("Tgl.Lahir :");
        ckTglCari.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ckTglCari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ckTglCari.setName("ckTglCari"); // NOI18N
        ckTglCari.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(ckTglCari);

        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelisi2.add(DTPCari1);

        label33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label33.setText("S.D.");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(27, 23));
        panelisi2.add(label33);

        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelisi2.add(DTPCari2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(155, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi2.add(BtnCari);

        jLabel7.setText("Halaman :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi2.add(jLabel7);

        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi2.add(cmbHlm);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed1(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed1(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelisi1.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+PT");
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelisi1.add(LCount);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 279));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(560, 208));
        FormInput.setLayout(null);

        label12.setText("No.RM Bayi :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label12);
        label12.setBounds(0, 12, 85, 23);

        NoRm.setName("NoRm"); // NOI18N
        NoRm.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmKeyPressed(evt);
            }
        });
        FormInput.add(NoRm);
        NoRm.setBounds(89, 12, 100, 23);

        label18.setText("Ibu Bayi :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label18);
        label18.setBounds(0, 42, 85, 23);

        label22.setText("J.K.Bayi :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label22);
        label22.setBounds(0, 132, 85, 23);

        label24.setText("Panjang Badan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label24);
        label24.setBounds(475, 12, 98, 23);

        Proses.setName("Proses"); // NOI18N
        Proses.setPreferredSize(new java.awt.Dimension(207, 23));
        Proses.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsesKeyPressed(evt);
            }
        });
        FormInput.add(Proses);
        Proses.setBounds(577, 222, 300, 23);

        Anakke.setName("Anakke"); // NOI18N
        Anakke.setPreferredSize(new java.awt.Dimension(207, 23));
        Anakke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnakkeKeyPressed(evt);
            }
        });
        FormInput.add(Anakke);
        Anakke.setBounds(782, 72, 95, 23);

        label25.setText("Kelahiran Ke :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label25);
        label25.setBounds(679, 72, 100, 23);

        LingkarKepala.setName("LingkarKepala"); // NOI18N
        LingkarKepala.setPreferredSize(new java.awt.Dimension(207, 23));
        LingkarKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarKepalaKeyPressed(evt);
            }
        });
        FormInput.add(LingkarKepala);
        LingkarKepala.setBounds(782, 12, 95, 23);

        label27.setText("Keterangan :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label27);
        label27.setBounds(475, 102, 98, 23);

        label28.setText("Tgl.Daftar :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label28);
        label28.setBounds(475, 72, 98, 23);

        JKel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        JKel.setName("JKel"); // NOI18N
        JKel.setPreferredSize(new java.awt.Dimension(100, 23));
        JKel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKelKeyPressed(evt);
            }
        });
        FormInput.add(JKel);
        JKel.setBounds(89, 132, 130, 23);

        label23.setText("Berat Bayi(gram) :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label23);
        label23.setBounds(245, 132, 117, 23);

        label30.setText("Tgl. Lahir :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label30);
        label30.setBounds(0, 162, 85, 23);

        Lahir.setDisplayFormat("dd-MM-yyyy");
        Lahir.setName("Lahir"); // NOI18N
        Lahir.setVerifyInputWhenFocusTarget(false);
        Lahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LahirItemStateChanged(evt);
            }
        });
        Lahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LahirKeyPressed(evt);
            }
        });
        FormInput.add(Lahir);
        Lahir.setBounds(89, 162, 95, 23);

        label31.setText("Diagnosa :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label31);
        label31.setBounds(475, 162, 98, 23);

        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.setPreferredSize(new java.awt.Dimension(207, 23));
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(577, 162, 120, 23);

        Nmibu.setName("Nmibu"); // NOI18N
        Nmibu.setPreferredSize(new java.awt.Dimension(207, 23));
        Nmibu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmibuKeyPressed(evt);
            }
        });
        FormInput.add(Nmibu);
        Nmibu.setBounds(89, 42, 230, 23);

        label26.setText("Lingkar Kepala :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label26);
        label26.setBounds(679, 12, 100, 23);

        label19.setText("Nama Ayah :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label19);
        label19.setBounds(0, 72, 85, 23);

        NmAyah.setName("NmAyah"); // NOI18N
        NmAyah.setPreferredSize(new java.awt.Dimension(207, 23));
        NmAyah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmAyahKeyPressed(evt);
            }
        });
        FormInput.add(NmAyah);
        NmAyah.setBounds(89, 72, 230, 23);

        label20.setText("Umur Ibu :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label20);
        label20.setBounds(292, 42, 100, 23);

        UmurIbu.setName("UmurIbu"); // NOI18N
        UmurIbu.setPreferredSize(new java.awt.Dimension(207, 23));
        UmurIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurIbuKeyPressed(evt);
            }
        });
        FormInput.add(UmurIbu);
        UmurIbu.setBounds(395, 42, 70, 23);

        label21.setText("Alamat Ibu :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label21);
        label21.setBounds(0, 102, 85, 23);

        AlamatIbu.setName("AlamatIbu"); // NOI18N
        AlamatIbu.setPreferredSize(new java.awt.Dimension(207, 23));
        AlamatIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatIbuKeyPressed(evt);
            }
        });
        FormInput.add(AlamatIbu);
        AlamatIbu.setBounds(89, 102, 376, 23);

        label32.setText("Jam Lahir :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label32);
        label32.setBounds(189, 162, 80, 23);

        jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jam.setName("jam"); // NOI18N
        jam.setPreferredSize(new java.awt.Dimension(100, 23));
        jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jamKeyPressed(evt);
            }
        });
        FormInput.add(jam);
        jam.setBounds(273, 162, 62, 23);

        menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        menit.setName("menit"); // NOI18N
        menit.setPreferredSize(new java.awt.Dimension(100, 23));
        menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                menitKeyPressed(evt);
            }
        });
        FormInput.add(menit);
        menit.setBounds(338, 162, 62, 23);

        detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        detik.setName("detik"); // NOI18N
        detik.setPreferredSize(new java.awt.Dimension(100, 23));
        detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                detikKeyPressed(evt);
            }
        });
        FormInput.add(detik);
        detik.setBounds(403, 162, 62, 23);

        label34.setText("Proses Kelahiran :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label34);
        label34.setBounds(475, 222, 98, 23);

        Berat.setName("Berat"); // NOI18N
        Berat.setPreferredSize(new java.awt.Dimension(207, 23));
        Berat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratKeyPressed(evt);
            }
        });
        FormInput.add(Berat);
        Berat.setBounds(365, 132, 100, 23);

        Panjang.setName("Panjang"); // NOI18N
        Panjang.setPreferredSize(new java.awt.Dimension(207, 23));
        Panjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangKeyPressed(evt);
            }
        });
        FormInput.add(Panjang);
        Panjang.setBounds(577, 12, 95, 23);

        Daftar.setDisplayFormat("dd-MM-yyyy");
        Daftar.setName("Daftar"); // NOI18N
        Daftar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DaftarKeyPressed(evt);
            }
        });
        FormInput.add(Daftar);
        Daftar.setBounds(577, 72, 95, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        keterangan.setColumns(20);
        keterangan.setRows(5);
        keterangan.setName("keterangan"); // NOI18N
        keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keteranganKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(keterangan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(577, 102, 300, 52);

        NmBayi.setName("NmBayi"); // NOI18N
        NmBayi.setPreferredSize(new java.awt.Dimension(207, 23));
        NmBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmBayiKeyPressed(evt);
            }
        });
        FormInput.add(NmBayi);
        NmBayi.setBounds(191, 12, 244, 23);

        label35.setText("Umur Ayah :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label35);
        label35.setBounds(292, 72, 100, 23);

        UmurAyah.setName("UmurAyah"); // NOI18N
        UmurAyah.setPreferredSize(new java.awt.Dimension(207, 23));
        UmurAyah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurAyahKeyPressed(evt);
            }
        });
        FormInput.add(UmurAyah);
        UmurAyah.setBounds(395, 72, 70, 23);

        label36.setText("Umur Bayi :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label36);
        label36.setBounds(475, 42, 98, 23);

        UmurBayi.setName("UmurBayi"); // NOI18N
        UmurBayi.setPreferredSize(new java.awt.Dimension(207, 23));
        UmurBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurBayiKeyPressed(evt);
            }
        });
        FormInput.add(UmurBayi);
        UmurBayi.setBounds(577, 42, 95, 23);

        label37.setText("Penyulit Kehamilan :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label37);
        label37.setBounds(473, 192, 100, 23);

        PenyulitKehamilan.setName("PenyulitKehamilan"); // NOI18N
        PenyulitKehamilan.setPreferredSize(new java.awt.Dimension(207, 23));
        PenyulitKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyulitKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(PenyulitKehamilan);
        PenyulitKehamilan.setBounds(577, 192, 300, 23);

        label38.setText("Ketuban :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label38);
        label38.setBounds(697, 162, 57, 23);

        Ketuban.setName("Ketuban"); // NOI18N
        Ketuban.setPreferredSize(new java.awt.Dimension(207, 23));
        Ketuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetubanKeyPressed(evt);
            }
        });
        FormInput.add(Ketuban);
        Ketuban.setBounds(757, 162, 120, 23);

        label39.setText("Lingkar Dada :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label39);
        label39.setBounds(679, 42, 100, 23);

        LingkarDada.setName("LingkarDada"); // NOI18N
        LingkarDada.setPreferredSize(new java.awt.Dimension(207, 23));
        LingkarDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarDadaKeyPressed(evt);
            }
        });
        FormInput.add(LingkarDada);
        LingkarDada.setBounds(782, 42, 95, 23);

        LingkarPerut.setName("LingkarPerut"); // NOI18N
        LingkarPerut.setPreferredSize(new java.awt.Dimension(207, 23));
        LingkarPerut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarPerutKeyPressed(evt);
            }
        });
        FormInput.add(LingkarPerut);
        LingkarPerut.setBounds(370, 222, 95, 23);

        label40.setText("Lingkar Perut :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label40);
        label40.setBounds(280, 222, 85, 23);

        label41.setText("No.SKL :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label41);
        label41.setBounds(0, 222, 85, 23);

        NoSKL.setName("NoSKL"); // NOI18N
        NoSKL.setPreferredSize(new java.awt.Dimension(207, 23));
        NoSKL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSKLKeyPressed(evt);
            }
        });
        FormInput.add(NoSKL);
        NoSKL.setBounds(90, 222, 160, 23);

        jLabel24.setText("Penolong :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 192, 85, 23);

        KdPenolong.setHighlighter(null);
        KdPenolong.setName("KdPenolong"); // NOI18N
        KdPenolong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPenolongKeyPressed(evt);
            }
        });
        FormInput.add(KdPenolong);
        KdPenolong.setBounds(90, 192, 100, 23);

        NmPenolong.setEditable(false);
        NmPenolong.setName("NmPenolong"); // NOI18N
        FormInput.add(NmPenolong);
        NmPenolong.setBounds(192, 192, 241, 23);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('1');
        BtnPenjab.setToolTipText("ALt+1");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        FormInput.add(BtnPenjab);
        BtnPenjab.setBounds(437, 192, 28, 23);

        BtnKelurahan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/011.png"))); // NOI18N
        BtnKelurahan1.setMnemonic('2');
        BtnKelurahan1.setToolTipText("ALt+2");
        BtnKelurahan1.setName("BtnKelurahan1"); // NOI18N
        BtnKelurahan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahan1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnKelurahan1);
        BtnKelurahan1.setBounds(437, 12, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        //Valid.pindah(evt,BtnClose,no_rm_ibu);
}//GEN-LAST:event_KdKeyPressed

    private void ProsesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsesKeyPressed
         Valid.pindah(evt,PenyulitKehamilan,BtnSimpan);
    }//GEN-LAST:event_ProsesKeyPressed

    private void AnakkeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnakkeKeyPressed
        Valid.pindah(evt,Daftar,keterangan);
    }//GEN-LAST:event_AnakkeKeyPressed


    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            jkelcari=""; tglcari="";
            if(! cmbCrJk.getSelectedItem().toString().equals("SEMUA")){
                jkelcari=" pasien.jk='"+cmbCrJk.getSelectedItem().toString().substring(0,1)+"' and ";
            }

            if(ckTglCari.isSelected()==true){
                    tglcari=" pasien.tgl_lahir between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and ";
            }  
            
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 

            String sql="select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, "+
                   "pasien.tgl_lahir,pasien_bayi.jam_lahir, pasien.umur, "+
                   "pasien.tgl_daftar,pasien.nm_ibu,pasien_bayi.umur_ibu, "+
                   "pasien_bayi.nama_ayah,pasien_bayi.umur_ayah,"+
                   "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "+
                   "pasien_bayi.berat_badan,pasien_bayi.panjang_badan, pasien_bayi.lingkar_kepala, "+
                   "pasien_bayi.proses_lahir,pasien_bayi.anakke, pasien_bayi.keterangan, "+
                   "pasien_bayi.diagnosa,pasien_bayi.penyulit_kehamilan,pasien_bayi.ketuban,"+
                   "pasien_bayi.lingkar_perut,pasien_bayi.lingkar_dada,pegawai.nama,"+
                   "pasien_bayi.no_skl from pasien inner join pegawai inner join pasien_bayi "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.no_rkm_medis=pasien_bayi.no_rkm_medis and pasien_bayi.penolong=pegawai.nik "+
                   "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                   "where "+jkelcari+tglcari+" pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.tgl_lahir like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.namakeluarga like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.alamat like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pegawai.nama like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien_bayi.diagnosa like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.nm_ibu like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien_bayi.proses_lahir like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien_bayi.penyulit_kehamilan like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien_bayi.ketuban like '%"+TCari.getText().trim()+"%'  order by pasien.no_rkm_medis desc";               
                Valid.MyReport("rptPasienbayi.jrxml","report","::[ Data Bayi ]::",sql,param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnEdit,BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();  
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void LingkarKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarKepalaKeyPressed
         Valid.pindah(evt,Panjang,UmurBayi);
    }//GEN-LAST:event_LingkarKepalaKeyPressed

private void JKelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKelKeyPressed
        Valid.pindah(evt,AlamatIbu,Berat);
}//GEN-LAST:event_JKelKeyPressed

private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah(evt,keterangan,Ketuban);
}//GEN-LAST:event_DiagnosaKeyPressed

private void NoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRmKeyPressed
    Valid.pindah(evt,TCari,NmBayi);
}//GEN-LAST:event_NoRmKeyPressed

private void LahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LahirKeyPressed
   Valid.pindah(evt,Berat,jam);
}//GEN-LAST:event_LahirKeyPressed

private void cmbCrJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbCrJkKeyPressed
   Valid.pindah(evt,BtnAll, DTPCari1);
}//GEN-LAST:event_cmbCrJkKeyPressed

private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
   Valid.pindah(evt,Proses,Diagnosa);
}//GEN-LAST:event_DTPCari1KeyPressed

private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_DTPCari2KeyPressed

private void jamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jamKeyPressed
   Valid.pindah(evt,Lahir, menit);
}//GEN-LAST:event_jamKeyPressed

private void menitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_menitKeyPressed
   Valid.pindah(evt,jam,detik);
}//GEN-LAST:event_menitKeyPressed

private void detikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_detikKeyPressed
   Valid.pindah(evt,menit,KdPenolong);
}//GEN-LAST:event_detikKeyPressed

private void BeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratKeyPressed
   Valid.pindah(evt,JKel,Lahir);
}//GEN-LAST:event_BeratKeyPressed

private void PanjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangKeyPressed
   Valid.pindah(evt,LingkarPerut,LingkarKepala);
}//GEN-LAST:event_PanjangKeyPressed

private void DaftarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DaftarKeyPressed
   Valid.pindah(evt,LingkarDada,Anakke);
}//GEN-LAST:event_DaftarKeyPressed

private void keteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keteranganKeyPressed
   Valid.pindah(evt,Anakke,Diagnosa);
}//GEN-LAST:event_keteranganKeyPressed

private void LahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LahirItemStateChanged
    lahir = Lahir.getDate();    
    birthday = lahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    p = Period.between(birthday,today);
    p2 =ChronoUnit.DAYS.between(birthday,today);
    UmurBayi.setText(String.valueOf(p.getYears())+" Th "+String.valueOf(p.getMonths())+" Bl "+String.valueOf(p.getDays())+" Hr");
}//GEN-LAST:event_LahirItemStateChanged

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
   isForm();  
}//GEN-LAST:event_ChkInputActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRm.getText().trim().equals("")){
            Valid.textKosong(NoRm,"No.Rekam Medis");
        }else if(NmBayi.getText().trim().equals("")){
            Valid.textKosong(NmBayi,"nama bayi");
        }else if(KdPenolong.getText().trim().equals("")||NmPenolong.getText().trim().equals("")){
            Valid.textKosong(KdPenolong,"penolong");
        }else if(NoSKL.getText().trim().equals("")){
            Valid.textKosong(NoSKL,"No.SKL");
        }else{
            if(Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_rkm_medis=?",NoRm.getText()).isEmpty()){   
                
                Sequel.queryu4("insert into cacat_fisik values(?,?)",2,new String[]{"0","-"});
                Sequel.queryu4("insert into penjab values(?,?)",2,new String[]{"-","-"});
                Sequel.queryu4("insert into kelurahan values(?,?)",2,new String[]{"0","-"});
                Sequel.queryu4("insert into kecamatan values(?,?)",2,new String[]{"0","-"});
                Sequel.queryu4("insert into kabupaten values(?,?)",2,new String[]{"0","-"});
                Sequel.queryu4("insert into propinsi values(?,?)",2,new String[]{"0","-"});
                Sequel.queryu4("insert into bahasa_pasien values(?,?)",2,new String[]{"0","-"});
                Sequel.queryu4("insert into suku_bangsa values(?,?)",2,new String[]{"0","-"});
                Sequel.queryu4("insert into perusahaan_pasien values(?,?,?,?,?)",2,new String[]{"-","-","-","-","-"});
                if(Sequel.menyimpantf("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                    NoRm.getText(),NmBayi.getText(),"-",
                    JKel.getSelectedItem().toString().substring(0,1),"-",
                    Valid.SetTgl(Lahir.getSelectedItem()+""),
                    Nmibu.getText(),AlamatIbu.getText(),"-","-","BELUM MENIKAH","-",
                    Valid.SetTgl(Daftar.getSelectedItem()+""),"0",UmurBayi.getText(),
                    "-","AYAH",NmAyah.getText(),"-","-",
                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?","-"),
                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?","-"),
                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?","-"),
                    "-",AlamatIbu.getText(),"-","-","-","-",
                    Sequel.cariIsi("select suku_bangsa.id from suku_bangsa where suku_bangsa.nama_suku_bangsa=?","-"),
                    Sequel.cariIsi("select bahasa_pasien.id from bahasa_pasien where bahasa_pasien.nama_bahasa=?","-"),
                    Sequel.cariIsi("select cacat_fisik.id from cacat_fisik where cacat_fisik.nama_cacat=?","-"),
                    "-","-",Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?","-"),"-"
                })==true){
                        if(Sequel.menyimpantf("pasien_bayi","'"+NoRm.getText()+"','"+
                            UmurIbu.getText()+"','"+
                            NmAyah.getText()+"','"+
                            UmurAyah.getText()+"','"+
                            Berat.getText()+"','"+
                            Panjang.getText()+"','"+
                            LingkarKepala.getText()+"','"+
                            Proses.getText()+"','"+
                            Anakke.getText()+"','"+
                            jam.getSelectedItem()+":"+menit.getSelectedItem()+":"+detik.getSelectedItem()+"','"+
                            keterangan.getText()+"','"+Diagnosa.getText()+"','"+
                            PenyulitKehamilan.getText()+"','"+Ketuban.getText()+"','"+
                            LingkarPerut.getText()+"','"+LingkarDada.getText()+"','"+
                            KdPenolong.getText()+"','"+NoSKL.getText()+"'","No.RM/No.SKL")==true){
                                Sequel.queryu2("delete from set_no_rkm_medis");
                                Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{NoRm.getText()}); 
                        }   
                        Sequel.queryu2("delete from set_no_rkm_medis");
                        Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{NoRm.getText()});
                        tampil();
                        emptTeks();
                }else{
                    autoNomor();
                    autoSKL();
                    if(Sequel.menyimpantf("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                            NoRm.getText(),NmBayi.getText(),"-",
                            JKel.getSelectedItem().toString().substring(0,1),"-",
                            Valid.SetTgl(Lahir.getSelectedItem()+""),
                            Nmibu.getText(),AlamatIbu.getText(),"-","-","BELUM MENIKAH","-",
                            Valid.SetTgl(Daftar.getSelectedItem()+""),"0",UmurBayi.getText(),
                            "-","AYAH",NmAyah.getText(),"-","-",
                            Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?","-"),
                            Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?","-"),
                            Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?","-"),
                            "-",AlamatIbu.getText(),"-","-","-","-",
                            Sequel.cariIsi("select suku_bangsa.id from suku_bangsa where suku_bangsa.nama_suku_bangsa=?","-"),
                            Sequel.cariIsi("select bahasa_pasien.id from bahasa_pasien where bahasa_pasien.nama_bahasa=?","-"),
                            Sequel.cariIsi("select cacat_fisik.id from cacat_fisik where cacat_fisik.nama_cacat=?","-"),
                            "-","-",Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?","-"),"-"
                        })==true){
                            if(Sequel.menyimpantf("pasien_bayi","'"+NoRm.getText()+"','"+
                                UmurIbu.getText()+"','"+
                                NmAyah.getText()+"','"+
                                UmurAyah.getText()+"','"+
                                Berat.getText()+"','"+
                                Panjang.getText()+"','"+
                                LingkarKepala.getText()+"','"+
                                Proses.getText()+"','"+
                                Anakke.getText()+"','"+
                                jam.getSelectedItem()+":"+menit.getSelectedItem()+":"+detik.getSelectedItem()+"','"+
                                keterangan.getText()+"','"+Diagnosa.getText()+"','"+
                                PenyulitKehamilan.getText()+"','"+Ketuban.getText()+"','"+
                                LingkarPerut.getText()+"','"+LingkarDada.getText()+"','"+
                                KdPenolong.getText()+"','"+NoSKL.getText()+"'","No.RM/No.SKL")==true){
                                    Sequel.queryu2("delete from set_no_rkm_medis");
                                    Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{NoRm.getText()}); 
                            }  
                            Sequel.queryu2("delete from set_no_rkm_medis");
                            Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{NoRm.getText()});
                        
                            tampil();
                            emptTeks();
                    }else{
                        autoNomor();
                        autoSKL();
                        if(Sequel.menyimpantf("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                                NoRm.getText(),NmBayi.getText(),"-",
                                JKel.getSelectedItem().toString().substring(0,1),"-",
                                Valid.SetTgl(Lahir.getSelectedItem()+""),
                                Nmibu.getText(),AlamatIbu.getText(),"-","-","BELUM MENIKAH","-",
                                Valid.SetTgl(Daftar.getSelectedItem()+""),"0",UmurBayi.getText(),
                                "-","AYAH",NmAyah.getText(),"-","-",
                                Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?","-"),
                                Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?","-"),
                                Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?","-"),
                                "-",AlamatIbu.getText(),"-","-","-","-",
                                Sequel.cariIsi("select suku_bangsa.id from suku_bangsa where suku_bangsa.nama_suku_bangsa=?","-"),
                                Sequel.cariIsi("select bahasa_pasien.id from bahasa_pasien where bahasa_pasien.nama_bahasa=?","-"),
                                Sequel.cariIsi("select cacat_fisik.id from cacat_fisik where cacat_fisik.nama_cacat=?","-"),
                                "-","-",Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?","-"),"-"
                            })==true){
                                if(Sequel.menyimpantf("pasien_bayi","'"+NoRm.getText()+"','"+
                                    UmurIbu.getText()+"','"+
                                    NmAyah.getText()+"','"+
                                    UmurAyah.getText()+"','"+
                                    Berat.getText()+"','"+
                                    Panjang.getText()+"','"+
                                    LingkarKepala.getText()+"','"+
                                    Proses.getText()+"','"+
                                    Anakke.getText()+"','"+
                                    jam.getSelectedItem()+":"+menit.getSelectedItem()+":"+detik.getSelectedItem()+"','"+
                                    keterangan.getText()+"','"+Diagnosa.getText()+"','"+
                                    PenyulitKehamilan.getText()+"','"+Ketuban.getText()+"','"+
                                    LingkarPerut.getText()+"','"+LingkarDada.getText()+"','"+
                                    KdPenolong.getText()+"','"+NoSKL.getText()+"'","No.RM/No.SKL")==true){
                                        Sequel.queryu2("delete from set_no_rkm_medis");
                                        Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{NoRm.getText()}); 
                                }  
                                Sequel.queryu2("delete from set_no_rkm_medis");
                                Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{NoRm.getText()});

                                tampil();
                                emptTeks();
                        }
                    }
                }
                
            }else{
                if(Sequel.menyimpantf("pasien_bayi","'"+NoRm.getText()+"','"+
                        UmurIbu.getText()+"','"+
                        NmAyah.getText()+"','"+
                        UmurAyah.getText()+"','"+
                        Berat.getText()+"','"+
                        Panjang.getText()+"','"+
                        LingkarKepala.getText()+"','"+
                        Proses.getText()+"','"+
                        Anakke.getText()+"','"+
                        jam.getSelectedItem()+":"+menit.getSelectedItem()+":"+detik.getSelectedItem()+"','"+
                        keterangan.getText()+"','"+Diagnosa.getText()+"','"+
                        PenyulitKehamilan.getText()+"','"+Ketuban.getText()+"','"+
                        LingkarPerut.getText()+"','"+LingkarDada.getText()+"','"+
                        KdPenolong.getText()+"','"+NoSKL.getText()+"'","No.RM/No.SKL")==true){
                            tampil();
                            emptTeks();
                }             
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Proses,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm();
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

private void BtnEditActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed1
        if(NoRm.getText().trim().equals("")){
            Valid.textKosong(NoRm,"No.Rekam Medis");
        }else if(NmBayi.getText().trim().equals("")){
            Valid.textKosong(NmBayi,"nama bayi");
        }else if(KdPenolong.getText().trim().equals("")||NmPenolong.getText().trim().equals("")){
            Valid.textKosong(KdPenolong,"penolong");
        }else{
            Valid.editTable(tabMode,"pasien","no_rkm_medis",Kd2,"no_rkm_medis='"+NoRm.getText()+
                    "',nm_pasien='"+NmBayi.getText()+
                    "',jk='"+JKel.getSelectedItem().toString().substring(0,1)+
                    "',tgl_lahir='"+Valid.SetTgl(Lahir.getSelectedItem()+"")+
                    "',alamat='"+AlamatIbu.getText()+
                    "',tgl_daftar='"+Valid.SetTgl(Daftar.getSelectedItem()+"")+
                    "',umur='"+UmurBayi.getText()+
                    "',nm_ibu='"+Nmibu.getText()+
                    "',namakeluarga='"+NmAyah.getText()+"'");
            Valid.editTable(tabMode,"pasien_bayi","no_rkm_medis",Kd2,"umur_ibu='"+UmurIbu.getText()+
                    "',nama_ayah='"+NmAyah.getText()+
                    "',umur_ayah='"+UmurAyah.getText()+
                    "',berat_badan='"+Berat.getText()+
                    "',panjang_badan='"+Panjang.getText()+
                    "',lingkar_kepala='"+LingkarKepala.getText()+
                    "',proses_lahir='"+Proses.getText()+
                    "',anakke='"+Anakke.getText()+
                    "',diagnosa='"+Diagnosa.getText()+
                    "',penyulit_kehamilan='"+PenyulitKehamilan.getText()+
                    "',ketuban='"+Ketuban.getText()+
                    "',lingkar_perut='"+LingkarPerut.getText()+
                    "',lingkar_dada='"+LingkarDada.getText()+
                    "',penolong='"+KdPenolong.getText()+
                    "',no_skl='"+NoSKL.getText()+
                    "',jam_lahir='"+jam.getSelectedItem()+":"+menit.getSelectedItem()+":"+detik.getSelectedItem()+
                    "',keterangan='"+keterangan.getText()+"'");
            if(tabMode.getRowCount()!=0){tampil();}
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed1

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_SPACE){
           BtnEditActionPerformed1(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

private void BtnHapusActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed1
   Valid.hapusTable(tabMode,NoRm,"pasien_bayi","no_rkm_medis");
   Valid.hapusTable(tabMode,NoRm,"bridging_dukcapil","no_rkm_medis");
   tampil();
   emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed1

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed1(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

private void NmBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmBayiKeyPressed
   Valid.pindah(evt, NoRm,Nmibu);
}//GEN-LAST:event_NmBayiKeyPressed

private void NmibuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmibuKeyPressed
   Valid.pindah(evt, NmBayi,UmurIbu);
}//GEN-LAST:event_NmibuKeyPressed

private void UmurIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurIbuKeyPressed
   Valid.pindah(evt, Nmibu,NmAyah);
}//GEN-LAST:event_UmurIbuKeyPressed

private void NmAyahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmAyahKeyPressed
   Valid.pindah(evt, Nmibu,UmurAyah);
}//GEN-LAST:event_NmAyahKeyPressed

private void UmurAyahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurAyahKeyPressed
    Valid.pindah(evt, NmAyah,AlamatIbu);
}//GEN-LAST:event_UmurAyahKeyPressed

private void AlamatIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatIbuKeyPressed
    Valid.pindah(evt, UmurAyah,JKel);
}//GEN-LAST:event_AlamatIbuKeyPressed

private void MnKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartuActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            NoRm.requestFocus();
        }else if(NmBayi.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien yang mau pulang dengan menklik data pada table...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.MyReport("rptKartuBerobat.jrxml","report","::[ Kartu Periksa Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, pasien.alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien where pasien.no_rkm_medis='"+NoRm.getText()+"' ");
        }
}//GEN-LAST:event_MnKartuActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnInformasiBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInformasiBayiActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptInformasiBayi.jrxml","report","::[ Data Informasi Bayi ]::",
                       "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, "+
                       "pasien.tgl_lahir,pasien_bayi.jam_lahir, pasien.umur, "+
                       "pasien.tgl_daftar,pasien.nm_ibu as namakeluarga,pasien_bayi.umur_ibu, "+
                       "pasien_bayi.nama_ayah,pasien_bayi.umur_ayah,pasien.alamat, "+
                       "pasien_bayi.berat_badan,pasien_bayi.panjang_badan, pasien_bayi.lingkar_kepala, "+
                       "pasien_bayi.proses_lahir,pasien_bayi.anakke, pasien_bayi.keterangan from pasien "+
                       "inner join pasien_bayi on pasien.no_rkm_medis=pasien_bayi.no_rkm_medis "+
                       "where pasien_bayi.no_rkm_medis='"+NoRm.getText()+"'",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnInformasiBayiActionPerformed

    private void MnSKLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSKLActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("nomor",NoSKL.getText());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                param.put("logo2",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptSKL.jrxml","report","::[ Surat Kelahiran Bayi ]::",
                       "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, "+
                        "pasien.tgl_lahir,pasien_bayi.jam_lahir, pasien.umur, "+
                        "pasien.tgl_daftar,pasien.nm_ibu,pasien_bayi.umur_ibu, "+
                        "pasien_bayi.nama_ayah,pasien_bayi.umur_ayah,"+
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "+
                        "pasien_bayi.berat_badan,pasien_bayi.panjang_badan, pasien_bayi.lingkar_kepala, "+
                        "pasien_bayi.proses_lahir,pasien_bayi.anakke, pasien_bayi.keterangan, "+
                        "pasien_bayi.diagnosa,pasien_bayi.penyulit_kehamilan,pasien_bayi.ketuban,"+
                        "pasien_bayi.lingkar_perut,pasien_bayi.lingkar_dada,pegawai.nama,"+
                        "pasien_bayi.no_skl from pasien inner join pegawai inner join pasien_bayi "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten "+
                        "on pasien.no_rkm_medis=pasien_bayi.no_rkm_medis and pasien_bayi.penolong=pegawai.nik "+
                        "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien_bayi.no_rkm_medis='"+NoRm.getText()+"'",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSKLActionPerformed

    private void UmurBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurBayiKeyPressed
        Valid.pindah(evt,LingkarKepala,LingkarDada);
    }//GEN-LAST:event_UmurBayiKeyPressed

    private void PenyulitKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyulitKehamilanKeyPressed
        Valid.pindah(evt,Ketuban,Proses);
    }//GEN-LAST:event_PenyulitKehamilanKeyPressed

    private void KetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetubanKeyPressed
        Valid.pindah(evt,Diagnosa,PenyulitKehamilan);
    }//GEN-LAST:event_KetubanKeyPressed

    private void LingkarDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarDadaKeyPressed
        Valid.pindah(evt,UmurBayi,Daftar);
    }//GEN-LAST:event_LingkarDadaKeyPressed

    private void LingkarPerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarPerutKeyPressed
        Valid.pindah(evt,NoSKL,Panjang);
    }//GEN-LAST:event_LingkarPerutKeyPressed

    private void NoSKLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSKLKeyPressed
        Valid.pindah(evt,KdPenolong,LingkarPerut);
    }//GEN-LAST:event_NoSKLKeyPressed

    private void KdPenolongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPenolongKeyPressed
        Valid.pindah(evt,detik,NoSKL);
    }//GEN-LAST:event_KdPenolongKeyPressed

    private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPenjabActionPerformed

    private void BtnKelurahan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahan1ActionPerformed
        if(NoRm.getText().trim().equals("")){
            Valid.textKosong(NoRm,"No.Rekam Medis");
        }else if(NmBayi.getText().trim().equals("")){
            Valid.textKosong(NmBayi,"nama bayi");
        }else if(KdPenolong.getText().trim().equals("")||NmPenolong.getText().trim().equals("")){
            Valid.textKosong(KdPenolong,"penolong");
        }else if(NoSKL.getText().trim().equals("")){
            Valid.textKosong(NoSKL,"No.SKL");
        }else if(Anakke.getText().trim().equals("")){
            Valid.textKosong(Anakke,"Kelahiran");
        }else if(Berat.getText().trim().equals("")){
            Valid.textKosong(Berat,"Berat");
        }else if(Panjang.getText().trim().equals("")){
            Valid.textKosong(Panjang,"Panjang");
        }else{
        }            
    }//GEN-LAST:event_BtnKelurahan1ActionPerformed

    private void MnSKL1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSKL1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("nomor",NoSKL.getText());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("logo2",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSKL2.jrxml","report","::[ Surat Kelahiran Bayi ]::",
                "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, "+
                "pasien.tgl_lahir,pasien_bayi.jam_lahir, pasien.umur, "+
                "pasien.tgl_daftar,pasien.nm_ibu,pasien_bayi.umur_ibu,pasien.pekerjaanpj, "+
                "pasien_bayi.nama_ayah,pasien_bayi.umur_ayah,pasien.no_ktp,"+
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "+
                "pasien_bayi.berat_badan,pasien_bayi.panjang_badan, pasien_bayi.lingkar_kepala, "+
                "pasien_bayi.proses_lahir,pasien_bayi.anakke, pasien_bayi.keterangan, "+
                "pasien_bayi.diagnosa,pasien_bayi.penyulit_kehamilan,pasien_bayi.ketuban,"+
                "pasien_bayi.lingkar_perut,pasien_bayi.lingkar_dada,pegawai.nama,"+
                "pasien_bayi.no_skl from pasien inner join pegawai inner join pasien_bayi "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "on pasien.no_rkm_medis=pasien_bayi.no_rkm_medis and pasien_bayi.penolong=pegawai.nik "+
                "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where pasien_bayi.no_rkm_medis='"+NoRm.getText()+"'",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSKL1ActionPerformed

    private void MnSKL2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSKL2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Locale locale = new Locale ("id", "ID"); 
            Locale.setDefault(locale); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("nomor",NoSKL.getText());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("logo2",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSKL3.jrxml","report","::[ Surat Kelahiran Bayi ]::",
                "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, "+
                "pasien.tgl_lahir,pasien_bayi.jam_lahir, pasien.umur, "+
                "pasien.tgl_daftar,pasien.nm_ibu,pasien_bayi.umur_ibu,pasien.pekerjaanpj, "+
                "pasien_bayi.nama_ayah,pasien_bayi.umur_ayah,pasien.no_ktp,"+
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "+
                "pasien_bayi.berat_badan,pasien_bayi.panjang_badan, pasien_bayi.lingkar_kepala, "+
                "pasien_bayi.proses_lahir,pasien_bayi.anakke, pasien_bayi.keterangan, "+
                "pasien_bayi.diagnosa,pasien_bayi.penyulit_kehamilan,pasien_bayi.ketuban,"+
                "pasien_bayi.lingkar_perut,pasien_bayi.lingkar_dada,pegawai.nama,"+
                "pasien_bayi.no_skl from pasien inner join pegawai inner join pasien_bayi "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "on pasien.no_rkm_medis=pasien_bayi.no_rkm_medis and pasien_bayi.penolong=pegawai.nik "+
                "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where pasien_bayi.no_rkm_medis='"+NoRm.getText()+"'",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSKL2ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgIKBBayi dialog = new DlgIKBBayi(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatIbu;
    private widget.TextBox Anakke;
    private widget.TextBox Berat;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKelurahan1;
    private widget.Button BtnPenjab;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal Daftar;
    private widget.TextBox Diagnosa;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox JKel;
    private widget.TextBox Kd2;
    private widget.TextBox KdPenolong;
    private widget.TextBox Ketuban;
    private widget.Label LCount;
    private widget.Tanggal Lahir;
    private widget.TextBox LingkarDada;
    private widget.TextBox LingkarKepala;
    private widget.TextBox LingkarPerut;
    private javax.swing.JMenuItem MnInformasiBayi;
    private javax.swing.JMenuItem MnKartu;
    private javax.swing.JMenuItem MnSKL;
    private javax.swing.JMenuItem MnSKL1;
    private javax.swing.JMenuItem MnSKL2;
    private widget.TextBox NmAyah;
    private widget.TextBox NmBayi;
    private widget.TextBox NmPenolong;
    private widget.TextBox Nmibu;
    private widget.TextBox NoRm;
    private widget.TextBox NoSKL;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Panjang;
    private widget.TextBox PenyulitKehamilan;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox Proses;
    private widget.TextBox TCari;
    private widget.TextBox UmurAyah;
    private widget.TextBox UmurBayi;
    private widget.TextBox UmurIbu;
    private widget.CekBox ckTglCari;
    private widget.ComboBox cmbCrJk;
    private widget.ComboBox cmbHlm;
    private widget.ComboBox detik;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel24;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel2;
    private widget.ComboBox jam;
    private widget.TextArea keterangan;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label9;
    private widget.ComboBox menit;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        jkelcari=""; tglcari="";
        if(! cmbCrJk.getSelectedItem().toString().equals("SEMUA")){
            jkelcari=" pasien.jk='"+cmbCrJk.getSelectedItem().toString().substring(0,1)+"' and ";
        }
        
        if(ckTglCari.isSelected()==true){
                tglcari=" pasien.tgl_lahir between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and ";
        }        
        
        String sql="select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, "+
                   "pasien.tgl_lahir,pasien_bayi.jam_lahir, pasien.umur, "+
                   "pasien.tgl_daftar,pasien.nm_ibu,pasien_bayi.umur_ibu, "+
                   "pasien_bayi.nama_ayah,pasien_bayi.umur_ayah,"+
                   "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "+
                   "pasien_bayi.berat_badan,pasien_bayi.panjang_badan, pasien_bayi.lingkar_kepala, "+
                   "pasien_bayi.proses_lahir,pasien_bayi.anakke, pasien_bayi.keterangan, "+
                   "pasien_bayi.diagnosa,pasien_bayi.penyulit_kehamilan,pasien_bayi.ketuban,"+
                   "pasien_bayi.lingkar_perut,pasien_bayi.lingkar_dada,pegawai.nama,"+
                   "pasien_bayi.no_skl from pasien inner join pegawai inner join pasien_bayi "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.no_rkm_medis=pasien_bayi.no_rkm_medis and pasien_bayi.penolong=pegawai.nik "+
                   "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                   "where "+jkelcari+tglcari+" pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.tgl_lahir like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.namakeluarga like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.alamat like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pegawai.nama like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien_bayi.diagnosa like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien.nm_ibu like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien_bayi.proses_lahir like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien_bayi.penyulit_kehamilan like '%"+TCari.getText().trim()+"%' "+
                   "or "+jkelcari+tglcari+" pasien_bayi.ketuban like '%"+TCari.getText().trim()+"%'  order by pasien.no_rkm_medis desc";               
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try{
            java.sql.Statement stat=koneksi.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            while(rs.next()){
                tabMode.addRow(new Object[]{rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               rs.getString(7),
                               rs.getString(8),
                               rs.getString(9),
                               rs.getString(10),
                               rs.getString(11),
                               rs.getString(12),
                               rs.getString(13),
                               rs.getString(14),
                               rs.getString(15),
                               rs.getString(16),
                               rs.getString(17),
                               rs.getString(18),
                               rs.getString(19),
                               rs.getString(20),
                               rs.getString(21),
                               rs.getString(22),
                               rs.getString(23),
                               rs.getString(24),
                               rs.getString(25)});
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        NoRm.setText("");
        NmBayi.setText("");
        Nmibu.setText("");
        UmurIbu.setText("");
        NmAyah.setText("");
        UmurAyah.setText("");
        AlamatIbu.setText("");
        JKel.setSelectedIndex(0);
        Berat.setText("");
        Lahir.setDate(new Date());
        jam.setSelectedIndex(0);
        menit.setSelectedIndex(0);
        detik.setSelectedIndex(0);
        Panjang.setText("");
        LingkarKepala.setText("");
        UmurBayi.setText("");
        Proses.setText("");
        LingkarPerut.setText("");
        LingkarDada.setText("");
        Daftar.setDate(new Date());
        Anakke.setText("");
        Diagnosa.setText("");
        PenyulitKehamilan.setText("");
        Ketuban.setText("");
        keterangan.setText("");
        autoNomor();        
        autoSKL();
        NoRm.requestFocus();
    }
    

    private void getData() {
        if(tbDokter.getSelectedRow()!= -1){
            NoRm.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
            Kd2.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
            NmBayi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString().equals("L")){
                JKel.setSelectedItem("LAKI-LAKI");
            }else if(tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString().equals("P")){
                JKel.setSelectedItem("PEREMPUAN");
            }
            Valid.SetTgl(Lahir,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());            
            jam.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString().substring(0,2));
            menit.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString().substring(3,5));
            detik.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString().substring(6,8));
            UmurBayi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString());
            Valid.SetTgl(Lahir,tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()); 
            Nmibu.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString());
            UmurIbu.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString());
            NmAyah.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString());
            UmurAyah.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString());
            AlamatIbu.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),11).toString());
            Berat.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),12).toString());
            Panjang.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),13).toString());
            LingkarKepala.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString());
            Proses.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),15).toString());
            Anakke.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),16).toString());
            keterangan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),17).toString());    
            Diagnosa.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),18).toString());    
            PenyulitKehamilan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),19).toString());    
            Ketuban.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),20).toString());    
            LingkarPerut.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),21).toString());    
            LingkarDada.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),22).toString());    
            NmPenolong.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),23).toString());  
            NoSKL.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),24).toString());
            Sequel.cariIsi("select penolong from pasien_bayi where no_rkm_medis=?",KdPenolong,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
        }
    }


    public JTextField getTextField(){
        return NoRm;
    }

    public JButton getButton(){
        return BtnKeluar;
    }

    private void isIbu(){
        try {
            Statement stat = koneksi.createStatement();
            ResultSet rs=stat.executeQuery("select pasien_ibu.nm_pasien,"+
                    " pasien_ibu.suami, "+
                    " pasien_ibu.umur, "+
                    " pasien_ibu.alamat "+
                    " from pasien_ibu where pasien_ibu.no_rm_ib ");
            while(rs.next()){
                Nmibu.setText(rs.getString(1));
                NmAyah.setText(rs.getString(2));
                UmurIbu.setText(rs.getString(3));
                AlamatIbu.setText(rs.getString(4));
            }
        } catch (SQLException ex) {
            System.out.println("Catatan ibu : "+ex);
        }
    } 
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,279));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void setNoRM(String norm,String nama,String ibubayi,String alamatibu,
            String jkel,String umur,Date tgllhir,Date daftar){
        NoRm.setText(norm);
        NmBayi.setText(nama);
        Nmibu.setText(ibubayi);
        AlamatIbu.setText(alamatibu);
        JKel.setSelectedItem(jkel);
        UmurBayi.setText(umur);
        Lahir.setDate(tgllhir);
        Daftar.setDate(daftar); 
        
        if(Sequel.cariIsi("select keluarga from pasien where no_rkm_medis=?",norm).equals("AYAH")){
            Sequel.cariIsi("select namakeluarga from pasien where no_rkm_medis=?",NmAyah,norm);
        }
        
        ChkInput.setSelected(true);
        isForm();
        autoSKL();
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getmanajemen());
        BtnHapus.setEnabled(var.getmanajemen());
        BtnEdit.setEnabled(var.getmanajemen());
        BtnPrint.setEnabled(var.getmanajemen());
    }
    
    private void autoNomor() {  
        if(Kd2.getText().equals("")){            
            if(tahun.equals("Yes")){
                awalantahun=Daftar.getSelectedItem().toString().substring(8,10);
            }else{
                awalantahun="";
            }

            if(bulan.equals("Yes")){
                awalanbulan=Daftar.getSelectedItem().toString().substring(3,5);
            }else{
                awalanbulan="";
            }

            if(posisitahun.equals("Depan")){
                switch (pengurutan) {
                    case "Straight":
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rkm_medis,6),signed)),0) from set_no_rkm_medis","",6,NoRm);
                        break;
                    case "Terminal":
                        Valid.autoNomer4("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(no_rkm_medis,6),5,2),SUBSTRING(RIGHT(no_rkm_medis,6),3,2),SUBSTRING(RIGHT(no_rkm_medis,6),1,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                        break;
                    case "Middle":
                        Valid.autoNomer5("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(no_rkm_medis,6),3,2),SUBSTRING(RIGHT(no_rkm_medis,6),1,2),SUBSTRING(RIGHT(no_rkm_medis,6),5,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                        break;
                }
            }else if(posisitahun.equals("Belakang")){
                switch (pengurutan) {
                    case "Straight":
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(LEFT(no_rkm_medis,6),signed)),0) from set_no_rkm_medis","",6,NoRm);
                        break;
                    case "Terminal":
                        Valid.autoNomer4("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(no_rkm_medis,6),5,2),SUBSTRING(LEFT(no_rkm_medis,6),3,2),SUBSTRING(LEFT(no_rkm_medis,6),1,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                        break;
                    case "Middle":
                        Valid.autoNomer5("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(no_rkm_medis,6),3,2),SUBSTRING(LEFT(no_rkm_medis,6),1,2),SUBSTRING(LEFT(no_rkm_medis,6),5,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                        break;
                }            
            }

            if(posisitahun.equals("Depan")){
                NoRm.setText(awalantahun+awalanbulan+NoRm.getText());
            }else if(posisitahun.equals("Belakang")){
                if(!(awalanbulan+awalantahun).equals("")){
                    NoRm.setText(NoRm.getText()+"-"+awalanbulan+awalantahun);
                }else{
                    NoRm.setText(NoRm.getText());
                }            
            }
        }        
    }
    
    public JTable getTable(){
        return tbDokter;
    }

    private void autoSKL() {
        Valid.autoNomer6("select ifnull(MAX(CONVERT(LEFT(no_skl,4),signed)),0) from pasien inner join pasien_bayi on pasien.no_rkm_medis=pasien_bayi.no_rkm_medis where "+
                       "pasien.tgl_lahir like '%"+Valid.SetTgl(Lahir.getSelectedItem()+"").substring(0,7)+"%' ","/RM-SKL/"+Valid.SetTgl(Lahir.getSelectedItem()+"").substring(5,7)+
                        "/"+Valid.SetTgl(Lahir.getSelectedItem()+"").substring(0,4),4,NoSKL);         
    }
}
